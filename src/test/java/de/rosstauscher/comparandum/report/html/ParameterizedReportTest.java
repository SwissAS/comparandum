package de.rosstauscher.comparandum.report.html;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import de.rosstauscher.comparandum.TestHelper;
import de.rosstauscher.comparandum.config.Config;
import de.rosstauscher.comparandum.config.ConfigBuilder;
import de.rosstauscher.comparandum.junit.Comparandum;
import de.rosstauscher.comparandum.render.IRenderable;
import de.rosstauscher.comparandum.report.IReportGenerator.TestStatus;
import de.rosstauscher.comparandum.report.ReportGeneratorUtil;
import de.rosstauscher.comparandum.util.IOUtil;

/*****************************************************************************
 * Unit tests for the HTML report generator for parameterized tests.  
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

class ParameterizedReportTest {

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
		System.setProperty(Comparandum.PROPERTY_REPORT_FOLDER, testFolder.getAbsolutePath());
	}
	
	/*************************************************************************
	 * Cleanup stuff.
	 ************************************************************************/
	@AfterAll
	public static void teardown() {
		System.clearProperty(Comparandum.PROPERTY_REPORT_FOLDER);
		File[] allTempFiles = testFolder.listFiles();
		for (File file : allTempFiles) {
			file.delete();
		}
		testFolder.delete();
	}
	
	/*************************************************************************
	 * @return the test data.
	 ************************************************************************/
	public static Collection<Object[]> getTestData() {
		return Arrays.asList(
				new Object[][]{
					{"Test1"}, {"Test2"}, {"Test3"}
				});
	}
	
	/*************************************************************************
	 * Test method
	 * @param testParam represents one entity of getTestData
	 * @throws IOException on error.
	 ************************************************************************/
	@ParameterizedTest
	@MethodSource("getTestData")
	public void fileNameShouldIncludeParameter(String testParam) throws IOException {
		Config testContext = new ConfigBuilder()
				.compareToImage(TestHelper.TEST_FILE1)
				.parameterizedWith(testParam)
				.build();
		
		ReportGeneratorUtil.invokeReportGenerator(TestStatus.PASSED, "Test has passed", R1, testContext);
		
		File[] generatedFiles = testFolder.listFiles();
		int found = 0;
		for (File file : generatedFiles) {
			if (file.getName().contains("[" + testParam + "]")) {
				found++;
			}
		}
		assertEquals(3, found, "Expected 3 generated files for: "+ testParam);
	}

}

