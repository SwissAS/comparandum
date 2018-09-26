package de.rosstauscher.comparandum.config;

import java.awt.Rectangle;
import java.awt.Shape;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;

import de.rosstauscher.comparandum.filter.ExcludeAreaFilter;
import de.rosstauscher.comparandum.filter.FilterList;
import de.rosstauscher.comparandum.filter.IFilter;
import de.rosstauscher.comparandum.filter.ScaleFilter;
import de.rosstauscher.comparandum.loader.DirectLoader;
import de.rosstauscher.comparandum.loader.ImageFileLoader;
import de.rosstauscher.comparandum.loader.RemoteLoader;
import de.rosstauscher.comparandum.render.IRenderable;

/*****************************************************************************
 * A builder that can be used to create a Config object.
 * All methods are designed to allow method chaining. For some methods the order 
 * of invocation does matter. For example filters will be applied in the order they
 * were added. When finished call the build() method to create an Config object. 
 * A builder can be reused multiple times but all settings are reset after
 * the build method is invoked.
 *  
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class ConfigBuilder {
	
	private Config context;
	
	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	public ConfigBuilder() {
		super();
		this.context = new Config(); 
	}

	/*************************************************************************
	 * Adds a given filter to the filter list applied to the renderable object 
	 * before comparison.
	 * @param filter to add to the filter list of the config.
	 * @return the builder for method chaining.
	 ************************************************************************/
	
	public ConfigBuilder applyFilter(IFilter filter) {
		FilterList filterList = this.context.getFilter();
		filterList.add(filter);
		return this;
	}
	
	/*************************************************************************
	 * Adds a given filter to the filter list applied to the comparatum object
	 * before comparison.
	 * @param filter to add to the filter list of the config.
	 * @return the builder for method chaining.
	 ************************************************************************/
	
	public ConfigBuilder applyComparatumFilter(IFilter filter) {
		FilterList filterList = this.context.getComparatumFilter();
		filterList.add(filter);
		return this;
	}

	/*************************************************************************
	 * Builds a Config object and resets the builder.
	 * After this call the builder can be reused to build another config
	 * object. All previously stored settings are reset.
	 * @return the configured Config object.
	 ************************************************************************/
	
	public Config build() {
		extractTestClass();
		Config result = this.context;
		this.context = new Config();
		return result;
	}

	/*************************************************************************
	 * Set the source of the comparatum to the given image file.
	 * Supported are all image formats that are supported by Java per default
	 * JPG, PNG, GIF 
	 * @param f an image file to compare to.
	 * @return this builder for method chaining.
	 ************************************************************************/
	
	public ConfigBuilder compareToImage(File f) {
		this.context.setComparatumLoader(new ImageFileLoader(f));
		return this;
	}
	
	/*************************************************************************
	 * Set the source of the comparatum to the given image file.
	 * Supported are all image formats that are supported by Java per default
	 * JPG, PNG, GIF 
	 * @param url to an image file to compare to.
	 * @return this builder for method chaining.
	 ************************************************************************/
	
	public ConfigBuilder compareToImage(URL url) {
		this.context.setComparatumLoader(new RemoteLoader(url));
		return this;	
	}
	
	/*************************************************************************
	 * Set the actual IRenderable that you have created and that you want to 
	 * compare to the reference.
	 * @param actual IRenderable to compare.
	 * @return this builder for method chaining.
	 ************************************************************************/
	
	public ConfigBuilder compareThis(IRenderable actual) {
		this.context.setComparandum(actual);
		return this;	
	}
	
	/*************************************************************************
	 * Set the source of the comparatum to the given IRenderable.
	 * @param expected reference to compare to.
	 * @return this builder for method chaining.
	 ************************************************************************/
	
	public ConfigBuilder compareTo(IRenderable expected) {
		this.context.setComparatumLoader(new DirectLoader(expected));
		return this;	
	}

	/*************************************************************************
	 * Applies an "exclude area" filter to the input and to the comparatum.
	 * @param shape to mask in the comparision.
	 * @return this builder for method chaining.
	 ************************************************************************/
	
	public ConfigBuilder ignore(Shape shape) {
		ExcludeAreaFilter filter = new ExcludeAreaFilter(shape);
		applyFilter(filter);
		applyComparatumFilter(filter);
		return this;
	}
	
	/*************************************************************************
 	 * Applies a rectangular "exclude area" filter to the input and to the comparatum.
	 * @param x position of the exclude area
	 * @param y position of the exclude area
	 * @param x2 position of of the exclude area
	 * @param y2 position of of the exclude area
	 * @return this builder for method chaining.
	 ************************************************************************/

	public ConfigBuilder ignore(int x, int y, int x2, int y2) {
		Rectangle r = new Rectangle(x, y, x2-x, y2-y);
		return ignore(r);
	}
	
	/*************************************************************************
	 * Applies a scale filter to the input before comparing it.
	 * @param newWidht of the input 
	 * @param newHeight of the input
	 * @return this builder for method chaining.
	 ************************************************************************/
	
	public ConfigBuilder scaleInput(int newWidht, int newHeight) {
		ScaleFilter filter = new ScaleFilter(newWidht, newHeight);
		applyFilter(filter);
		return this;
	}
	
	
	/*************************************************************************
	 * Set the mode of operation used later on in the assert API
	 * @param mode to set.
	 * @return this builder for method chaining.
	 ************************************************************************/
	
	ConfigBuilder setMode(OperationMode mode) {
		this.context.setOperationMode(mode);
		return this;
	}
	
	/*************************************************************************
	 * Sets the additional info for parameterized tests. This info
	 * is appended to the test name in reports. 
	 * @param o the data for the parameterized test.
	 * @return this builder for method chaining.
	 ************************************************************************/
	
	public ConfigBuilder parameterizedWith(Object o) {
		this.context.setTestMethodParameter(String.valueOf(o));
		return this;
	}
	
	/*************************************************************************
	 * Activates a mode where we compare only hashes. 
	 * This allows to work with some tolerance.  
	 * @param allowedDistance to specify how similar the images should be.
	 * @return this builder for method chaining.
	 ************************************************************************/
	
	public ConfigBuilder compareHashes(int allowedDistance) {
		this.context.setAcceptedDistance(allowedDistance);
		return this;
	}

	/*************************************************************************
	 * Builds the test name for the current test run.
	 * We try to find the test method from the method stack trace via reflection. 
	 * @param config of the current test run.
	 * @return a unique name build from the invoked "test method".
	 ************************************************************************/

	private void extractTestClass() {
		for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
			String className = stackTraceElement.getClassName();
			String methodName = stackTraceElement.getMethodName();
			if (isTestMethod(className, methodName)) {
				this.context.setTestClassName(className);
				this.context.setTestMethodName(methodName);
				break;
			}
		}
	}
	
	/*************************************************************************
	 * Check if the given method is marked with an @Test annotation.
	 * @param className of the class under inspection 
	 * @param methodName of the method under inspection. 
	 * @return true if this is a method annotated with @Test  
	 ************************************************************************/
	
	private static boolean isTestMethod(String className, String methodName) {
		try {
			Class<?> cls = Class.forName(className);
			Method method = cls.getMethod(methodName);
			return method.getAnnotation(org.junit.Test.class) != null;
		} catch (SecurityException e) {
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

}

