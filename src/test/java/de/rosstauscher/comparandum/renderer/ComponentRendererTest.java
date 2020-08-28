package de.rosstauscher.comparandum.renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;

import javax.swing.JLabel;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import de.rosstauscher.comparandum.junit.Comparandum;
import de.rosstauscher.comparandum.render.ComponentRenderable;


/*****************************************************************************
 * Some unit tests. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

class ComponentRendererTest {
	
	/*************************************************************************
	 * Unit Test
	 ************************************************************************/
	@Test
	void paintComponentRenderableShouldWork() {
		Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
		JLabel c = new JLabel("Hello World");
		c.setPreferredSize(new Dimension(80, 20));
		ComponentRenderable r = new ComponentRenderable(c);
		
		Comparandum.assertEquals(r, r);
		assertEquals(80, r.getDimension().width);
		assertEquals(20, r.getDimension().height);
	}
	

}

