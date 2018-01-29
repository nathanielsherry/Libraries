package swidget.stratus.painters.spinner;

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
import swidget.stratus.painters.ButtonPainter;

public class PreviousButtonPainter extends ButtonPainter {

	public PreviousButtonPainter(ButtonState... buttonStates) {
		super(buttonStates);
	}
	
	@Override
    public void paint(Graphics2D g, JComponent object, int width, int height) {
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    	
    	float pad = margin, lpad = 0, bpad = 0, tpad=0;
    	
    	
    	
    	//Bevel under button
    	g.setPaint(Color.WHITE);
    	Shape bevel = new RoundRectangle2D.Float(lpad, tpad+1, width-pad-lpad, height-pad-bpad, radius, radius);     
    	g.fill(bevel);
    	
    	//Border
    	g.setPaint(borderColor);
    	Shape border = new RoundRectangle2D.Float(lpad, tpad-radius, width-pad-lpad, height-pad-bpad+radius, radius, radius);     
    	g.fill(border);
    	
    	
    	
    	//Main fill
    	pad += borderWidth;
    	lpad += borderWidth;
    	bpad += borderWidth;
    	tpad += borderWidth;
    	Shape fillArea = new RoundRectangle2D.Float(lpad, tpad-radius, width-pad-lpad, height-pad-bpad+radius, radius, radius);
    	g.setPaint(new LinearGradientPaint(0, -(height-(pad+1)), 0, height-bpad, points, colours));
    	g.fill(fillArea);


    	
    	//Restore border at top after mail fill was overextended
    	//Border
    	g.setPaint(borderColor);
    	//g.drawLine(0, 0, (int)(width-pad), 0);
    	
	}
	
	
}
