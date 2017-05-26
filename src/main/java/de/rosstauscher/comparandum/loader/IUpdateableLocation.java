package de.rosstauscher.comparandum.loader;

import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * This interface defines an updateable location for an comparatum element. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public interface IUpdateableLocation {
	
	/*************************************************************************
	 * @param toRender to render and store as comparatum.
	 ************************************************************************/
	
	public void storeAsComparatum(IRenderable toRender);
	

}

