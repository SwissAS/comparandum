package de.rosstauscher.comparandum.renderer;

import java.awt.Dimension;

import javax.swing.JLabel;

import org.junit.Test;

import de.rosstauscher.comparandum.junit.Comparandum;
import de.rosstauscher.comparandum.render.ComponentRenderable;


/*****************************************************************************
 * Some unit tests. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class ComponentRendererTest {
	
	/*************************************************************************
	 * Unit Test
	 ************************************************************************/
	@Test
	public void paintComponentRenderableShouldWork() {
		JLabel c = new JLabel("Hello World");
		c.setPreferredSize(new Dimension(80, 20));
		ComponentRenderable r = new ComponentRenderable(c);
		
		Comparandum.assertEquals(r, r);
		org.junit.Assert.assertEquals(80, r.getDimension().width);
		org.junit.Assert.assertEquals(20, r.getDimension().height);
	}
	

}

