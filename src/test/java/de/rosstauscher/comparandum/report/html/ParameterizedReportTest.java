package de.rosstauscher.comparandum.report.html;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

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

@RunWith(Parameterized.class)
public class ParameterizedReportTest {

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
	private final String testParam;

	/*************************************************************************
	 * Constructor
	 * @param parameter used as test input
	 ************************************************************************/
	
	public ParameterizedReportTest(String parameter) {
		super();
		this.testParam = parameter;
	}
	
	/*************************************************************************
	 * Setup some stuff.
	 ************************************************************************/
	@BeforeClass
	public static void setup() {
		testFolder = IOUtil.createTempFolder();
		System.setProperty(Comparandum.PROPERTY_REPORT_FOLDER, testFolder.getAbsolutePath());
	}
	
	/*************************************************************************
	 * Cleanup stuff.
	 ************************************************************************/
	@AfterClass
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
	@Parameters
	public static Collection<Object[]> getTestData() {
		return Arrays.asList(
				new Object[][]{
					{"Test1"}, {"Test2"}, {"Test3"}
				});
	}
	
	/*************************************************************************
	 * Test method
	 * @throws IOException on error.
	 ************************************************************************/
	@Test
	public void fileNameShouldIncludeParameter() throws IOException {
		Config testContext = new ConfigBuilder()
				.compareToImage(TestHelper.TEST_FILE1)
				.parameterizedWith(this.testParam)
				.build();
		
		ReportGeneratorUtil.invokeReportGenerator(TestStatus.PASSED, "Test has passed", R1, testContext);
		
		File[] generatedFiles = testFolder.listFiles();
		int found = 0;
		for (File file : generatedFiles) {
			if (file.getName().contains("["+this.testParam+"]")) {
				found++;
			}
		}
		assertEquals("Expected 3 generated files for: "+this.testParam, 3, found);
	}

}

