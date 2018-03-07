package swidget.stratus.painters.progressbar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.Painter;

import swidget.stratus.Stratus;


public class ProgressBarForegroundPainter implements Painter<JComponent> {

	public enum Mode {
		EMPTY,
		INDETERMINATE,
		FULL
	}

	protected Color c1, c2, c3, c4, c5;
	private boolean enabled=true;
	private Mode mode;
	
	public ProgressBarForegroundPainter(boolean enabled, Mode mode) {
		this.enabled = enabled;
		this.mode = mode;
		
		c1 = Stratus.saturate(Stratus.lighten(Stratus.highlight, 0.2f), 0.05f);
		c2 = Stratus.saturate(Stratus.highlight, 0.05f);
		c3 = Stratus.saturate(Stratus.darken(Stratus.highlight, 0.2f), 0.05f);
		c4 = Stratus.desaturate(Stratus.lighten(Stratus.highlight, 0.05f), 0.04f);
		c5 = Stratus.desaturate(Stratus.lighten(Stratus.highlight, 0.2f), 0.2f);
	}
	
	
	@Override
	public void paint(Graphics2D g, JComponent object, int width, int height) {
    	
		int pad = 2;
		float radius = Stratus.borderRadius;
		
		//LinearGradientPaint cannot handle the start and end point being the same
		if (height == 0) height = 1;
		
		g.setPaint(new LinearGradientPaint(0, 0, 0, height, new float[] {0, 0.15f, 0.6f, 0.9f, 1}, new Color[] {c1, c2, c3, c4, c5}));
    	Shape border = new RoundRectangle2D.Float(pad, pad, width-pad*2, height-pad*2, radius, radius);
    	g.fill(border);

    	//Border
    	g.setPaint(c3);
    	g.draw(border);
    	
    	Shape bevel = new RoundRectangle2D.Float(pad+1, pad+1, width-pad*2-2, height-pad*2, radius, radius);
    	g.setPaint(new LinearGradientPaint(0, 0, 0, height, new float[] {0.2f, 0.6f}, new Color[] {c1, new Color(1f, 1f, 1f, 0f)}));
    	g.draw(bevel);
    	
    	Shape oldClip = g.getClip();
    	g.clip(border);
    	
    	g.setColor(new Color(0, 0, 0, 0.08f));
    	int increment=20;
    	for (int i = 0; i < width+increment*2; i+=increment) {
    		if ((i/increment)%2 == 0) {
    			g.setColor(new Color(0, 0, 0, 0.04f));		
    		} else {
    			g.setColor(new Color(1, 1, 1, 0.04f));
    		}
    		GeneralPath bar = new GeneralPath();
    		bar.moveTo(i, 0);
    		bar.lineTo(i-height, height);
    		bar.lineTo(i-height+increment, height);
    		bar.lineTo(i+increment, 0);
    		bar.closePath();
    		g.fill(bar);
    	}
    	
    	g.setClip(oldClip);
    	
	}

}
