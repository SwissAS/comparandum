package de.rosstauscher.comparandum.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

/*****************************************************************************
 * This facade will create a border around a given renderable.
 *  
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class BorderRenderable extends AbstractRenderableFacade {
	
	private final int left;
	private final int right;
	private final int top;
	private final int bottom;
	private final Color bgColor;

	/*************************************************************************
	 * Constructor
	 * @param delegate to paint inside the border
	 * @param border the border width in pixel.
	 * @param bgColor the background color for the border, may be null.
	 ************************************************************************/
	
	public BorderRenderable(IRenderable delegate, int border, Color bgColor) {
		this(delegate, border, border, border, border, bgColor);
	}
	
	/*************************************************************************
	 * Constructor
	 * @param delegate to paint inside the border
	 * @param left border width in pixel.
	 * @param right border width in pixel.
	 * @param top border width in pixel.
	 * @param bottom border width in pixel.
	 * @param bgColor the background color for the border, may be null.
	 ************************************************************************/
	
	public BorderRenderable(IRenderable delegate, int left, int right, int top, int bottom, Color bgColor) {
		super(delegate);
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom; 
		this.bgColor = bgColor;
	}

	/*************************************************************************
	 * paint
	 * @see de.rosstauscher.comparandum.render.AbstractRenderableFacade#paint(java.awt.Graphics2D)
	 ************************************************************************/
	@Override
	public void paint(Graphics2D g) {
		Dimension d = getDimension();
		if (this.bgColor != null) {
			g.setBackground(this.bgColor);
			g.fillRect(0, 0, d.width, d.height);
		}
		Graphics2D g2 = (Graphics2D) g.create(this.left, this.top, d.width, d.height);
		super.paint(g2);
		g2.dispose();
	}

	/*************************************************************************
	 * getDimension
	 * @see de.rosstauscher.comparandum.render.AbstractRenderableFacade#getDimension()
	 ************************************************************************/
	@Override
	public Dimension getDimension() {
		Dimension origD = super.getDimension();
		return new Dimension(
				origD.width+this.left+this.right, 
				origD.height+this.top+this.bottom);
	}

}
