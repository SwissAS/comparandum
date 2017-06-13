package de.rosstauscher.comparandum.report.html;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import de.rosstauscher.comparandum.report.IReportGenerator;
import de.rosstauscher.comparandum.util.ComparandumException;
import de.rosstauscher.comparandum.util.IOUtil;

/*****************************************************************************
 * This class can be used to generate a HTML report of all executed tests.
 * To generate the HTML report you have to define the following system properties
 * before you run your tests. 
 * To activate the HTML report you have to define the following property.
 * 
 * <code>de.rosstauscher.comparandum.reportGenerator=HTML</code>
 * 
 * You have to override the operation mode of comparandum to either:
 * <code>de.rosstauscher.comparandum.mode=REPORT_ONLY</code> or
 * <code>de.rosstauscher.comparandum.mode=REPORT_AND_COMPARE</code>
 * 
 * Additionally you can to define a property to specify the output folder 
 * for the report:
 * 
 * <code>de.rosstauscher.comparandum.reportFolder=comparandum_report</code>
 *  
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class HtmlReportGenerator extends AbstractReportGenerator implements IReportGenerator {
	
	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	public HtmlReportGenerator() {
		super();
	}
	
	/*************************************************************************
	 * generateTestReportPage
	 * @see de.rosstauscher.comparandum.report.html.AbstractReportGenerator#generateTestReportPage()
	 ************************************************************************/
	@Override
	protected void generateTestReportPage() throws IOException {
		String htmlPageTemplate = loadTestPageTemplate();
		
		htmlPageTemplate = htmlPageTemplate.replace("${TEST_NAME}", buildDisplayName());
		htmlPageTemplate = htmlPageTemplate.replace("${TEST_MSG}", this.testMessage);
		htmlPageTemplate = htmlPageTemplate.replace("${TEST_STATUS}", this.testStatus.toString());

		Dimension actualSize = this.renderable.getDimension();
		htmlPageTemplate = htmlPageTemplate.replace("${ACTUAL_IMG}", this.actualImageName);
		htmlPageTemplate = htmlPageTemplate.replace("${ACTUAL_WIDTH}", Integer.toString(actualSize.width));
		htmlPageTemplate = htmlPageTemplate.replace("${ACTUAL_HEIGHT}", Integer.toString(actualSize.height));

		Dimension expectedSize = this.testConfig.getComparatum().getDimension();
		htmlPageTemplate = htmlPageTemplate.replace("${EXPECTED_IMG}", this.expectedImageName);
		htmlPageTemplate = htmlPageTemplate.replace("${EXPECTED_WIDTH}", Integer.toString(expectedSize.width));
		htmlPageTemplate = htmlPageTemplate.replace("${EXPECTED_HEIGHT}", Integer.toString(expectedSize.height));
		
		String timeStamp = getTimeStampFormat().format(new Date());
		htmlPageTemplate = htmlPageTemplate.replace("${TIME_STAMP}", timeStamp);
		
		saveTestPageToReportFolder(htmlPageTemplate);
		copyAdditionalResources();
	}

	/*************************************************************************
	 * Copy css, images and scrips to output folder.
	 ************************************************************************/
	
	private void copyAdditionalResources() {
		try {
			copyFileIfNotExisting(this.outputFolder, "comparandum.css");
			copyFileIfNotExisting(this.outputFolder, "js/handle.gif");
			copyFileIfNotExisting(this.outputFolder, "js/jquery.beforeafter-1.4.js");
			copyFileIfNotExisting(this.outputFolder, "js/jquery-1.6.1.min.js");
			copyFileIfNotExisting(this.outputFolder, "js/jquery-ui-1.8.13.custom.min.js");
			copyFileIfNotExisting(this.outputFolder, "js/rt-small.png");
			copyFileIfNotExisting(this.outputFolder, "js/lt-small.png");
		} catch (IOException e) {
			throw new ComparandumException("Error copying report resource to output folder.",e);
		}
	}

	/*************************************************************************
	 * Copies the given file from the resources to the output folder.
	 * @param outputFolder 
	 * @param resourceName to copy
	 * @throws IOException on error. 
	 ************************************************************************/
	
	public static void copyFileIfNotExisting(File outputFolder, String resourceName) throws IOException {
		File destinationFile = new File(outputFolder, resourceName);
		if (!destinationFile.exists()) {
			destinationFile.getParentFile().mkdirs();
			IOUtil.copyResourceToFile(HtmlReportGenerator.class, "resources/"+resourceName, destinationFile);
		}
	}

	/*************************************************************************
	 * @return the DateFormat used to format the time stamp information.
	 ************************************************************************/
	
	private DateFormat getTimeStampFormat() {
		return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.LONG);
	}

	/*************************************************************************
	 * Saves the given HTML template to the destination folder.
	 * @param htmlPageTemplate the HTML page for that test.
	 * @throws IOException on write error.
	 ************************************************************************/
	
	protected void saveTestPageToReportFolder(String htmlPageTemplate) throws IOException {
		File reportFile = new File(this.outputFolder, this.testName+".html");
		IOUtil.writeTextFile(htmlPageTemplate, reportFile);
	}

	/*************************************************************************
	 * Loads the template for the HTML page.
	 * @return the HTML template.
	 * @throws IOException on read error.
	 ************************************************************************/
	
	protected String loadTestPageTemplate() throws IOException {
		return IOUtil.readTextFileFromResource(getClass(), "resources/test_page.html");
	}

}

