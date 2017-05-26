package de.rosstauscher.comparandum.loader;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import de.rosstauscher.comparandum.render.IRenderable;
import de.rosstauscher.comparandum.util.ComparandumException;
import de.rosstauscher.comparandum.util.IOUtil;
import de.rosstauscher.comparandum.util.RenderUtil;

/*****************************************************************************
 * Loads a comparatum image from a file 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class ImageFileLoader extends AbstractImageLoader implements IUpdateableLocation {

	private static final String DEFAULT_IMAGE_FORMAT = "png";

	private final File f;
	
	/*************************************************************************
	 * Constructor
	 * @param f a file to load the image from.
	 ************************************************************************/
	
	public ImageFileLoader(File f) {
		super();
		this.f = f;
	}
	
	/*************************************************************************
	 * Loads the comparatum image. 
	 * @return the image loaded from a file.
	 ************************************************************************/
	@Override
	protected Image loadImage() {
		return new ImageIcon(this.f.getPath()).getImage();
	}

	/*************************************************************************
	 * Used to store an comparatum back to the image file.
	 * @see de.rosstauscher.comparandum.loader.IUpdateableLocation#storeAsComparatum(de.rosstauscher.comparandum.render.IRenderable)
	 ************************************************************************/
	@Override
	public void storeAsComparatum(IRenderable toRender) {
		BufferedImage img = RenderUtil.renderToImage(toRender);
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(this.f.getAbsolutePath());
			ImageIO.write(img, DEFAULT_IMAGE_FORMAT, fout);
		} catch (IOException e) {
			throw new ComparandumException(e.getMessage(), e);
		} finally {
			IOUtil.closeSilently(fout);
		}
	}

}

