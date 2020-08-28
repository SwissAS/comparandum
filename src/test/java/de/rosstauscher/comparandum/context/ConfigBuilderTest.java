package de.rosstauscher.comparandum.context;

import java.awt.Rectangle;
import java.net.MalformedURLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.rosstauscher.comparandum.TestHelper;
import de.rosstauscher.comparandum.config.Config;
import de.rosstauscher.comparandum.config.ConfigBuilder;
import de.rosstauscher.comparandum.filter.ExcludeAreaFilter;
import de.rosstauscher.comparandum.filter.ScaleFilter;

/*****************************************************************************
 * Some unit tests 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

class ConfigBuilderTest {
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	void buildFilter() {
		ConfigBuilder bldr = new ConfigBuilder();
		ExcludeAreaFilter excludeAreaFilter = new ExcludeAreaFilter(new Rectangle(0, 0, 10, 10));
		Config config = bldr.applyFilter(excludeAreaFilter).build();
		Assertions.assertTrue(config.getFilter().contains(excludeAreaFilter));
	}
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	void buildComparatumFilter() {
		ConfigBuilder bldr = new ConfigBuilder();
		ExcludeAreaFilter excludeAreaFilter = new ExcludeAreaFilter(new Rectangle(0, 0, 10, 10));
		Config config = bldr.applyComparatumFilter(excludeAreaFilter).build();
		Assertions.assertTrue(config.getComparatumFilter().contains(excludeAreaFilter));
	}
	
	/*************************************************************************
	 * Unit test
	 * @throws MalformedURLException on error 
	 ************************************************************************/
	@Test
	void buildLoadFromUrl() throws MalformedURLException {
		ConfigBuilder bldr = new ConfigBuilder();
		Config config = bldr.compareToImage(TestHelper.TEST_FILE1.toURI().toURL()).build();
		Assertions.assertNotNull(config.getComparatum());
	}
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	void buildLoadFromFile() {
		ConfigBuilder bldr = new ConfigBuilder();
		Config config = bldr.compareToImage(TestHelper.TEST_FILE1).build();
		Assertions.assertNotNull(config.getComparatum());
	}
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	void buildExcludeFilter() {
		ConfigBuilder bldr = new ConfigBuilder();
		Rectangle shape = new Rectangle(0, 0, 10, 10);
		Config config = bldr.ignore(shape).build();
		Assertions.assertTrue(config.getFilter().get(0) instanceof ExcludeAreaFilter);
		Assertions.assertTrue(config.getComparatumFilter().get(0) instanceof ExcludeAreaFilter);
	}

	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	void buildExcludeFilterRect() {
		ConfigBuilder bldr = new ConfigBuilder();
		Config config = bldr.ignore(0, 0, 10, 10).build();
		Assertions.assertTrue(config.getFilter().get(0) instanceof ExcludeAreaFilter);
		Assertions.assertTrue(config.getComparatumFilter().get(0) instanceof ExcludeAreaFilter);
	}
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	void buildScaleFilter() {
		ConfigBuilder bldr = new ConfigBuilder();
		Config config = bldr.scaleInput(32, 32).build();
		Assertions.assertTrue(config.getFilter().get(0) instanceof ScaleFilter);
	}
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	public void buildShouldUpdateTestClassName() {
		ConfigBuilder bldr = new ConfigBuilder();
		Config config = bldr.build();
		Assertions.assertEquals(this.getClass().getName(), config.getTestClassName());
	}
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	public void buildShouldUpdateTestMethodName() {
		ConfigBuilder bldr = new ConfigBuilder();
		Config config = bldr.build();
		Assertions.assertEquals("buildShouldUpdateTestMethodName", config.getTestMethodName());
	}
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	void buildShouldSetPHashDistance() {
		ConfigBuilder bldr = new ConfigBuilder();
		bldr.compareHashes(10);
		Config config = bldr.build();
		Assertions.assertEquals(10, config.getAcceptedDistance());
	}



}

