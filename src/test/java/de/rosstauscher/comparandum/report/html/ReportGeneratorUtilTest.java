package de.rosstauscher.comparandum.report.html;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.IOException;

import org.junit.Test;

import de.rosstauscher.comparandum.TestHelper;
import de.rosstauscher.comparandum.config.Config;
import de.rosstauscher.comparandum.config.ConfigBuilder;
import de.rosstauscher.comparandum.junit.Comparandum;
import de.rosstauscher.comparandum.render.IRenderable;
import de.rosstauscher.comparandum.report.IReportGenerator.TestStatus;
import de.rosstauscher.comparandum.report.ReportGeneratorUtil;

/*****************************************************************************
 * Unit tests for the ReportGeneratorUtil.  
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class ReportGeneratorUtilTest {

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
	
	/*****************************************************************************
	 * Helper class for the tests 
	 ****************************************************************************/

	public static class TestGenerator extends AbstractReportGenerator {

		static boolean invoked = false;
		
		/*************************************************************************
		 * generateTestReportPage
		 * @see de.rosstauscher.comparandum.report.html.AbstractReportGenerator#generateTestReportPage()
		 ************************************************************************/
		@Override
		protected void generateTestReportPage() throws IOException {
			invoked = true;
		}
	}

	/*************************************************************************
	 * Test method
	 * @throws IOException on error.
	 ************************************************************************/
	@Test
	public void testGenerateTestReport() throws IOException {
		System.setProperty(Comparandum.PROPERTY_REPORT_GENERATOR, TestGenerator.class.getName());
		
		Config testContext = new ConfigBuilder().compareToImage(TestHelper.TEST_FILE1).build();
		ReportGeneratorUtil.invokeReportGenerator(TestStatus.PASSED, "Test has passed", R1, testContext);

		assertTrue(TestGenerator.invoked);
	}
	
	/*************************************************************************
	 * Test method
	 * @throws IOException on error.
	 ************************************************************************/
	@Test
	public void buildTestNameWithoutPackageShouldWork() throws IOException {
		String actual = ReportGeneratorUtil.buildTestName("ABC", "method", "123");
		assertEquals("ABC__method[123]", actual);
	}
	
	/*************************************************************************
	 * Test method
	 * @throws IOException on error.
	 ************************************************************************/
	@Test
	public void buildTestNameWithPackageShouldWork() throws IOException {
		String actual = ReportGeneratorUtil.buildTestName("a.b.c.ClassName", "method", "123");
		assertEquals("a_b_c_ClassName__method[123]", actual);
	}
	
	/*************************************************************************
	 * Test method
	 * @throws IOException on error.
	 ************************************************************************/
	@Test
	public void buildTestNameWithoutParameterShouldWork() throws IOException {
		String actual = ReportGeneratorUtil.buildTestName("ABC", "method", null);
		assertEquals("ABC__method", actual);
	}
	

}

