package de.rosstauscher.comparandum.report.html;

import java.io.File;
import java.io.IOException;

import de.rosstauscher.comparandum.config.Config;
import de.rosstauscher.comparandum.render.IRenderable;
import de.rosstauscher.comparandum.report.IReportGenerator;
import de.rosstauscher.comparandum.report.ReportGeneratorUtil;
import de.rosstauscher.comparandum.util.RenderUtil;

/*****************************************************************************
 * Base class for report generator implementations.  
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public abstract class AbstractReportGenerator implements IReportGenerator {

	protected File outputFolder;
	
	protected Config testConfig;
	protected IRenderable renderable;

	protected TestStatus testStatus;
	protected String testMessage;
	
	protected String testName;

	protected String actualImageName;
	protected String expectedImageName;

	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	public AbstractReportGenerator() {
		super();
	}

	/*************************************************************************
	 * setOutputFolder
	 * @see de.rosstauscher.comparandum.report.IReportGenerator#setOutputFolder(java.io.File)
	 ************************************************************************/
	@Override
	public final void setOutputFolder(File outputFolder) {
		this.outputFolder = outputFolder; 
	}
	
	/*************************************************************************
	 * generateTestReport
	 * @see de.rosstauscher.comparandum.report.IReportGenerator#generateTestReport(de.rosstauscher.comparandum.report.IReportGenerator.TestStatus, java.lang.String, de.rosstauscher.comparandum.render.IRenderable, de.rosstauscher.comparandum.config.Config)
	 ************************************************************************/
	@Override
	public void generateTestReport(TestStatus status, String message, IRenderable renderable, Config testConfig) throws IOException {

		this.renderable = renderable;
		this.testConfig = testConfig;
		this.testStatus = status;
		this.testMessage = message;
		this.testName = buildTestName();
		
		ensureReportFolderExists();
		renderActualImageToReportFolder();
		renderComparatumToReportFolder();
		generateTestReportPage();
		updateSummaryPage();
	}

	/*************************************************************************
	 * Builds a unique name for output files generated for the current test invocation.
	 * @return the unique name.
	 ************************************************************************/
	
	protected String buildTestName() {
		return ReportGeneratorUtil.buildTestName(
				this.testConfig.getTestClassName(), 
				this.testConfig.getTestMethodName(), 
				this.testConfig.getTestMethodParameter()
				);
	}
	
	/*************************************************************************
	 * Builds the display name for the report.
	 * @return the generated name.
	 ************************************************************************/
	
	protected String buildDisplayName() {
		StringBuilder testDisplayName = new StringBuilder();
		testDisplayName.append(this.testConfig.getTestClassName());
		testDisplayName.append("#");
		testDisplayName.append(this.testConfig.getTestMethodName());
		testDisplayName.append("(");

		String testMethodParameter = this.testConfig.getTestMethodParameter();
		if (testMethodParameter != null) {
			testDisplayName.append(testMethodParameter);
		}
		
		testDisplayName.append(")");
		return testDisplayName.toString();
	}

	/*************************************************************************
	 * Overwrite to generate the test result page.
	 * @throws IOException on error
	 ************************************************************************/
	
	protected abstract void generateTestReportPage() throws IOException;

	/*************************************************************************
	 * Make sure that the destination folder and all subfolders exist.
	 * @throws IOException on write error.
	 ************************************************************************/
	
	protected void ensureReportFolderExists() throws IOException {
		if (!this.outputFolder.exists() && !this.outputFolder.mkdirs()) {
			throw new IOException("Can't create report folder: "+this.outputFolder);
		}
	}

	/*************************************************************************
	 * Renders the comparatum to an image file.
	 * @throws IOException on write error.
	 ************************************************************************/
	
	protected void renderComparatumToReportFolder() throws IOException {
		this.expectedImageName = this.testName+"-expected.png";
		File file = new File(this.outputFolder, this.expectedImageName);
		RenderUtil.renderToDisk(this.testConfig.getComparatum(), file);
	}

	/*************************************************************************
	 * Renders the renderable objec to an image file.
	 * @throws IOException on write error.
	 ************************************************************************/
	
	protected void renderActualImageToReportFolder() throws IOException {
		this.actualImageName = this.testName+"-actual.png";
		File file = new File(this.outputFolder, this.actualImageName);
		RenderUtil.renderToDisk(this.renderable, file);
	}
	
	/*************************************************************************
	 * Build or append to the SummaryPage page.
	 ************************************************************************/
	
	protected void updateSummaryPage() {
		// Not used yet.
	}

}
