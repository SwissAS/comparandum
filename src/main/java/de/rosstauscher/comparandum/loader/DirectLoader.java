package de.rosstauscher.comparandum.loader;

import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * Loads directly from a given renderable. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2012
 ****************************************************************************/

public class DirectLoader implements IComparatumLoader {

	private final IRenderable expected;
	
	/***************************************************************************
	 * Constructor
	 * @param expected the renderable that should be provided directly
	 **************************************************************************/
	
	public DirectLoader(IRenderable expected) {
		super();
		this.expected = expected;
	}
	
	/***************************************************************************
	 * @see de.rosstauscher.comparandum.loader.IComparatumLoader#load()
	 **************************************************************************/
	@Override
	public IRenderable load() {
		return this.expected;
	}

}