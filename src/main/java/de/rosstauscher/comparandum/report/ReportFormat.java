package de.rosstauscher.comparandum.report;

import de.rosstauscher.comparandum.report.html.HtmlReportGenerator;
import de.rosstauscher.comparandum.report.xml.XmlReportGenerator;

/*****************************************************************************
 * Defines the report format to generate. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public enum ReportFormat {

	HTML(HtmlReportGenerator.class.getName()),
	
	XML(XmlReportGenerator.class.getName());
	
	private String reportGenerator;
	
	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	private ReportFormat(String reportGenerator) {
		this.reportGenerator = reportGenerator;
	}

	/*************************************************************************
	 * @return Returns the report generator class to produce this format.
	 ************************************************************************/
	
	public final String getReportGeneratorClass() {
		return this.reportGenerator;
	}
	
	

}

