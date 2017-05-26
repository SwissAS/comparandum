package de.rosstauscher.comparandum.config;

import java.util.logging.Logger;

import de.rosstauscher.comparandum.filter.FilterList;
import de.rosstauscher.comparandum.junit.Comparandum;
import de.rosstauscher.comparandum.loader.IComparatumLoader;
import de.rosstauscher.comparandum.loader.IUpdateableLocation;
import de.rosstauscher.comparandum.render.IRenderable;
import de.rosstauscher.comparandum.util.IOUtil;

/*****************************************************************************
 * The config object describes some settings and the environment for a test.
 * Use a ConfigBuilder to create one.
 *   
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class Config {
	
	private static final String DEFAULT_MODE = OperationMode.COMPARE.name();
	
	private IRenderable comparandum;
	private IComparatumLoader comparatumLoader;
	private final FilterList comparatumFilter;
	private final FilterList filter;
	private OperationMode operationMode;
	private String testClassName;
	private String testMethodName;
	private String parameterValues;

	
	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	Config() {
		super();
		this.comparatumFilter = new FilterList();
		this.filter = new FilterList();
		this.operationMode = getDefaultOperationMode();
	}

	/*************************************************************************
	 * @return the default initial operation mode. 
	 ************************************************************************/
	
	private OperationMode getDefaultOperationMode() {
		String mode = System.getProperty(Comparandum.PROPERTY_MODE_OVERRIDE, DEFAULT_MODE);
		return OperationMode.valueOf(mode.toUpperCase().trim());
	}
	
	public IRenderable getComparandum() {
		return this.comparandum;
	}

	/*************************************************************************
	 * @return the comparatum to compare against.
	 ************************************************************************/
	
	public IRenderable getComparatum() {
		return this.comparatumLoader.load();
	}

	/*************************************************************************
	 * @return a list of filters that are applied to the comparatum before comparison.
	 ************************************************************************/
	
	public FilterList getComparatumFilter() {
		return this.comparatumFilter;
	}

	/*************************************************************************
	 * @return a list of filters applied to the IRenderable before comparison.
	 ************************************************************************/
	
	public FilterList getFilter() {
		return this.filter;
	}

	/*************************************************************************
	 * Used internally only
	 * @return a mode of operation to use. Either COMPARE, UPDATE or REPORT
	 ************************************************************************/
	
	public OperationMode getOperationMode() {
		return this.operationMode;
	}
	
	/*************************************************************************
	 * This  will be used to set the comparandum.
	 * @param comparandum to compare with the comparatum. 
	 ************************************************************************/
	
	final void setComparandum(IRenderable comparandum) {
		this.comparandum = comparandum;
	}

	/*************************************************************************
	 * This loader will be used to get the instance of the comparatum.
	 * @param comparatum loader The loader to used 
	 ************************************************************************/
	
	final void setComparatumLoader(IComparatumLoader comparatumLoader) {
		this.comparatumLoader = comparatumLoader;
	}

	/*************************************************************************
	 * Sets the mode of operation used during the test run.
	 * @param operationMode The operationMode to set.
	 ************************************************************************/
	
	final void setOperationMode(OperationMode operationMode) {
		this.operationMode = operationMode;
	}

	/*************************************************************************
	 * This method is invoked to update the comparatum from the current IRenderable object.
	 * If the location of the comparatum is not mutable then this is a noop.
	 * @param renderable To write back as comparatum image.
	 ************************************************************************/
	
	public void updateComparatum(IRenderable renderable) {
		if (this.comparatumLoader instanceof IUpdateableLocation) {
			((IUpdateableLocation)this.comparatumLoader).storeAsComparatum(renderable);
		} else {
			Logger.getLogger(IOUtil.DEFAULT_LOGGER_NAME).warning("Location is not updateable.");
		}
	}

	/*************************************************************************
	 * Sets the name of the unit test class running.
	 * @param className a class name.
	 ************************************************************************/
	
	final void setTestClassName(String className) {
		this.testClassName = className;
	}
	
	/*************************************************************************
	 * Gets the name of the unit test class running.
	 * @return Returns the test class name.
	 ************************************************************************/
	
	public String getTestClassName() {
		return this.testClassName;
	}

	/*************************************************************************
	 * Sets the name of the unit test method running.
	 * @param methodName to set.
	 ************************************************************************/
	
	final void setTestMethodName(String methodName) {
		this.testMethodName = methodName;
	}

	/*************************************************************************
	 * Gets the name of the unit test method running.
	 * @return Returns the testMethodName.
	 ************************************************************************/
	
	public String getTestMethodName() {
		return this.testMethodName;
	}

	/*************************************************************************
	 * Gets the optional name that may be used for "parameterized tests" to
	 * distinguish them from each other.
	 * @return Returns the parameter(s).
	 ************************************************************************/
	
	public String getTestMethodParameter() {
		return this.parameterValues;
	}

	/*************************************************************************
	 * Sets the optional name that may be used for "parameterized tests" to
	 * distinguish them from each other.
	 * @param parameterValues The parameterName to set.
	 ************************************************************************/
	
	final void setTestMethodParameter(String parameterValues) {
		this.parameterValues = parameterValues;
	}
	
}

