package de.rosstauscher.comparandum.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/*****************************************************************************
 * Some util methods for IO operations. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class IOUtil {
	
	public static final String DEFAULT_LOGGER_NAME = "COMPARANDUM";
	public static final String DEFAULT_FILE_ENCODING = "UTF-8";

	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	private IOUtil() {
		super();
	}
	
	/*************************************************************************
	 * Closes a given file without throwing an exception on error.
	 * @param c a closeable object like a file.
	 ************************************************************************/
	
	public static void closeSilently(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				Logger.getLogger(DEFAULT_LOGGER_NAME).log(Level.WARNING, e.getLocalizedMessage(), e);
			}
		}
	}
	
	/*************************************************************************
	 * Reads a UTF-8 encoded text file from a given resource.
	 * @param clazz the reference class.
	 * @param resourcePath the resource path relative to the reference class.
	 * @return the content of the text file as String.
	 * @throws IOException on read error.
	 ************************************************************************/
	
	public static String readTextFileFromResource(Class<?> clazz, String resourcePath) throws IOException {
		InputStream in = null;
		try {
			in = clazz.getResourceAsStream(resourcePath);
			return readTextFromStream(in);
		} finally {
			closeSilently(in);
		}
	}

	/*************************************************************************
	 * @param in
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 ************************************************************************/
	
	private static String readTextFromStream(InputStream in)
			throws UnsupportedEncodingException, IOException {
		StringBuilder result = new StringBuilder();
		BufferedReader rin = new BufferedReader(new InputStreamReader(in, DEFAULT_FILE_ENCODING));
		String line = rin.readLine();
		while (line != null) {
			result.append(line).append("\n");
			line = rin.readLine();
		}
		return result.toString();
	}
	
	/*************************************************************************
	 * Writes a string to a UTF-8 encoded text file.
	 * @param content the text file content to write.
	 * @param destination file to write to.
	 * @throws IOException on write error.
	 ************************************************************************/
	
	public static void writeTextFile(String content, File destination) throws IOException {
		Writer writer = null;
		try {
			writer = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(destination),
							IOUtil.DEFAULT_FILE_ENCODING)
					);
			writer.append(content);
		} finally {
			IOUtil.closeSilently(writer);
		}
	}

	/*************************************************************************
	 * Reads a text from a given file.
	 * @param file to read from.
	 * @return the text content of the file.
	 * @throws IOException on error.
	 ************************************************************************/
	
	public static String readTextFile(File file) throws IOException {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			return readTextFromStream(in);
		} finally {
			closeSilently(in);
		}
	}
	
	/*************************************************************************
	 * This method creates a temp folder. This folder is NOT deleted automatically.
	 * @return a temporary folder.
	 ************************************************************************/
	
	public static File createTempFolder() {
		File result = new File(System.getProperty("java.io.tmpdir") ,
				"comparandum_test_data"+System.currentTimeMillis());
		result.mkdirs();
		return result;
	}
	
	/*************************************************************************
	 * Copies a resource to a given destination file.
	 * @param clazz to get the resource for.
	 * @param resourcePath the path to the resource.
	 * @param destinationFile to copy to.
	 * @throws IOException on error.
	 ************************************************************************/
	
	public static void copyResourceToFile(Class<?>clazz, String resourcePath, File destinationFile) throws IOException {
		InputStream in = clazz.getResourceAsStream(resourcePath);
		FileOutputStream out = new FileOutputStream(destinationFile, false);
		copyStream(in, out);
	}

	/*************************************************************************
	 * Copies a stream and finally closes the input and output stream.
	 * @param from input stream to copy from
	 * @param to output stream to copy to
	 * @throws IOException on error.
	 ************************************************************************/
	
	public static void copyStream(InputStream from, FileOutputStream to)
			throws IOException {
		try {
			byte[] buffer = new byte[4096];
			int read = 0;
			while ((read = from.read(buffer)) != -1) {
				to.write(buffer, 0, read);
			}
		} finally {
			IOUtil.closeSilently(from);
			IOUtil.closeSilently(to);
		}
	}

}

