package de.rosstauscher.comparandum.filter;

import java.awt.Dimension;
import java.awt.Graphics2D;

import de.rosstauscher.comparandum.render.AbstractRenderableFacade;
import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * Implements a filter that will scale a given IRenderable.
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class ScaleFilter implements IFilter {

	public static final int SAME = -1;
	
	private class Renderable extends AbstractRenderableFacade {
		
		public Renderable(IRenderable delegate) {
			super(delegate);
		}

		@Override
		public void paint(Graphics2D g) {
			Dimension d = this.delegate.getDimension();
			double sx = getScale(d.width, ScaleFilter.this.newWidht);
			double sy = getScale(d.height, ScaleFilter.this.newHeight); 
			g.scale(sx, sy);
			super.paint(g);
		}

		@Override
		public Dimension getDimension() {
			Dimension d = this.delegate.getDimension();
			double sx = getScale(d.width, ScaleFilter.this.newWidht);
			double sy = getScale(d.height, ScaleFilter.this.newHeight); 
			return new Dimension((int)(d.width*sx), (int)(d.height*sy));
		}
		
		/*************************************************************************
		 * Performs the scale.
		 * @param value dimension to scale.
		 * @param expectedValue the expected dimension to scale to.
		 * @return
		 ************************************************************************/
		
		private double getScale(int value, int expectedValue) {
			if (expectedValue == SAME) {
				return 1.0;
			}
			return expectedValue / (double)value;
		}
		
	}
	
	private final int newWidht;
	private final int newHeight;

	/*************************************************************************
	 * Constructor
	 * @param newHeight height to scale to.
	 * @param newWidht width to scale to.
	 ************************************************************************/
	
	public ScaleFilter(int newWidht, int newHeight) {
		super();
		this.newWidht = newWidht;
		this.newHeight = newHeight;
	}

	/*************************************************************************
	 * Applies the filter to the renderable object.
	 * @see de.rosstauscher.comparandum.filter.IFilter#applyTo(de.rosstauscher.comparandum.render.IRenderable)
	 ************************************************************************/
	@Override
	public IRenderable applyTo(IRenderable input) {
		return new Renderable(input);
	}

}

