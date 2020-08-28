package de.rosstauscher.comparandum.filter;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.junit.jupiter.api.Test;

import de.rosstauscher.comparandum.junit.Comparandum;
import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * Some unit tests 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

class ExcludeFilterTest {
	
	private static final IRenderable R1 = new IRenderable() {
		@Override
		public void paint(Graphics2D g) {
			g.setColor(Color.BLUE);
			g.drawLine(0, 0, 9, 9);
			g.drawLine(10, 10, 20, 20);
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
			g.drawLine(0, 9, 9, 0);
			g.drawLine(10, 10, 20, 20);
		}

		@Override
		public Dimension getDimension() {
			return new Dimension(20, 20);
		}
	};

	/*************************************************************************
	 * Unit test 
	 ************************************************************************/
	@Test
	void excludedAreaShouldBeIgnored() {
		ExcludeAreaFilter f = new ExcludeAreaFilter(
				new Rectangle(0, 0, 10, 10));
		IRenderable expected = f.applyTo(R1);
		IRenderable actual = f.applyTo(R2);

		Comparandum.assertEquals(expected, actual);
	}
	
	/*************************************************************************
	 * Unit test 
	 ************************************************************************/
	@Test
	void excludedFilterShouldNotExcludeAll() {
		ExcludeAreaFilter f = new ExcludeAreaFilter(
				new Rectangle(0, 0, 9, 9));
		IRenderable expected = f.applyTo(R1);
		IRenderable actual = f.applyTo(R2);
		try {
			Comparandum.assertEquals(expected, actual);
		} catch (AssertionError e) {
			// Must fail for this tests to pass.
			return; 
		}
		fail("Filter does cover to mutch");
	}

}

