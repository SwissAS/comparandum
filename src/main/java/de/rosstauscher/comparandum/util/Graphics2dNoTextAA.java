package de.rosstauscher.comparandum.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.util.Map;

/*****************************************************************************
 * Some helper class for deactivating text AA globally. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2018
 ****************************************************************************/

final class Graphics2dNoTextAA extends Graphics2dDecorator {
	
	/**
	 * @param delegate
	 */
	Graphics2dNoTextAA(Graphics2D delegate) {
		super(delegate);
		setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	}

	/* (non-Javadoc)
	 * @see de.rosstauscher.comparandum.util.Graphics2dDecorator#setRenderingHint(java.awt.RenderingHints.Key, java.lang.Object)
	 */
	@Override
	public void setRenderingHint(Key hintKey, Object hintValue) {
		if (RenderingHints.KEY_TEXT_ANTIALIASING.equals(hintKey)) {
			hintValue = RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
		}
		if (RenderingHints.KEY_FRACTIONALMETRICS.equals(hintKey)) {
			hintValue = RenderingHints.VALUE_FRACTIONALMETRICS_OFF;
		}
		if (RenderingHints.KEY_STROKE_CONTROL.equals(hintKey)) {
			hintValue = RenderingHints.VALUE_STROKE_PURE;
		}
		super.setRenderingHint(hintKey, hintValue);
	}

	/* (non-Javadoc)
	 * @see de.rosstauscher.comparandum.util.Graphics2dDecorator#setRenderingHints(java.util.Map)
	 */
	@Override
	public void setRenderingHints(Map<?, ?> hints) {
		super.setRenderingHints(filterTextAASettings(hints));
	}
	
	/* (non-Javadoc)
	 * @see de.rosstauscher.comparandum.util.Graphics2dDecorator#addRenderingHints(java.util.Map)
	 */
	@Override
	public void addRenderingHints(Map<?, ?> hints) {
		super.addRenderingHints(filterTextAASettings(hints));
	}

	/**
	 * Filter the corresponding keys in the map and overwrite them.
	 * @param hints
	 * @return 
	 */
	private Map<?, ?> filterTextAASettings(Map<?, ?> hints) {
		if (hints != null && hints.containsKey(RenderingHints.KEY_TEXT_ANTIALIASING)) {
			@SuppressWarnings("unchecked")
			Map<Object, Object> hh = (Map<Object, Object>) hints;
			hh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			hh.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
			hh.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		}
		return hints;
	}
	
}