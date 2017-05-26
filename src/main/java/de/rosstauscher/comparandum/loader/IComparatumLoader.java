package de.rosstauscher.comparandum.loader;

import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * Loads a comparatum from an external resource like a image file or a 
 * render instruction file.  
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public interface IComparatumLoader {
	
	/*************************************************************************
	 * Loads a reference IRenderable object from an external resource. 
	 * @return the loaded renderable object.
	 ************************************************************************/
	
	public IRenderable load();

}

