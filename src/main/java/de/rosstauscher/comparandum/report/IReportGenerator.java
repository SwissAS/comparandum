package de.rosstauscher.comparandum.report;

import java.io.File;
import java.io.IOException;

import de.rosstauscher.comparandum.config.Config;
import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * This interface defines a report generator. 
 * This one is used to produce reports for the results of a test run.
 * There are implementations available for HTML reports and XML reporting.
 *   
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public interface IReportGenerator {
	
	/*****************************************************************************
	 * Enum for test result / status. 
	 ****************************************************************************/

	public enum TestStatus {
		PASSED, // set when the test has passed successfully
		FAILED  // used when the test has failed
	}

	/*************************************************************************
	 * Called for every single test execution.
	 * @param status the status of the test result. Either PASSED or FAILED.
	 * @param message the error message or empty in case the test has passed.
	 * @param renderable the renderable image 
	 * @param testConfig the test context containing additional configuration.
	 * @throws IOException on error writing the report file. 
	 ************************************************************************/
	
	public void generateTestReport(TestStatus status, String message, IRenderable renderable, Config testConfig) throws IOException;

	/*************************************************************************
	 * Sets the output folder for the report generator.
	 * @param outputFolder the folder to generate all files in.
	 ************************************************************************/
	
	public void setOutputFolder(File outputFolder);

}
