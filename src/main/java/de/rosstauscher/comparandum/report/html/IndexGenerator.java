package de.rosstauscher.comparandum.report.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
	
	private class TestResultNode {
		
		private final String name;
		private int passed;
		private int failed;
		private final int index; 
		private final Map<String, TestResultNode> children;
		private final String fileName;
		
		public TestResultNode(String name, String file, int passed, int failed) {
			super();
			this.name = name;
			this.fileName = file;
			this.passed = passed;
			this.failed = failed;
			this.index = IndexGenerator.this.nodeIndex++;
			this.children = new TreeMap<String, TestResultNode>();
		}
		
		public int getFailed() {
			return this.failed;
		}
		
		public int getPassed() {
			return this.passed;
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getIndex() {
			return this.index;
		}
		
		public TestResultNode add(String name, TestResultNode childNode) {
			TestResultNode nodeToAdd = this.children.get(name);
			if (nodeToAdd == null) {
				nodeToAdd = childNode;
			} else {
				nodeToAdd.addResult( 
						childNode.getPassed(), 
						childNode.getFailed()
					);
			}
			this.children.put(name, nodeToAdd);
			return nodeToAdd;
		}
		
		private void addResult(int passed, int failed) {
			this.passed += passed;
			this.failed += failed;
		}

		public Map<String, TestResultNode> getChildren() {
			return this.children;
		}

		public String getFileName() {
			return this.fileName;
		}
		
	}

	private BufferedWriter out;

	private int nodeIndex = 1; // 1 is our root node
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
			System.out.println("Usage: java ... IndexGenerator <Path To Report Folder>");
			System.exit(-1);
		}
		File reportFolder = new File(args[0]);
		if (!reportFolder.exists() || !reportFolder.isDirectory()) {
			System.out.println("Report folder is not existing: "+reportFolder);
			System.exit(-1);
		}
		new IndexGenerator().generateIndexFile(reportFolder);
		System.out.println("Report index generated successfully in : "+reportFolder+File.separator+"index.tml");
	}
	
	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	public IndexGenerator() {
		super();
	}
	
	/*************************************************************************
	 * Generates an index html file for all test reports found in the given folder.
	 * @param reportFolder the folder where all HTML test reports are stored in.
	 * @throws IOException on error. 
	 ************************************************************************/
	
	public void generateIndexFile(File reportFolder) throws IOException {
		
		TestResultNode rootNode = new TestResultNode("All Tests", "", 0, 0);
		collectTestResults(reportFolder, rootNode);
		
		HtmlReportGenerator.copyFileIfNotExisting(reportFolder, "js/jquery-3.2.1_min.js");
//		HtmlReportGenerator.copyFileIfNotExisting(reportFolder, "js/jquery.treegrid-0.3.0.js");
//		HtmlReportGenerator.copyFileIfNotExisting(reportFolder, "js/collaps.png");
//		HtmlReportGenerator.copyFileIfNotExisting(reportFolder, "js/expand.png");
		
		openIndexFile(reportFolder);
		try {
			int parentIndex = 0;
			writeTestResults(rootNode, parentIndex);
		} finally {
			closeIndexFile();
		}
	}
	
	/*************************************************************************
	 * Collects the test results from all tests in the given folder.
	 * @param reportFolder to search for HTML test reports.
	 * @param rootNode for our index tree 
	 * @throws IOException on error 
	 ************************************************************************/
	
	private void collectTestResults(File reportFolder, TestResultNode rootNode) throws IOException {
		File[] htmlReportFiles = reportFolder.listFiles(new HtmlFileFilter());
		for (File file : htmlReportFiles) {
			collectForTest(file, rootNode);
		}
		rootNode.addResult(this.passedCount, this.failedCount);
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
		String timeStamp = getTimeStampFormat().format(new Date());
		header = header.replace("${TIME_STAMP}", timeStamp);
		header = header.replace("${TOTAL_COUNT}", Integer.toString(this.totalCount));
		header = header.replace("${PASSED_COUNT}", Integer.toString(this.passedCount));
		header = header.replace("${FAILED_COUNT}", Integer.toString(this.failedCount));
		
		this.out.write(header);
		this.out.flush();
	}
	
	/*************************************************************************
	 * Extracts the test result from a single test html report page.
	 * @param file of the test report.
	 * @param rootNode for our index tree
	 * @throws IOException on error.
	 ************************************************************************/
	
	private void collectForTest(File file, TestResultNode rootNode) throws IOException {
		String testReport = IOUtil.readTextFile(file);
		boolean testPassed = testReport.contains("class=\"PASSED\"");
		if (testPassed) {
			this.passedCount++;
		} else {
			this.failedCount++;
		}
		this.totalCount++;
		
		String name = buildFQCN(file);
		
		TestResultNode pathNode = rootNode;

		String[] token = name.split("\\.");
		for (String t : token) {
			TestResultNode childNode = new TestResultNode(t, file.getName(), testPassed ? 1 : 0, testPassed ? 0 : 1);
			pathNode = pathNode.add(t, childNode);
		}
	}
	
	/*************************************************************************
	 * Writes an entry for a given test tree node into the index file.
	 * @param file of the test report.
	 * @param node that contains the accumulated test result data.
	 * @param parentIndex the index of the parent node for this node. 
	 * @throws IOException on error.
	 ************************************************************************/
	
	private void writeTestResults(TestResultNode node, int parentIndex) throws IOException {
		String content = IOUtil.readTextFileFromResource(getClass(), "resources/index_content.html");

		String status = node.getFailed() == 0 ? "PASSED" : "FAILED";
		String testStatus = "<span class=\""+status+"\">"+status+"</span>" 
				;
		if (!node.getChildren().isEmpty()) {
			testStatus += " (<span class=\"PASSED\">"+node.getPassed()+"</span> / ";
			testStatus += "<span class=\"FAILED\">"+node.getFailed()+"</span> / ";
			testStatus += "<span class=\"TOTAL\">"+node.getFailed()+node.getPassed()+"</span>)";
		} else {
			content = content.replace("${FILE}", node.getFileName());
		}
		
		content = content.replace("${TEST_STATUS}", testStatus);
		content = content.replace("${PASSED}", Integer.toString(node.getPassed()));
		content = content.replace("${FAILED}", Integer.toString(node.getFailed()));
		content = content.replace("${TOTAL}", Integer.toString(node.getFailed()+node.getPassed()));
		content = content.replace("${FILE}", "");  
		content = content.replace("${NAME}", node.getName());
		content = content.replace("${TREE_INDEX}", Integer.toString(node.getIndex()));
		content = content.replace("${PARENT_INDEX}", Integer.toString(parentIndex));
		
		String treeclass = "treegrid-"+node.getIndex();
		if (parentIndex > 0) {
			treeclass += " treegrid-parent-"+parentIndex;
		}
		content = content.replace("${TREE_CLASS}", treeclass);
		
		this.out.write(content);
		
		Map<String, TestResultNode> children = node.getChildren();
		Set<String> keys = children.keySet();
		for (String k : keys) {
			writeTestResults(children.get(k), node.getIndex());
		}
		
	}

	/*************************************************************************
	 * Builds a clean name for a given test result file name.
	 * @param file pointing to the test result html page.
	 * @return a clean test name string.
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

