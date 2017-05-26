package de.rosstauscher.comparandum.filter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

import de.rosstauscher.comparandum.render.AbstractRenderableFacade;
import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * Implements a filter that will erase a given area.
 * This ensures that the area will then be ignored in a later compare.
 * You should apply the same filter to both the "expected" and the "actual"
 * renderable before comparison. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class ExcludeAreaFilter implements IFilter {

	public static final Color IGNORE_COLOR = Color.WHITE;

	private class Renderable extends AbstractRenderableFacade {
		
		public Renderable(IRenderable delegate) {
			super(delegate);
		}

		@Override
		public void paint(Graphics2D g) {
			super.paint(g);
			g.setColor(IGNORE_COLOR);
			g.fill(ExcludeAreaFilter.this.excludeArea);
		}
	}
	
	private final Shape excludeArea;

	/*************************************************************************
	 * Constructor
	 * @param excludeArea to exclude during comparison.
	 ************************************************************************/
	
	public ExcludeAreaFilter(Shape excludeArea) {
		super();
		this.excludeArea = excludeArea;
	}

	/*************************************************************************
	 * Applies the filter to the input.
	 * @see de.rosstauscher.comparandum.filter.IFilter#applyTo(de.rosstauscher.comparandum.render.IRenderable)
	 ************************************************************************/
	@Override
	public IRenderable applyTo(IRenderable input) {
		return new Renderable(input);
	}

}

