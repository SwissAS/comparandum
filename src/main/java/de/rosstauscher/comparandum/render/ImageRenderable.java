package de.rosstauscher.comparandum.render;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

/*****************************************************************************
 * A image renderer. This is a adapter to wrap an Image as IRenderable. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class ImageRenderable implements IRenderable {

	private final Image image;

	/*************************************************************************
	 * Constructor
	 * @param image the image to wrap.
	 ************************************************************************/
	
	public ImageRenderable(Image image) {
		super();
		this.image = image;
	}
	
	/*************************************************************************
	 * paint
	 * @see de.rosstauscher.comparandum.render.IRenderable#paint(java.awt.Graphics2D)
	 ************************************************************************/
	@Override
	public void paint(Graphics2D g) {
		Dimension d = getDimension();
		g.drawImage(this.image, 0, 0, d.width, d.height, null);
	}

	/*************************************************************************
	 * getDimension
	 * @see de.rosstauscher.comparandum.render.IRenderable#getDimension()
	 ************************************************************************/
	@Override
	public Dimension getDimension() {
		return new Dimension(this.image.getWidth(null), this.image.getHeight(null));
	}

}

