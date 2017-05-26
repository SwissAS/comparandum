package de.rosstauscher.comparandum.render;

import java.awt.Dimension;
import java.awt.Graphics2D;

/*****************************************************************************
 * This is a base class to implement renderable delgates. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public abstract class AbstractRenderableFacade implements IRenderable {

	protected IRenderable delegate;

	/*************************************************************************
	 * Constructor
	 * @param delegate to use.
	 ************************************************************************/
	
	public AbstractRenderableFacade(IRenderable delegate) {
		super();
		this.delegate = delegate;
	}
	
	/*************************************************************************
	 * paint
	 * @see de.rosstauscher.comparandum.render.IRenderable#paint(java.awt.Graphics2D)
	 ************************************************************************/
	@Override
	public void paint(Graphics2D g) {
		this.delegate.paint(g);
	}

	/*************************************************************************
	 * getDimension
	 * @see de.rosstauscher.comparandum.render.IRenderable#getDimension()
	 ************************************************************************/
	@Override
	public Dimension getDimension() {
		return this.delegate.getDimension();
	}

}