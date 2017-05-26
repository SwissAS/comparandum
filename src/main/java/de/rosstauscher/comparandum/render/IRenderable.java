package de.rosstauscher.comparandum.render;

import java.awt.Dimension;
import java.awt.Graphics2D;

/*****************************************************************************
 * Interface for all renderable objects.
 *  
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public interface IRenderable {
	
	/*************************************************************************
	 * Paints this renderable element to the given graphics context.
	 * @param g the graphics to paint against.
	 ************************************************************************/
	
	public void paint(Graphics2D g);

	/*************************************************************************
	 * Gets the dimension the object has.
	 * @return the dimension of the renderable element.
	 ************************************************************************/
	
	public Dimension getDimension();
	

}

