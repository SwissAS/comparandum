package de.rosstauscher.comparandum.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * Some helper methods for rendering objects to images. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class RenderUtil {
	
	private static final String DEFAULT_IMAGE_FORMAT = "png";
	
	public static final int DEFAULT_IMAGE_TYPE = BufferedImage.TYPE_INT_ARGB;
	
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
		ImageIO.write(img, DEFAULT_IMAGE_FORMAT, new BufferedOutputStream(new FileOutputStream(destination)));
	}

}

