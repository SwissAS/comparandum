package de.rosstauscher.comparandum.util;

/*****************************************************************************
 * Thrown if something goes wrong in the framework. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class ComparandumException extends RuntimeException {

	/*************************************************************************
	 * Constructor
	 * @param message describing the error that has occured.
	 * @param cause of the error for exception chaining.
	 ************************************************************************/
	
	public ComparandumException(String message, Exception cause) {
		super(message, cause);
	}
	
	/*************************************************************************
	 * Constructor
	 * @param cause of the error for exception chaining.
	 ************************************************************************/
	
	public ComparandumException(Exception cause) {
		super(cause);
	}


}

