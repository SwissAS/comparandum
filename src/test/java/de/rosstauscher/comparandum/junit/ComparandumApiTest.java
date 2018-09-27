package de.rosstauscher.comparandum.junit;

import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.net.MalformedURLException;

import org.junit.Test;

import de.rosstauscher.comparandum.TestHelper;
import de.rosstauscher.comparandum.config.Config;
import de.rosstauscher.comparandum.config.ConfigBuilder;
import de.rosstauscher.comparandum.loader.ImageFileLoader;
import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * Some unit tests 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class ComparandumApiTest {

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
	
	/** 
	 * Helper class for the tests 
	 **/
	
	private static final IRenderable R2 = new IRenderable() {
		@Override
		public void paint(Graphics2D g) {
			Dimension d = getDimension();
			g.setColor(Color.BLUE);
			g.drawLine(0, d.height, d.width, 0);
		}

		@Override
		public Dimension getDimension() {
			return new Dimension(32, 32);
		}
	};
	
	/*************************************************************************
	 * Unit test 
	 ************************************************************************/
	@Test
	public void assertEqualsShouldWork() {
		Comparandum.assertEquals(R1, R1);
	}
	
	/*************************************************************************
	 * Unit test 
	 ************************************************************************/
	@Test
	public void assertPhashShouldWork() {
		Config config = new ConfigBuilder()
			.compareThis(R1)
			.compareTo(R1)
			.compareHashes(0)
			.build();
		
		Comparandum.assertEquals(config);
	}
	
	/*************************************************************************
	 * Unit test 
	 ************************************************************************/
	@Test
	public void assertPhashShouldFail() {

		Config config = new ConfigBuilder()
			.compareThis(R1)
			.compareTo(R2)
			.compareHashes(2)
			.build();
		
		try {
			Comparandum.assertEquals(config);
		} catch (AssertionError e) {
			// Expected error failure message
			if (e.getMessage().contains("pHash distance is")) {
				return;
			}
		}
		fail("Expected failure but difference was accepted?");
	}


	/*************************************************************************
	 * Unit test 
	 ************************************************************************/
	@Test
	public void assertEqualsShouldFailOnDifference() {
		try {
			Comparandum.assertEquals(R1, R2);
		} catch (AssertionError e) {
			// This must fail for the test to pass
			return; 
		}
		org.junit.Assert.fail("Assert is expected to fail.");
	}
	
	/*************************************************************************
	 * Unit test 
	 * @throws MalformedURLException on error. 
	 ************************************************************************/
	@Test
	public void exlucdedAreaShouldBeIgnoredOnCompare() throws MalformedURLException {
		IRenderable actual = new ImageFileLoader(
				TestHelper.TEST_FILE2).load(); 

		Config config = new ConfigBuilder()
		.compareThis(actual)
		.compareToImage(TestHelper.TEST_FILE1)
		.ignore(0, 0, 20, 20)
		.ignore(20, 20, 40, 40)
		.build();
		
		Comparandum.assertEquals(config);
	}

}

