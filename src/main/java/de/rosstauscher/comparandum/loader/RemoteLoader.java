package de.rosstauscher.comparandum.loader;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

/*****************************************************************************
 * Loads a comparatum image from a URL 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class RemoteLoader extends AbstractImageLoader {

	private final URL url;
	
	/*************************************************************************
	 * Constructor
	 * @param url to load the image from.
	 ************************************************************************/
	
	public RemoteLoader(URL url) {
		super();
		this.url = url;
	}

	/*************************************************************************
	 * Loads the comparatum image. 
	 * @return the image loaded from an URL.
	 ************************************************************************/
	@Override
	protected Image loadImage() {
		return new ImageIcon(this.url).getImage();
	}


}

