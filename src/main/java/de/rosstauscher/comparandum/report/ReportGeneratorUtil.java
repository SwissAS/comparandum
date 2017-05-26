package de.rosstauscher.comparandum.report;

import java.io.File;
import java.io.IOException;

import de.rosstauscher.comparandum.config.Config;
import de.rosstauscher.comparandum.junit.Comparandum;
import de.rosstauscher.comparandum.render.IRenderable;
import de.rosstauscher.comparandum.report.IReportGenerator.TestStatus;
import de.rosstauscher.comparandum.util.ComparandumException;

/*****************************************************************************
 * Some helper methods to build a report for a given test execution. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class ReportGeneratorUtil {
	
	private static final String DEFAULT_FORMAT = ReportFormat.HTML.name();
	private static final String DEFAULT_FOLDER = "comparandum_report";

	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	private ReportGeneratorUtil() {
		super();
	}
	
	/*************************************************************************
	 * Invokes a report generator to generate a report page for the given test run results.
	 * @param status the status of the test run: FAILED, PASSED
	 * @param message an error message in case the test has failed, else ""
	 * @param renderable that was compared to the comparatum.
	 * @param config used during the test run.
	 * @throws ComparandumException on error generating the report. 
	 ************************************************************************/
	
	public static void invokeReportGenerator(TestStatus status, String message, IRenderable renderable, Config config) {
		try {
			IReportGenerator gen = buildReportGenerator();
			gen.setOutputFolder(getOuputFolder());
			gen.generateTestReport(status, message, renderable, config);
		} catch (IOException e) {
			throw new ComparandumException("Error writing report file: "+e.getMessage(), e);
		}
	}

	/*************************************************************************
	 * Builds a report generator instance.
	 * @return a IReportGenerator implementation.
	 ************************************************************************/
	
	private static IReportGenerator buildReportGenerator() {
		String reportGeneratorType = System.getProperty(Comparandum.PROPERTY_REPORT_GENERATOR, DEFAULT_FORMAT);
		String className = reportGeneratorType.trim(); 
		
		String possibleCode = reportGeneratorType.toUpperCase().trim();
		for (ReportFormat format : ReportFormat.values()) {
			if (format.name().equals(possibleCode)) {
				className = format.getReportGeneratorClass();
				break;
			}
		}
		return instanciateFromClassName(className);
	}

	/*************************************************************************
	 * Gets the output folder for generating reports.
	 * @return the folder to use to generate the report.
	 ************************************************************************/
	
	private static File getOuputFolder() {
		return new File(System.getProperty(
				Comparandum.PROPERTY_REPORT_FOLDER, DEFAULT_FOLDER));
	}

	/*************************************************************************
	 * Instanciates a IReportGenerator from a given full qualified class name.
	 * @param fqcn the full qualified class name.
	 * @return a IReportGenerator instance.
	 * @throws InstantiationException if reportGeneratorType is not a valid class name.
	 * @throws IllegalAccessException if reportGeneratorType is not a valid class name.
	 * @throws ClassNotFoundException if reportGeneratorType is not a valid class name.
	 ************************************************************************/
	
	private static IReportGenerator instanciateFromClassName(String fqcn) {
		try {
			return (IReportGenerator) Class.forName(fqcn).newInstance();
		} catch (InstantiationException e) {
			throw new ComparandumException("Not a valid report generator class or format name."+fqcn, e);
		} catch (IllegalAccessException e) {
			throw new ComparandumException("Not a valid report generator class or format name."+fqcn, e);
		} catch (ClassNotFoundException e) {
			throw new ComparandumException("Not a valid report generator class or format name."+fqcn, e);
		}
	}

	/*************************************************************************
	 * Builds a unique test name for the given test method invocation.
	 * @param className of the unit test class.
	 * @param methodName of the test method.
	 * @param param an optional parameter name for parameterized tests.
	 * @return a unique name.
	 ************************************************************************/
	
	public static String buildTestName(String className, String methodName, String param) {
		String result = className.replace('.', '_')+"__"+methodName;
		if (param != null) {
			param = param.replaceAll("[^a-zA-Z0-9]", "");
			result = result += "["+ param + "]";
		}
		return result; 
	}

}

