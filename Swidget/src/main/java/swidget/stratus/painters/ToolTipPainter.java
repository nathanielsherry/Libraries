package swidget.stratus.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.Painter;

import swidget.stratus.Stratus;

public class ToolTipPainter implements Painter<JComponent> {

	private float radius = Stratus.borderRadius*1.5f;
	
	@Override
	public void paint(Graphics2D g, JComponent object, int width, int height) {
		
		Shape area = new RoundRectangle2D.Float(0, 0, width, height, radius, radius);
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		
		g.setPaint(Color.black);
		g.fill(area);
	}

}
