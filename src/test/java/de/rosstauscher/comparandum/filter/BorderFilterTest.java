package de.rosstauscher.comparandum.filter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.rosstauscher.comparandum.junit.Comparandum;
import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * Some unit tests 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

class BorderFilterTest {
	
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
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 40, 40);
			g.setColor(Color.BLUE);
			g.fillRect(10, 10, 20, 20);
		}

		@Override
		public Dimension getDimension() {
			return new Dimension(40, 40);
		}
	};
	
	private static final IRenderable R3 = new IRenderable() {
		@Override
		public void paint(Graphics2D g) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 40, 40);
			g.setColor(Color.BLUE);
			g.fillRect(5, 5, 20, 20);
		}

		@Override
		public Dimension getDimension() {
			return new Dimension(40, 40);
		}
	};

	/*************************************************************************
	 * Unit test 
	 ************************************************************************/
	@Test
	void borderFilterShouldInfluenceSize() {
		BorderFilter f = new BorderFilter(10, 10, 10, 10, Color.WHITE);
		IRenderable actual = f.applyTo(R1);
		Assertions.assertEquals(10+10+R1.getDimension().width, actual.getDimension().width);
		Assertions.assertEquals(10+10+R1.getDimension().height, actual.getDimension().height);
	}
	
	/*************************************************************************
	 * Unit test 
	 ************************************************************************/
	@Test
	void borderShouldBeWhite() {
		BorderFilter f = new BorderFilter(10, 10, 10, 10, Color.WHITE);
		IRenderable actual = f.applyTo(R1);
		Comparandum.assertEquals(R2, actual);
	}
	
	/*************************************************************************
	 * Unit test 
	 ************************************************************************/
	@Test
	void asyncBorderShouldBeWhite() {
		BorderFilter f = new BorderFilter(5, 15, 5, 15, Color.WHITE);
		IRenderable actual = f.applyTo(R1);
		Comparandum.assertEquals(R3, actual);
	}

}

