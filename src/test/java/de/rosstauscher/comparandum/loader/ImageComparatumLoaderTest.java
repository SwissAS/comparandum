package de.rosstauscher.comparandum.loader;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;

import de.rosstauscher.comparandum.render.IRenderable;
import de.rosstauscher.comparandum.util.ComparandumException;

/*****************************************************************************
 * Some unit tests. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class ImageComparatumLoaderTest {

	private static final File TEST_FILE = new File("test/resources/test1.png");
	
	/*************************************************************************
	 * Unit test
	 * @throws MalformedURLException
	 ************************************************************************/
	@Test
	public void loadImageShouldWork() {
		ImageFileLoader loader = new ImageFileLoader(TEST_FILE);
		IRenderable r = loader.load();
		assertTrue(r.getDimension().width > 0);
		assertTrue(r.getDimension().height > 0);
	}
	
	/*************************************************************************
	 * Unit test
	 * @throws MalformedURLException
	 ************************************************************************/
	@Test(expected=ComparandumException.class)
	public void loadShouldFailForUnknownFile() {
		ImageFileLoader loader = new ImageFileLoader(new File("NotValid"));
		loader.load();
	}
	
	/*************************************************************************
	 * Unit test
	 * @throws IOException 
	 ************************************************************************/
	@Test
	public void storeShouldWriteComparatumFile() throws IOException {
		File f = File.createTempFile("cmp_junit", null);
		f.deleteOnExit();

		ImageFileLoader loader = new ImageFileLoader(TEST_FILE);
		IRenderable r = loader.load();

		loader = new ImageFileLoader(f);
		loader.storeAsComparatum(r);
		
		assertTrue(f.exists());
	}
	
	/*************************************************************************
	 * Unit test
	 * @throws MalformedURLException
	 ************************************************************************/
	@Test(expected=ComparandumException.class)
	public void storeShouldFailForInvalidPath() {
		ImageFileLoader loader = new ImageFileLoader(TEST_FILE);
		IRenderable r = loader.load();

		File f = new File(File.separator);
		loader = new ImageFileLoader(f);
		loader.storeAsComparatum(r);
		
		assertTrue(f.exists());	}


}

