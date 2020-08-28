package de.rosstauscher.comparandum.loader;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;

import de.rosstauscher.comparandum.TestHelper;
import de.rosstauscher.comparandum.render.IRenderable;
import de.rosstauscher.comparandum.util.ComparandumException;

/*****************************************************************************
 * Some unit tests. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

class ImageComparatumLoaderTest {
	
	/*************************************************************************
	 * Unit test
	 * @throws MalformedURLException
	 ************************************************************************/
	@Test
	void loadImageShouldWork() {
		ImageFileLoader loader = new ImageFileLoader(TestHelper.TEST_FILE1);
		IRenderable r = loader.load();
		assertTrue(r.getDimension().width > 0);
		assertTrue(r.getDimension().height > 0);
	}
	
	/*************************************************************************
	 * Unit test
	 * @throws MalformedURLException
	 ************************************************************************/
	@Test
	void loadShouldFailForUnknownFile() {
		assertThrows(ComparandumException.class, () -> {
			ImageFileLoader loader = new ImageFileLoader(new File("NotValid"));
			loader.load();
		});
	}
	
	/*************************************************************************
	 * Unit test
	 * @throws IOException 
	 ************************************************************************/
	@Test
	void storeShouldWriteComparatumFile() throws IOException {
		File f = File.createTempFile("cmp_junit", null);
		f.deleteOnExit();

		ImageFileLoader loader = new ImageFileLoader(TestHelper.TEST_FILE1);
		IRenderable r = loader.load();

		loader = new ImageFileLoader(f);
		loader.storeAsComparatum(r);
		
		assertTrue(f.exists());
	}
	
	/*************************************************************************
	 * Unit test
	 * @throws MalformedURLException
	 ************************************************************************/
	@Test
	void storeShouldFailForInvalidPath() {
		assertThrows(ComparandumException.class, () -> {
			ImageFileLoader loader = new ImageFileLoader(TestHelper.TEST_FILE1);
			IRenderable r = loader.load();
	
			File f = new File(File.separator);
			loader = new ImageFileLoader(f);
			loader.storeAsComparatum(r);
			
			assertTrue(f.exists());
		});
	}
}

