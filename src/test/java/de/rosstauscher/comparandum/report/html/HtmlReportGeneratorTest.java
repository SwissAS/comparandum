package de.rosstauscher.comparandum.report.html;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.rosstauscher.comparandum.TestHelper;
import de.rosstauscher.comparandum.config.Config;
import de.rosstauscher.comparandum.config.ConfigBuilder;
import de.rosstauscher.comparandum.render.IRenderable;
import de.rosstauscher.comparandum.report.IReportGenerator.TestStatus;
import de.rosstauscher.comparandum.report.ReportGeneratorUtil;
import de.rosstauscher.comparandum.util.IOUtil;

/*****************************************************************************
 * Unit tests for the HTML report generator.  
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

class HtmlReportGeneratorTest {

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

	private static File testFolder;
	
	/*************************************************************************
	 * Setup some stuff.
	 ************************************************************************/
	@BeforeAll
	public static void setup() {
		testFolder = IOUtil.createTempFolder();
	}
	
	/*************************************************************************
	 * Cleanup stuff.
	 ************************************************************************/
	@AfterAll
	public static void teardown() {
		File[] allTempFiles = testFolder.listFiles();
		for (File file : allTempFiles) {
			file.delete();
		}
		testFolder.delete();
	}
	
	/*************************************************************************
	 * Test method
	 * @throws IOException on error.
	 ************************************************************************/
	@Test
	public void testGenerateTestReport() throws IOException {
		HtmlReportGenerator generator = new HtmlReportGenerator();
		generator.setOutputFolder(testFolder);
		Config testConfig = new ConfigBuilder().compareToImage(TestHelper.TEST_FILE1).build();
		generator.generateTestReport(TestStatus.PASSED, "Test has passed", R1, testConfig);
		
		String testName = ReportGeneratorUtil.buildTestName(
				testConfig.getTestClassName(), 
				testConfig.getTestMethodName(), 
				testConfig.getTestMethodParameter()
				);		
		assertTrue(new File(testFolder, testName+".html").exists());
		assertTrue(new File(testFolder, testName+"-actual.png").exists());
		assertTrue(new File(testFolder, testName+"-expected.png").exists());
	}
	
	/*************************************************************************
	 * Test method
	 * @throws IOException on error.
	 ************************************************************************/
	@Test
	void testIndexGeneration() throws IOException {
		IndexGenerator generator = new IndexGenerator();
		generator.generateIndexFile(testFolder);
		assertTrue(new File(testFolder, "index.html").length() > 0);
	}

}

