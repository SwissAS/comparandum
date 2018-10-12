package de.rosstauscher.comparandum.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * Some helper methods for rendering objects to images. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class RenderUtil {
	
	private static final String USE_FAST_PNG_ENCODER_PROPERTY = "de.rosstauscher.comparandum.fastpng";
	private static final String DISABLE_TEXT_AA = "de.rosstauscher.comparandum.disableTextAA";

	private static final String DEFAULT_IMAGE_FORMAT = "png";
	
	public static final int DEFAULT_IMAGE_TYPE = BufferedImage.TYPE_INT_ARGB;
	
	private static Object fastPngEncoder;
	private static Method fastEncodeMethod;
	
	static {
		if (Boolean.getBoolean(USE_FAST_PNG_ENCODER_PROPERTY)) {
			try {
				Class<?> encoderClass = Class.forName("com.objectplanet.image.PngEncoder");
				Constructor<?> cc = encoderClass.getConstructor(int.class, int.class);

				// Color type RGB with Alpha channel and compression "Best Speed"
				int rgba = 6;
				int bestSpeed = 1;
				fastPngEncoder = cc.newInstance(rgba, bestSpeed);
				
				fastEncodeMethod = encoderClass.getMethod("encode", Image.class, OutputStream.class);
			} catch (Exception e) {
				// Cannot load fast png library then fallback to default mode.
			}
		}
		
		
	}
	
	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	private RenderUtil() {
		super();
	}
	
	/*************************************************************************
	 * Renders the given renderable object as Image.
	 * @param r the renderable object 
	 * @return an Image representing this object.
	 ************************************************************************/
	
	public static BufferedImage renderToImage(IRenderable r) {
		Dimension d = r.getDimension();
		BufferedImage bi = new BufferedImage(d.width, d.height, DEFAULT_IMAGE_TYPE);
		Graphics2D g = bi.createGraphics();

		if (Boolean.getBoolean(DISABLE_TEXT_AA)) {
			g = new Graphics2dNoTextAA(g);
		}
		
		r.paint(g);
		g.dispose();
		
		return bi;
	}
	
	/*************************************************************************
	 * Renders the given renderable object and saves it to an image file.
	 * @param r the renderable object 
	 * @param destination file to write the image to.
	 * @throws IOException on error.
	 ************************************************************************/
	
	public static void renderToDisk(IRenderable r, File destination) throws IOException {
		BufferedImage img = RenderUtil.renderToImage(r);
		FileOutputStream fout = new FileOutputStream(destination);
		BufferedOutputStream out = new BufferedOutputStream(fout);
		try {
			if (isFastPngEncoderSupported()) {
				fastEncodeAsPng(img, out); 
			} else {
				ImageIO.write(img, DEFAULT_IMAGE_FORMAT, new BufferedOutputStream(fout));
			}
		} finally {
			IOUtil.closeSilently(fout);
		}
	}

	/*************************************************************************
	 * @return
	 ************************************************************************/
	
	public static boolean isFastPngEncoderSupported() {
		return fastEncodeMethod != null;
	}

	/*************************************************************************
	 * @param img
	 * @param out
	 ************************************************************************/
	
	private static void fastEncodeAsPng(BufferedImage img, BufferedOutputStream out) {
		try {
			fastEncodeMethod.invoke(fastPngEncoder, img, out);
			out.flush();
		} catch (Exception e) {
			Logger.getLogger(IOUtil.DEFAULT_LOGGER_NAME).log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

}

