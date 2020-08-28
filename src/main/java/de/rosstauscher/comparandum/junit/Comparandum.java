package de.rosstauscher.comparandum.junit;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.Arrays;

import de.rosstauscher.comparandum.config.Config;
import de.rosstauscher.comparandum.filter.FilterList;
import de.rosstauscher.comparandum.render.IRenderable;
import de.rosstauscher.comparandum.report.IReportGenerator.TestStatus;
import de.rosstauscher.comparandum.report.ReportGeneratorUtil;
import de.rosstauscher.comparandum.util.ImagePHash;
import de.rosstauscher.comparandum.util.RenderUtil;

/*****************************************************************************
 * This class provides low level API methods for direct use in unit tests.
 * You can use one of the assert methods to compare an painting code with a 
 * given reference (comparatum). You can compare directly two 
 * IRenderable objects or a IRenderable object with a predefined test 
 * configuration.
 *  
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class Comparandum {
	
	/** 
	 * A System property to override the default mode of operation
	 * @see de.rosstauscher.comparandum.config.OperationMode for a list of allowed options. 
	 **/
	public static final String PROPERTY_MODE_OVERRIDE = "de.rosstauscher.comparandum.mode";
	
	/**
	 * With this property to can define the report generator to use. 
	 * This property is only applicable for one of the "REPORT" operation modes.
	 * Valid values are define in the ReportFormat enumeration.  
	 **/
	public static final String PROPERTY_REPORT_GENERATOR 	= "de.rosstauscher.comparandum.reportGenerator";
	
	
	/**
	 * Set this property to define where the report should be generated.
	 * This property is only applicable for one of the "REPORT" operation modes.
	 * If not set the report will be generated in the folder "comparandum_report" 
	 * in the current working directory.
	 **/
	public static final String PROPERTY_REPORT_FOLDER 		= "de.rosstauscher.comparandum.reportFolder";

	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	private Comparandum() {
		super();
	}
	
	/*************************************************************************
	 * Compares two renderable objects with each other.
	 * This method fails when the provided objects to not produce the same visual output.
	 * @param expected the reference to compare to.
	 * @param actual the generated renderable object to compare.
	 ************************************************************************/

	public static void assertEquals(IRenderable expected, IRenderable actual) {
		assertEquals(expected, actual, "");
	}

	/*************************************************************************
	 * @param expected
	 * @param actual
	 * @param testPrefix
	 ************************************************************************/
	
	private static void assertEquals(IRenderable expected, IRenderable actual, String testPrefix) {
		assertDimensionEquals(expected, actual, "");
		BufferedImage b1 = RenderUtil.renderToImage(expected);
		BufferedImage b2 = RenderUtil.renderToImage(actual);
		assertEquals(b1, b2, "");
	}

	/*************************************************************************
	 * @param expected
	 * @param actual
	 * @param testPrefix
	 ************************************************************************/
	
	private static void assertEquals(BufferedImage expected, BufferedImage actual, String testPrefix) {
		Raster d1 = expected.getData();
		Raster d2 = actual.getData();
		int[] p1 = null; 
		int[] p2 = null;
		for (int y = 0; y < d1.getHeight(); y++) {
			for (int x = 0; x < d1.getWidth(); x++) {
				p1 = d1.getPixel(x, y, p1);
				p2 = d2.getPixel(x, y, p2);
				if (!Arrays.equals(p1, p2)) {
					fail(testPrefix+"Pixels differ at "+x+" / "+ y);
				}
			}
		}
	}

	/*************************************************************************
	 * @param expected
	 * @param actual
	 * @param testPrefix
	 ************************************************************************/
	
	private static void assertDimensionEquals(IRenderable expected, IRenderable actual, String testPrefix) {
		Dimension d1 = expected.getDimension();
		Dimension d2 = actual.getDimension();
		if (!d1.equals(d2)) {
			fail("Dimensions do not match expected "+ d1 + " but was " + d2);
		}
	}
	
	/*************************************************************************
	 * Compares a given IRenderable object with a test configuration that 
	 * defines how to load the reference to compare to and may also define
	 * filters and additional context information for the comparison.
	 * @param config that defines the comparison context.
	 ************************************************************************/
	
	public static void assertEquals(Config config) {
		IRenderable actual = config.getComparandum();
		switch (config.getOperationMode()) {
			case UPDATE:
				updateComparatum(actual, config);
				break;
			case COMPARE:
				compareWithComparatum(actual, config);
				break;
			case REPORT_AND_COMPARE:
				generateReport(actual, config, true);
				break;
			case REPORT_ONLY:
				generateReport(actual, config, false);
				break;
		}
	}

	/*************************************************************************
	 * @param renderable to use to create or update the comparatum.
	 * @param config the context to update with the current renderable object.
	 ************************************************************************/
	
	private static void updateComparatum(IRenderable renderable, Config config) {
		config.updateComparatum(renderable);
	}

	/*************************************************************************
	 * @param renderable
	 * @param config
	 ************************************************************************/
	
	private static void compareWithComparatum(IRenderable renderable, Config config) {
		IRenderable comparatum = config.getComparatum();
		FilterList comparatumFilter = config.getComparatumFilter();
		IRenderable expected =  comparatumFilter.applyTo(comparatum);

		FilterList inputFilter = config.getFilter();
		IRenderable actual = inputFilter.applyTo(renderable);
		
		String testPrefix = config.getTestMethodParameter() == null 
				? "": "["+config.getTestMethodParameter()+"] ";
		
		if (config.getAcceptedDistance() >= 0) {
			assertSimilar(expected, actual, config.getAcceptedDistance(), testPrefix);
		} else {
			assertEquals(expected, actual, testPrefix);
		}
	}
	
	/*************************************************************************
	 * @param expected
	 * @param actual
	 * @param acceptedDistance
	 * @param testPrefix
	 ************************************************************************/

	private static void assertSimilar(IRenderable expected, IRenderable actual, int acceptedDistance, String testPrefix) {
		ImagePHash pHash = new ImagePHash();
		
		BufferedImage b1 = RenderUtil.renderToImage(expected);
		BufferedImage b2 = RenderUtil.renderToImage(actual);
		
		String h1 = pHash.getHash(b1);
		String h2 = pHash.getHash(b2);
		int dist = pHash.distance(h1, h2);
		if (dist > acceptedDistance) {
			fail(testPrefix+" pHash distance is "+dist+" allowed is "+ acceptedDistance);
		}
	}

	/*************************************************************************
	 * @param renderable
	 * @param config
	 * @param rethrow
	 ************************************************************************/
	
	private static void generateReport(IRenderable renderable, Config config, boolean rethrow) {
		try {
			compareWithComparatum(renderable, config);
			ReportGeneratorUtil.invokeReportGenerator(TestStatus.PASSED, "\"Actual\" and \"Expected\" look the same.", renderable, config);
		} catch (AssertionError e) {
			ReportGeneratorUtil.invokeReportGenerator(TestStatus.FAILED, e.getMessage(), renderable, config);
			if (rethrow) {
				throw e;
			}
		}
	}

}

