package de.rosstauscher.comparandum.report.xml;

import java.io.File;
import java.io.IOException;

import de.rosstauscher.comparandum.report.html.AbstractReportGenerator;
import de.rosstauscher.comparandum.util.IOUtil;

/*****************************************************************************
 * A report generator that will produce XML files for the test run results. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class XmlReportGenerator extends AbstractReportGenerator {

	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	public XmlReportGenerator() {
		super();
	}

	/*************************************************************************
	 * generateTestReportPage
	 * @see de.rosstauscher.comparandum.report.html.AbstractReportGenerator#generateTestReportPage()
	 ************************************************************************/
	@Override
	protected void generateTestReportPage() throws IOException {
		String xmlTemplate = loadTestPageTemplate();
		xmlTemplate = xmlTemplate.replace("${TEST_NAME}", buildDisplayName());
		xmlTemplate = xmlTemplate.replace("${TEST_MSG}", this.testMessage);
		xmlTemplate = xmlTemplate.replace("${TEST_STATUS}", this.testStatus.toString());
		xmlTemplate = xmlTemplate.replace("${ACTUAL_IMG}", this.actualImageName);
		xmlTemplate = xmlTemplate.replace("${EXPECTED_IMG}", this.expectedImageName);

		// TODO rossi 01.12.2011 this all needs to be finished.
		
		
//		String timeStamp = getTimeStampFormat().format(new Date());
//		xmlTemplate = xmlTemplate.replace("${TIME_STAMP}", timeStamp);
		
		saveTestPageToReportFolder(xmlTemplate);	}

	/*************************************************************************
	 * Saves the XML report for the given test.
	 * @param testName of the test exectued.
	 * @param xmlTemplate to write.
	 * @throws IOException on write error. 
	 ************************************************************************/
	
	private void saveTestPageToReportFolder(String xmlTemplate) throws IOException {
		File reportFile = new File(this.outputFolder, this.testName+".xml");
		IOUtil.writeTextFile(xmlTemplate, reportFile);
	}

	/*************************************************************************
	 * Loads the template for the the test result XML block.
	 * @return the template as string.
	 * @throws IOException on read error. 
	 ************************************************************************/
	
	private String loadTestPageTemplate() throws IOException {
		return IOUtil.readTextFileFromResource(getClass(), "resources/test_page.xml");
	}

}

