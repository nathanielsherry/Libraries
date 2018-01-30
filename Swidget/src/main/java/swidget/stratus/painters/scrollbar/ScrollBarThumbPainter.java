package swidget.stratus.painters.scrollbar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.Painter;

import swidget.stratus.Stratus;
import swidget.stratus.Stratus.ButtonState;

public class ScrollBarThumbPainter implements Painter<JComponent> {

	private Color c; 
	
	public ScrollBarThumbPainter(ButtonState state) {
		c = Stratus.darken(Stratus.border, 0.1f);
		if (state == ButtonState.MOUSEOVER) {
			c = Stratus.darken(c, 0.1f);
		}
		if (state == ButtonState.PRESSED) {
			c = Stratus.highlight;
		}
	}
	
	@Override
	public void paint(Graphics2D g, JComponent object, int width, int height) {

    	float pad = 3;
    	float radius = Stratus.borderRadius*2;

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    	
    	g.setPaint(c);
    	Shape thumb = new RoundRectangle2D.Float(pad, pad, width-(pad*2), height-(pad*2), radius, radius);     
    	g.fill(thumb);
		
	}
	
	
}