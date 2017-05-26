package de.rosstauscher.comparandum.report.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

import de.rosstauscher.comparandum.util.IOUtil;

/*****************************************************************************
 * This class can be used to generate an index file for an HTML report folder.
 *  
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class IndexGenerator {
	
	private final static class HtmlFileFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String name) {
			return name.toLowerCase().endsWith(".html") 
					&& !"index.html".equalsIgnoreCase(name);
		}
	}
	
	private BufferedWriter out;
	private int totalCount = 0;
	private int passedCount = 0;
	private int failedCount = 0;
		
	/*************************************************************************
	 * Runs the index file generator with the given folder.
	 * @param args for execution.
	 * @throws IOException on error.
	 ************************************************************************/
	
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Usage: java ... IndexGenerator PathToReportFolder");
			System.exit(-1);
		}
			
		new IndexGenerator().generateIndexFile(new File(args[0]));
	}
	
	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	public IndexGenerator() {
		super();
	}
	
	/*************************************************************************
	 * Generates an index file for all test reports found in the given folder.
	 * @param reportFolder the folder where all HTML test reports are stored in.
	 * @throws IOException on error. 
	 ************************************************************************/
	
	public void generateIndexFile(File reportFolder) throws IOException {
		openIndexFile(reportFolder);
		try {
			createContent(reportFolder);
		} finally {
			closeIndexFile();
		}
	}

	/*************************************************************************
	 * Opens the index.html for writing.
	 * @param reportFolder the folder to store the file in.
	 * @throws IOException on error.
	 ************************************************************************/
	
	private void openIndexFile(File reportFolder) throws IOException {
		File indexFile = new File(reportFolder, "index.html");

		this.out = new BufferedWriter(
				new OutputStreamWriter(
						new FileOutputStream(indexFile), IOUtil.DEFAULT_FILE_ENCODING));

		String header = IOUtil.readTextFileFromResource(getClass(), "resources/index_header.html");
		this.out.write(header);
		this.out.flush();
	}

	/*************************************************************************
	 * Writes the test case listing.
	 * @param reportFolder to search for HTML test reports.
	 * @throws IOException on error 
	 ************************************************************************/
	
	private void createContent(File reportFolder) throws IOException {
		File[] htmlReportFiles = reportFolder.listFiles(new HtmlFileFilter());
		Arrays.sort(htmlReportFiles);
		for (File file : htmlReportFiles) {
			appendToIndexFile(file);
			this.totalCount++;
		}
	}
	
	/*************************************************************************
	 * @param file of the test report.
	 * @throws IOException on error.
	 ************************************************************************/
	
	private void appendToIndexFile(File file) throws IOException {
		String content = IOUtil.readTextFileFromResource(getClass(), "resources/index_content.html");

		String testReport = IOUtil.readTextFile(file);
		boolean testPassed = testReport.contains("class=\"PASSED\"");
		if (testPassed) {
			this.passedCount++;
			content = content.replace("${TEST_STATUS}", "PASSED");
		} else {
			this.failedCount++;
			content = content.replace("${TEST_STATUS}", "FAILED");
		}
		content = content.replace("${FILE}", file.getName());
		content = content.replace("${NAME}", buildFQCN(file));
		
		this.out.write(content);
	}

	/*************************************************************************
	 * @param file
	 * @return
	 ************************************************************************/
	
	
	private String buildFQCN(File file) {
		return file.getName()
			.replace("__", "#")
			.replace("_", ".")
			.replace(".html", "()");
	}

	/*************************************************************************
	 * Writes the footer and closes the file.
	 * @throws IOException on error
	 ************************************************************************/
	
	private void closeIndexFile() throws IOException {
		String footer = IOUtil.readTextFileFromResource(getClass(), "resources/index_footer.html");
		String timeStamp = getTimeStampFormat().format(new Date());
		footer = footer.replace("${TIME_STAMP}", timeStamp);
		footer = footer.replace("${TOTAL_COUNT}", Integer.toString(this.totalCount));
		footer = footer.replace("${PASSED_COUNT}", Integer.toString(this.passedCount));
		footer = footer.replace("${FAILED_COUNT}", Integer.toString(this.failedCount));
		
		this.out.write(footer);
		this.out.flush();
		
		IOUtil.closeSilently(this.out);
	}
	
	/*************************************************************************
	 * @return the DateFormat used to format the time stamp information.
	 ************************************************************************/
	
	private DateFormat getTimeStampFormat() {
		return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.LONG);
	}

}

