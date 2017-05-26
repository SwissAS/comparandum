package de.rosstauscher.comparandum.filter;

import java.awt.Color;

import de.rosstauscher.comparandum.render.BorderRenderable;
import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * This is a filter to add a border around a renderable object. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class BorderFilter implements IFilter {

	private final int left;
	private final int right;
	private final int top;
	private final int bottom;
	private final Color bgColor;

	/*************************************************************************
	 * Constructor
	 * @param border width in pixel.
	 * @param bgColor of the border area.
	 ************************************************************************/
	
	public BorderFilter(int border, Color bgColor) {
		this(border, border, border, border, bgColor);
	}
	
	/*************************************************************************
	 * Constructor
	 * @param left width in pixel.
	 * @param right width in pixel.
	 * @param top width in pixel.
	 * @param bottom width in pixel.
	 * @param bgColor of the border area.
	 ************************************************************************/
	
	public BorderFilter(int left, int right, int top, int bottom, Color bgColor) {
		super();
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom; 
		this.bgColor = bgColor;
	}

	/*************************************************************************
	 * applyTo
	 * @see de.rosstauscher.comparandum.filter.IFilter#applyTo(de.rosstauscher.comparandum.render.IRenderable)
	 ************************************************************************/
	@Override
	public IRenderable applyTo(IRenderable input) {
		return new BorderRenderable(input, 
				this.left, this.right, 
				this.top, this.bottom, 
				this.bgColor);
	}
	
	

}
