package de.rosstauscher.comparandum.context;

import java.awt.Rectangle;
import java.io.File;
import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Test;

import de.rosstauscher.comparandum.config.Config;
import de.rosstauscher.comparandum.config.ConfigBuilder;
import de.rosstauscher.comparandum.filter.ExcludeAreaFilter;
import de.rosstauscher.comparandum.filter.ScaleFilter;

/*****************************************************************************
 * Some unit tests 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class ConfigBuilderTest {
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	public void buildFilter() {
		ConfigBuilder bldr = new ConfigBuilder();
		ExcludeAreaFilter excludeAreaFilter = new ExcludeAreaFilter(new Rectangle(0, 0, 10, 10));
		Config config = bldr.applyFilter(excludeAreaFilter).build();
		Assert.assertTrue(config.getFilter().contains(excludeAreaFilter));
	}
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	public void buildComparatumFilter() {
		ConfigBuilder bldr = new ConfigBuilder();
		ExcludeAreaFilter excludeAreaFilter = new ExcludeAreaFilter(new Rectangle(0, 0, 10, 10));
		Config config = bldr.applyComparatumFilter(excludeAreaFilter).build();
		Assert.assertTrue(config.getComparatumFilter().contains(excludeAreaFilter));
	}
	
	/*************************************************************************
	 * Unit test
	 * @throws MalformedURLException on error 
	 ************************************************************************/
	@Test
	public void buildLoadFromUrl() throws MalformedURLException {
		ConfigBuilder bldr = new ConfigBuilder();
		File f = new File("test/resources/test1.png");
		Config config = bldr.compareToImage(f.toURI().toURL()).build();
		Assert.assertNotNull(config.getComparatum());
	}
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	public void buildLoadFromFile() {
		ConfigBuilder bldr = new ConfigBuilder();
		File f = new File("test/resources/test1.png");
		Config config = bldr.compareToImage(f).build();
		Assert.assertNotNull(config.getComparatum());
	}
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	public void buildExcludeFilter() {
		ConfigBuilder bldr = new ConfigBuilder();
		Rectangle shape = new Rectangle(0, 0, 10, 10);
		Config config = bldr.ignore(shape).build();
		Assert.assertTrue(config.getFilter().get(0) instanceof ExcludeAreaFilter);
		Assert.assertTrue(config.getComparatumFilter().get(0) instanceof ExcludeAreaFilter);
	}

	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	public void buildExcludeFilterRect() {
		ConfigBuilder bldr = new ConfigBuilder();
		Config config = bldr.ignore(0, 0, 10, 10).build();
		Assert.assertTrue(config.getFilter().get(0) instanceof ExcludeAreaFilter);
		Assert.assertTrue(config.getComparatumFilter().get(0) instanceof ExcludeAreaFilter);
	}
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	public void buildScaleFilter() {
		ConfigBuilder bldr = new ConfigBuilder();
		Config config = bldr.scaleInput(32, 32).build();
		Assert.assertTrue(config.getFilter().get(0) instanceof ScaleFilter);
	}
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	public void buildShouldUpdateTestClassName() {
		ConfigBuilder bldr = new ConfigBuilder();
		Config config = bldr.build();
		Assert.assertEquals(this.getClass().getName(), config.getTestClassName());
	}
	
	/*************************************************************************
	 * Unit test
	 ************************************************************************/
	@Test
	public void buildShouldUpdateTestMethodName() {
		ConfigBuilder bldr = new ConfigBuilder();
		Config config = bldr.build();
		Assert.assertEquals("buildShouldUpdateTestMethodName", config.getTestMethodName());
	}



}

