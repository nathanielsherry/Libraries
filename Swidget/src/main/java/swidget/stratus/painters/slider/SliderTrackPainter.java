package swidget.stratus.painters.slider;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.Painter;

import swidget.stratus.Stratus;

public class SliderTrackPainter implements Painter<JComponent> {

	boolean enabled=true;
	public SliderTrackPainter(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public void paint(Graphics2D g, JComponent object, int width, int height) {
		
		float radius = Stratus.borderRadius;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

		Shape area = new RoundRectangle2D.Float(0, height/2-2, width-1, 4, radius, radius);
		
		//Fill
		if (enabled) {
			g.setPaint(new LinearGradientPaint(0, height/2-2, 0, height/2-2+4, new float[] {0, 1f}, new Color[] {Stratus.border, Stratus.control}));
			g.fill(area);
		}
		
    	//Border
    	if (enabled) {
    		g.setPaint(Stratus.darken(Stratus.border, 0.1f));
    	} else {
    		g.setPaint(Stratus.border);
    	}
    	g.draw(area);
    	

		
	}

}
