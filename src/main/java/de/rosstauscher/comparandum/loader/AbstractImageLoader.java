package de.rosstauscher.comparandum.loader;

import java.awt.Image;

import de.rosstauscher.comparandum.render.IRenderable;
import de.rosstauscher.comparandum.render.ImageRenderable;
import de.rosstauscher.comparandum.util.ComparandumException;

/*****************************************************************************
 * An abstract image loader.  
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public abstract class AbstractImageLoader implements IComparatumLoader {

	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	public AbstractImageLoader() {
		super();
	}

	/*************************************************************************
	 * Loads the comparatum image from the source and returns it as IRenderable.
	 * @see de.rosstauscher.comparandum.loader.IComparatumLoader#load()
	 ************************************************************************/
	@Override
	public final IRenderable load() {
		Image img = loadImage();
		assertImageIsValid(img);
		return new ImageRenderable(img);
	}

	/*************************************************************************
	 * Loads the image from the source.
	 * @return an Image.
	 ************************************************************************/
	
	protected abstract Image loadImage();

	/*************************************************************************
	 * Ensure the image is fully loaded.
	 * @param img to inspect
	 * @throws ComparandumException on error.
	 ************************************************************************/
	
	private void assertImageIsValid(Image img) throws ComparandumException {
		if (img.getWidth(null) == -1 || img.getHeight(null) == -1) {
			throw new ComparandumException("Error loading image.", null);
		}
	}

}
