/**
 * 
 */
package de.rosstauscher.comparandum.util;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

/*****************************************************************************
 * Some helper class for implementing a rendering decorator to influence the rendering globally. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2018
 ****************************************************************************/

public class Graphics2dDecorator extends Graphics2D {
	
	private final Graphics2D delegate;
	
	/**
	 * @param delegate
	 */
	public Graphics2dDecorator(Graphics2D delegate) {
		super();
		this.delegate = delegate;
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.delegate.hashCode();
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return this.delegate.equals(obj);
	}

	/**
	 * @return
	 * @see java.awt.Graphics#create()
	 */
	@Override
	public Graphics create() {
		return this.delegate.create();
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 * @see java.awt.Graphics#create(int, int, int, int)
	 */
	@Override
	public Graphics create(int x, int y, int width, int height) {
		return this.delegate.create(x, y, width, height);
	}

	/**
	 * @return
	 * @see java.awt.Graphics#getColor()
	 */
	@Override
	public Color getColor() {
		return this.delegate.getColor();
	}

	/**
	 * @param c
	 * @see java.awt.Graphics#setColor(java.awt.Color)
	 */
	@Override
	public void setColor(Color c) {
		this.delegate.setColor(c);
	}

	/**
	 * 
	 * @see java.awt.Graphics#setPaintMode()
	 */
	@Override
	public void setPaintMode() {
		this.delegate.setPaintMode();
	}

	/**
	 * @param c1
	 * @see java.awt.Graphics#setXORMode(java.awt.Color)
	 */
	@Override
	public void setXORMode(Color c1) {
		this.delegate.setXORMode(c1);
	}

	/**
	 * @return
	 * @see java.awt.Graphics#getFont()
	 */
	@Override
	public Font getFont() {
		return this.delegate.getFont();
	}

	/**
	 * @param font
	 * @see java.awt.Graphics#setFont(java.awt.Font)
	 */
	@Override
	public void setFont(Font font) {
		this.delegate.setFont(font);
	}

	/**
	 * @return
	 * @see java.awt.Graphics#getFontMetrics()
	 */
	@Override
	public FontMetrics getFontMetrics() {
		return this.delegate.getFontMetrics();
	}

	/**
	 * @param f
	 * @return
	 * @see java.awt.Graphics#getFontMetrics(java.awt.Font)
	 */
	@Override
	public FontMetrics getFontMetrics(Font f) {
		return this.delegate.getFontMetrics(f);
	}

	/**
	 * @return
	 * @see java.awt.Graphics#getClipBounds()
	 */
	@Override
	public Rectangle getClipBounds() {
		return this.delegate.getClipBounds();
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @see java.awt.Graphics#clipRect(int, int, int, int)
	 */
	@Override
	public void clipRect(int x, int y, int width, int height) {
		this.delegate.clipRect(x, y, width, height);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @see java.awt.Graphics#setClip(int, int, int, int)
	 */
	@Override
	public void setClip(int x, int y, int width, int height) {
		this.delegate.setClip(x, y, width, height);
	}

	/**
	 * @return
	 * @see java.awt.Graphics#getClip()
	 */
	@Override
	public Shape getClip() {
		return this.delegate.getClip();
	}

	/**
	 * @param clip
	 * @see java.awt.Graphics#setClip(java.awt.Shape)
	 */
	@Override
	public void setClip(Shape clip) {
		this.delegate.setClip(clip);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param dx
	 * @param dy
	 * @see java.awt.Graphics#copyArea(int, int, int, int, int, int)
	 */
	@Override
	public void copyArea(int x, int y, int width, int height, int dx, int dy) {
		this.delegate.copyArea(x, y, width, height, dx, dy);
	}

	/**
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @see java.awt.Graphics#drawLine(int, int, int, int)
	 */
	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		this.delegate.drawLine(x1, y1, x2, y2);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @see java.awt.Graphics#fillRect(int, int, int, int)
	 */
	@Override
	public void fillRect(int x, int y, int width, int height) {
		this.delegate.fillRect(x, y, width, height);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @see java.awt.Graphics#drawRect(int, int, int, int)
	 */
	@Override
	public void drawRect(int x, int y, int width, int height) {
		this.delegate.drawRect(x, y, width, height);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param raised
	 * @see java.awt.Graphics2D#draw3DRect(int, int, int, int, boolean)
	 */
	@Override
	public void draw3DRect(int x, int y, int width, int height, boolean raised) {
		this.delegate.draw3DRect(x, y, width, height, raised);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @see java.awt.Graphics#clearRect(int, int, int, int)
	 */
	@Override
	public void clearRect(int x, int y, int width, int height) {
		this.delegate.clearRect(x, y, width, height);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param arcWidth
	 * @param arcHeight
	 * @see java.awt.Graphics#drawRoundRect(int, int, int, int, int, int)
	 */
	@Override
	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		this.delegate.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param raised
	 * @see java.awt.Graphics2D#fill3DRect(int, int, int, int, boolean)
	 */
	@Override
	public void fill3DRect(int x, int y, int width, int height, boolean raised) {
		this.delegate.fill3DRect(x, y, width, height, raised);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param arcWidth
	 * @param arcHeight
	 * @see java.awt.Graphics#fillRoundRect(int, int, int, int, int, int)
	 */
	@Override
	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		this.delegate.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
	}

	/**
	 * @param s
	 * @see java.awt.Graphics2D#draw(java.awt.Shape)
	 */
	@Override
	public void draw(Shape s) {
		this.delegate.draw(s);
	}

	/**
	 * @param img
	 * @param xform
	 * @param obs
	 * @return
	 * @see java.awt.Graphics2D#drawImage(java.awt.Image, java.awt.geom.AffineTransform, java.awt.image.ImageObserver)
	 */
	@Override
	public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
		return this.delegate.drawImage(img, xform, obs);
	}

	/**
	 * @param img
	 * @param op
	 * @param x
	 * @param y
	 * @see java.awt.Graphics2D#drawImage(java.awt.image.BufferedImage, java.awt.image.BufferedImageOp, int, int)
	 */
	@Override
	public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
		this.delegate.drawImage(img, op, x, y);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @see java.awt.Graphics#drawOval(int, int, int, int)
	 */
	@Override
	public void drawOval(int x, int y, int width, int height) {
		this.delegate.drawOval(x, y, width, height);
	}

	/**
	 * @param img
	 * @param xform
	 * @see java.awt.Graphics2D#drawRenderedImage(java.awt.image.RenderedImage, java.awt.geom.AffineTransform)
	 */
	@Override
	public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
		this.delegate.drawRenderedImage(img, xform);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @see java.awt.Graphics#fillOval(int, int, int, int)
	 */
	@Override
	public void fillOval(int x, int y, int width, int height) {
		this.delegate.fillOval(x, y, width, height);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param startAngle
	 * @param arcAngle
	 * @see java.awt.Graphics#drawArc(int, int, int, int, int, int)
	 */
	@Override
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		this.delegate.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	/**
	 * @param img
	 * @param xform
	 * @see java.awt.Graphics2D#drawRenderableImage(java.awt.image.renderable.RenderableImage, java.awt.geom.AffineTransform)
	 */
	@Override
	public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
		this.delegate.drawRenderableImage(img, xform);
	}

	/**
	 * @param str
	 * @param x
	 * @param y
	 * @see java.awt.Graphics2D#drawString(java.lang.String, int, int)
	 */
	@Override
	public void drawString(String str, int x, int y) {
		this.delegate.drawString(str, x, y);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param startAngle
	 * @param arcAngle
	 * @see java.awt.Graphics#fillArc(int, int, int, int, int, int)
	 */
	@Override
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		this.delegate.fillArc(x, y, width, height, startAngle, arcAngle);
	}

	/**
	 * @param str
	 * @param x
	 * @param y
	 * @see java.awt.Graphics2D#drawString(java.lang.String, float, float)
	 */
	@Override
	public void drawString(String str, float x, float y) {
		this.delegate.drawString(str, x, y);
	}

	/**
	 * @param xPoints
	 * @param yPoints
	 * @param nPoints
	 * @see java.awt.Graphics#drawPolyline(int[], int[], int)
	 */
	@Override
	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
		this.delegate.drawPolyline(xPoints, yPoints, nPoints);
	}

	/**
	 * @param iterator
	 * @param x
	 * @param y
	 * @see java.awt.Graphics2D#drawString(java.text.AttributedCharacterIterator, int, int)
	 */
	@Override
	public void drawString(AttributedCharacterIterator iterator, int x, int y) {
		this.delegate.drawString(iterator, x, y);
	}

	/**
	 * @param xPoints
	 * @param yPoints
	 * @param nPoints
	 * @see java.awt.Graphics#drawPolygon(int[], int[], int)
	 */
	@Override
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		this.delegate.drawPolygon(xPoints, yPoints, nPoints);
	}

	/**
	 * @param iterator
	 * @param x
	 * @param y
	 * @see java.awt.Graphics2D#drawString(java.text.AttributedCharacterIterator, float, float)
	 */
	@Override
	public void drawString(AttributedCharacterIterator iterator, float x, float y) {
		this.delegate.drawString(iterator, x, y);
	}

	/**
	 * @param p
	 * @see java.awt.Graphics#drawPolygon(java.awt.Polygon)
	 */
	@Override
	public void drawPolygon(Polygon p) {
		this.delegate.drawPolygon(p);
	}

	/**
	 * @param xPoints
	 * @param yPoints
	 * @param nPoints
	 * @see java.awt.Graphics#fillPolygon(int[], int[], int)
	 */
	@Override
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		this.delegate.fillPolygon(xPoints, yPoints, nPoints);
	}

	/**
	 * @param g
	 * @param x
	 * @param y
	 * @see java.awt.Graphics2D#drawGlyphVector(java.awt.font.GlyphVector, float, float)
	 */
	@Override
	public void drawGlyphVector(GlyphVector g, float x, float y) {
		this.delegate.drawGlyphVector(g, x, y);
	}

	/**
	 * @param p
	 * @see java.awt.Graphics#fillPolygon(java.awt.Polygon)
	 */
	@Override
	public void fillPolygon(Polygon p) {
		this.delegate.fillPolygon(p);
	}

	/**
	 * @param s
	 * @see java.awt.Graphics2D#fill(java.awt.Shape)
	 */
	@Override
	public void fill(Shape s) {
		this.delegate.fill(s);
	}

	/**
	 * @param rect
	 * @param s
	 * @param onStroke
	 * @return
	 * @see java.awt.Graphics2D#hit(java.awt.Rectangle, java.awt.Shape, boolean)
	 */
	@Override
	public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
		return this.delegate.hit(rect, s, onStroke);
	}

	/**
	 * @param data
	 * @param offset
	 * @param length
	 * @param x
	 * @param y
	 * @see java.awt.Graphics#drawChars(char[], int, int, int, int)
	 */
	@Override
	public void drawChars(char[] data, int offset, int length, int x, int y) {
		this.delegate.drawChars(data, offset, length, x, y);
	}

	/**
	 * @return
	 * @see java.awt.Graphics2D#getDeviceConfiguration()
	 */
	@Override
	public GraphicsConfiguration getDeviceConfiguration() {
		return this.delegate.getDeviceConfiguration();
	}

	/**
	 * @param comp
	 * @see java.awt.Graphics2D#setComposite(java.awt.Composite)
	 */
	@Override
	public void setComposite(Composite comp) {
		this.delegate.setComposite(comp);
	}

	/**
	 * @param data
	 * @param offset
	 * @param length
	 * @param x
	 * @param y
	 * @see java.awt.Graphics#drawBytes(byte[], int, int, int, int)
	 */
	@Override
	public void drawBytes(byte[] data, int offset, int length, int x, int y) {
		this.delegate.drawBytes(data, offset, length, x, y);
	}

	/**
	 * @param paint
	 * @see java.awt.Graphics2D#setPaint(java.awt.Paint)
	 */
	@Override
	public void setPaint(Paint paint) {
		this.delegate.setPaint(paint);
	}

	/**
	 * @param img
	 * @param x
	 * @param y
	 * @param observer
	 * @return
	 * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, java.awt.image.ImageObserver)
	 */
	@Override
	public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
		return this.delegate.drawImage(img, x, y, observer);
	}

	/**
	 * @param s
	 * @see java.awt.Graphics2D#setStroke(java.awt.Stroke)
	 */
	@Override
	public void setStroke(Stroke s) {
		this.delegate.setStroke(s);
	}

	/**
	 * @param hintKey
	 * @param hintValue
	 * @see java.awt.Graphics2D#setRenderingHint(java.awt.RenderingHints.Key, java.lang.Object)
	 */
	@Override
	public void setRenderingHint(Key hintKey, Object hintValue) {
		this.delegate.setRenderingHint(hintKey, hintValue);
	}

	/**
	 * @param hintKey
	 * @return
	 * @see java.awt.Graphics2D#getRenderingHint(java.awt.RenderingHints.Key)
	 */
	@Override
	public Object getRenderingHint(Key hintKey) {
		return this.delegate.getRenderingHint(hintKey);
	}

	/**
	 * @param img
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param observer
	 * @return
	 * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, java.awt.image.ImageObserver)
	 */
	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
		return this.delegate.drawImage(img, x, y, width, height, observer);
	}

	/**
	 * @param hints
	 * @see java.awt.Graphics2D#setRenderingHints(java.util.Map)
	 */
	@Override
	public void setRenderingHints(Map<?, ?> hints) {
		this.delegate.setRenderingHints(hints);
	}

	/**
	 * @param hints
	 * @see java.awt.Graphics2D#addRenderingHints(java.util.Map)
	 */
	@Override
	public void addRenderingHints(Map<?, ?> hints) {
		this.delegate.addRenderingHints(hints);
	}

	/**
	 * @return
	 * @see java.awt.Graphics2D#getRenderingHints()
	 */
	@Override
	public RenderingHints getRenderingHints() {
		return this.delegate.getRenderingHints();
	}

	/**
	 * @param img
	 * @param x
	 * @param y
	 * @param bgcolor
	 * @param observer
	 * @return
	 * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, java.awt.Color, java.awt.image.ImageObserver)
	 */
	@Override
	public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
		return this.delegate.drawImage(img, x, y, bgcolor, observer);
	}

	/**
	 * @param x
	 * @param y
	 * @see java.awt.Graphics2D#translate(int, int)
	 */
	@Override
	public void translate(int x, int y) {
		this.delegate.translate(x, y);
	}

	/**
	 * @param tx
	 * @param ty
	 * @see java.awt.Graphics2D#translate(double, double)
	 */
	@Override
	public void translate(double tx, double ty) {
		this.delegate.translate(tx, ty);
	}

	/**
	 * @param theta
	 * @see java.awt.Graphics2D#rotate(double)
	 */
	@Override
	public void rotate(double theta) {
		this.delegate.rotate(theta);
	}

	/**
	 * @param img
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param bgcolor
	 * @param observer
	 * @return
	 * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, java.awt.Color, java.awt.image.ImageObserver)
	 */
	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
		return this.delegate.drawImage(img, x, y, width, height, bgcolor, observer);
	}

	/**
	 * @param theta
	 * @param x
	 * @param y
	 * @see java.awt.Graphics2D#rotate(double, double, double)
	 */
	@Override
	public void rotate(double theta, double x, double y) {
		this.delegate.rotate(theta, x, y);
	}

	/**
	 * @param sx
	 * @param sy
	 * @see java.awt.Graphics2D#scale(double, double)
	 */
	@Override
	public void scale(double sx, double sy) {
		this.delegate.scale(sx, sy);
	}

	/**
	 * @param shx
	 * @param shy
	 * @see java.awt.Graphics2D#shear(double, double)
	 */
	@Override
	public void shear(double shx, double shy) {
		this.delegate.shear(shx, shy);
	}

	/**
	 * @param img
	 * @param dx1
	 * @param dy1
	 * @param dx2
	 * @param dy2
	 * @param sx1
	 * @param sy1
	 * @param sx2
	 * @param sy2
	 * @param observer
	 * @return
	 * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, int, int, int, int, java.awt.image.ImageObserver)
	 */
	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			ImageObserver observer) {
		return this.delegate.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
	}

	/**
	 * @param tx
	 * @see java.awt.Graphics2D#transform(java.awt.geom.AffineTransform)
	 */
	@Override
	public void transform(AffineTransform tx) {
		this.delegate.transform(tx);
	}

	/**
	 * @param tx
	 * @see java.awt.Graphics2D#setTransform(java.awt.geom.AffineTransform)
	 */
	@Override
	public void setTransform(AffineTransform tx) {
		this.delegate.setTransform(tx);
	}

	/**
	 * @return
	 * @see java.awt.Graphics2D#getTransform()
	 */
	@Override
	public AffineTransform getTransform() {
		return this.delegate.getTransform();
	}

	/**
	 * @param img
	 * @param dx1
	 * @param dy1
	 * @param dx2
	 * @param dy2
	 * @param sx1
	 * @param sy1
	 * @param sx2
	 * @param sy2
	 * @param bgcolor
	 * @param observer
	 * @return
	 * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, int, int, int, int, java.awt.Color, java.awt.image.ImageObserver)
	 */
	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			Color bgcolor, ImageObserver observer) {
		return this.delegate.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer);
	}

	/**
	 * @return
	 * @see java.awt.Graphics2D#getPaint()
	 */
	@Override
	public Paint getPaint() {
		return this.delegate.getPaint();
	}

	/**
	 * @return
	 * @see java.awt.Graphics2D#getComposite()
	 */
	@Override
	public Composite getComposite() {
		return this.delegate.getComposite();
	}

	/**
	 * @param color
	 * @see java.awt.Graphics2D#setBackground(java.awt.Color)
	 */
	@Override
	public void setBackground(Color color) {
		this.delegate.setBackground(color);
	}

	/**
	 * @return
	 * @see java.awt.Graphics2D#getBackground()
	 */
	@Override
	public Color getBackground() {
		return this.delegate.getBackground();
	}

	/**
	 * @return
	 * @see java.awt.Graphics2D#getStroke()
	 */
	@Override
	public Stroke getStroke() {
		return this.delegate.getStroke();
	}

	/**
	 * @param s
	 * @see java.awt.Graphics2D#clip(java.awt.Shape)
	 */
	@Override
	public void clip(Shape s) {
		this.delegate.clip(s);
	}

	/**
	 * @return
	 * @see java.awt.Graphics2D#getFontRenderContext()
	 */
	@Override
	public FontRenderContext getFontRenderContext() {
		return this.delegate.getFontRenderContext();
	}

	/**
	 * 
	 * @see java.awt.Graphics#dispose()
	 */
	@Override
	public void dispose() {
		this.delegate.dispose();
	}

	/**
	 * @return
	 * @see java.awt.Graphics#toString()
	 */
	@Override
	public String toString() {
		return this.delegate.toString();
	}

	/**
	 * @return
	 * @deprecated
	 * @see java.awt.Graphics#getClipRect()
	 */
	@Deprecated
	@Override
	public Rectangle getClipRect() {
		return this.delegate.getClipRect();
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 * @see java.awt.Graphics#hitClip(int, int, int, int)
	 */
	@Override
	public boolean hitClip(int x, int y, int width, int height) {
		return this.delegate.hitClip(x, y, width, height);
	}

	/**
	 * @param r
	 * @return
	 * @see java.awt.Graphics#getClipBounds(java.awt.Rectangle)
	 */
	@Override
	public Rectangle getClipBounds(Rectangle r) {
		return this.delegate.getClipBounds(r);
	}

}
