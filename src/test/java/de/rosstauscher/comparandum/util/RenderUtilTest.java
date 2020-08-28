package de.rosstauscher.comparandum.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * Unit test for util class 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

class RenderUtilTest {
	
	/** 
	 * Helper class for the tests 
	 **/
	
	private static final IRenderable R1 = new IRenderable() {
		@Override
		public void paint(Graphics2D g) {
			Dimension d = getDimension();
			g.setColor(Color.BLUE);
			g.drawLine(0, 0, d.width, d.height);
		}
		
		@Override
		public Dimension getDimension() {
			return new Dimension(32, 32);
		}
	};
	
	/*************************************************************************
	 * Test method
	 ************************************************************************/
	@Test
	void testRenderToImage() {
		BufferedImage image = RenderUtil.renderToImage(R1);
		assertEquals(32, image.getWidth());
		assertEquals(32, image.getHeight());
	}

	/*************************************************************************
	 * Test method
	 * @throws IOException on error. 
	 ************************************************************************/
	@Test
	void testRenderToDisk() throws IOException {
		File testFile = File.createTempFile("comparandum", ".png");
		RenderUtil.renderToDisk(R1, testFile);
		assertTrue(testFile.exists());
		assertTrue(testFile.length() > 0);
	}

}

