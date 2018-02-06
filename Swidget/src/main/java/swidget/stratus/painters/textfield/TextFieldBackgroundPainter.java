package swidget.stratus.painters.textfield;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;

import swidget.stratus.Stratus;
import swidget.stratus.Stratus.ButtonState;
import swidget.stratus.painters.StatefulPainter;

public class TextFieldBackgroundPainter extends StatefulPainter {

	protected int margin = 2;
	protected float radius = Stratus.borderRadius;
	protected float[] points = new float[] {0f, 0.25f};
	
	protected Color c1, c2;
	
	public TextFieldBackgroundPainter(ButtonState... buttonStates) {
		super(buttonStates);
		
		if (!isDisabled()) {
			c1 = Stratus.darken(Color.WHITE, 0.05f);
			c2 = Color.WHITE;
		} else {
			c1 = Stratus.darken(Stratus.control, 0.05f);
			c2 = Stratus.control;
		}
	}
	
	@Override
	public void paint(Graphics2D g, JComponent object, int width, int height) {
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    	
    	float pad = margin;
    	   
  
    	//Bevel under area
    	g.setPaint(new Color(1f, 1f, 1f, 0.5f));
    	Shape bevel = new RoundRectangle2D.Float(pad, pad+1, width-pad*2, height-pad*2+1, radius, radius);     
    	g.fill(bevel);
    	
    	

    	//Border
    	Stroke old = g.getStroke();
    	float borderStroke = 1;
    	if (isSelected() || isFocused()) {
    		g.setPaint(Stratus.highlight);
    		borderStroke = 2;
    	} else {
    		g.setPaint(Stratus.border);
    	}
    	
    	g.setStroke(new BasicStroke(borderStroke));
    	Shape border = new RoundRectangle2D.Float(pad, pad, width-pad*2, height-pad*2, radius, radius);     
    	g.draw(border);
    	
    	g.setStroke(old);
    	
    	
    	//Main fill
    	pad = margin + 1;
    	Shape fillArea = new RoundRectangle2D.Float(pad, pad, width-pad*2+1, height-pad*2+1, radius, radius);
    	g.setPaint(new LinearGradientPaint(0, pad, 0, height-pad, points, new Color[] {c1, c2}));
    	g.fill(fillArea);

		
	}

}
