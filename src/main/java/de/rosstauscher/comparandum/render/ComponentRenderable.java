package de.rosstauscher.comparandum.render;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.lang.reflect.InvocationTargetException;

import javax.swing.CellRendererPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.rosstauscher.comparandum.util.ComparandumException;

/*****************************************************************************
 * Can render a given Swing component. 
 * @author Bernd Rosstauscher (java@rosstauscher.de) Copyright 2011
 ****************************************************************************/

public class ComponentRenderable implements IRenderable {

    private final Component c;
    
    /*************************************************************************
     * Constructor to render a given component with the preferred size 
     * of the component.
     * @param c the component to render.
     ************************************************************************/
    
    public ComponentRenderable(Component c) {
    	super();
    	this.c = c;
    }
  
	/*************************************************************************
	 * paint
	 * @see de.rosstauscher.comparandum.render.IRenderable#paint(java.awt.Graphics2D)
	 ************************************************************************/
	@Override
	public void paint(final Graphics2D g) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
			        CellRendererPane pane = new CellRendererPane();
                    JFrame frm = new JFrame();
                    frm.getContentPane().add(pane);
                    frm.setVisible(true);
			        
			        Component component = ComponentRenderable.this.c;
					Dimension prefSize = component.getPreferredSize();
			        pane.paintComponent(g, component, null, 0, 0, prefSize.width, prefSize.height, true);
			        
			        frm.dispose();
				}
			});
		} catch (InterruptedException e) {
			throw new ComparandumException("Error painting component"+this.c, e);
		} catch (InvocationTargetException e) {
			throw new ComparandumException("Error painting component"+this.c, e);
		}
	}

	/*************************************************************************
	 * getDimension
	 * @see de.rosstauscher.comparandum.render.IRenderable#getDimension()
	 ************************************************************************/
	@Override
	public Dimension getDimension() {
		return this.c.getPreferredSize();
	}

}

