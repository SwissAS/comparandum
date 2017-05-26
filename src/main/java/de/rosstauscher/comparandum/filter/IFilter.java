package de.rosstauscher.comparandum.filter;

import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * A filter for renderable elements. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public interface IFilter {
	
	/*************************************************************************
	 * Applies the filter to the given renderable object.
	 * @param input the IRenderable to process with this filter.
	 * @return a IRenderable that will render the filtered result.
	 ************************************************************************/
	
	public IRenderable applyTo(IRenderable input);  

}

