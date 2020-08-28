package de.rosstauscher.comparandum.filter;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import org.junit.jupiter.api.Test;

import de.rosstauscher.comparandum.junit.Comparandum;
import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * Some unit tests 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

class ScaleFilterTest {
	
	private static final IRenderable R1 = new IRenderable() {
		@Override
		public void paint(Graphics2D g) {
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, 20, 20);
		}
		
		@Override
		public Dimension getDimension() {
			return new Dimension(20, 20);
		}
	};
	
	private static final IRenderable R2 = new IRenderable() {
		@Override
		public void paint(Graphics2D g) {
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, 10, 10);
		}

		@Override
		public Dimension getDimension() {
			return new Dimension(10, 10);
		}
	};

	/*************************************************************************
	 * Unit test 
	 ************************************************************************/
	@Test
	void scaleFilterShouldWork() {
		ScaleFilter f = new ScaleFilter(20, 20);
		IRenderable actual = f.applyTo(R2);
		Comparandum.assertEquals(R1, actual);
	}
	
	/*************************************************************************
	 * Unit test 
	 ************************************************************************/
	@Test
	void scaledDifferentlyShouldFail() {
		ScaleFilter f = new ScaleFilter(20, ScaleFilter.SAME);
		IRenderable actual = f.applyTo(R2);
		try {
			Comparandum.assertEquals(R1, actual);
		} catch (AssertionError e) {
			// Must fail for this tests to pass.
			return; 
		}
		fail("Filter does not scale correctly");
	}

}

