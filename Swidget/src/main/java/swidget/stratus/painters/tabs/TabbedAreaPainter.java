package swidget.stratus.painters.tabs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.Painter;

import swidget.stratus.Stratus;

public class TabbedAreaPainter implements Painter<JComponent> {

	protected Color c1, c2;
	
	protected boolean enabled = true;

	private int lastWidth=-1, lastHeight=-1;
	private Paint lastPaint;
	
	public TabbedAreaPainter(boolean enabled) {
		this.enabled = enabled;
		if (enabled) {
			c1 = Stratus.darken(Stratus.control, 0.15f);
			c2 = Stratus.darken(Stratus.control, 0.1f);
		} else {
			c1 = Stratus.control;
			c2 = Stratus.control;
		}
		
	}
	
	@Override
	public void paint(Graphics2D g, JComponent object, int width, int height) {

		if (width != lastWidth || height != lastHeight || lastPaint == null) {
			lastPaint = new LinearGradientPaint(0, 0, 0, height, new float[] {0, 0.25f}, new Color[] {c1, c2});
			lastWidth = width;
			lastHeight = height;
		}
		
    	g.setPaint(lastPaint);
    	g.fillRect(0, 0, width, height);
    	g.setColor(Stratus.border);
    	g.drawLine(0, height-1, width, height-1);
		
	}

}
