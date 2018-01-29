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

public class NextButtonPainter extends ButtonPainter {

	public NextButtonPainter(ButtonState... buttonStates) {
		super(buttonStates);
	}
	
	@Override
    public void paint(Graphics2D g, JComponent object, int width, int height) {
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    	
    	float pad = margin, lpad = 0, bpad = 0;
    	float tpad = pad+1;
    	float bevelTop = 0;
    	
    	
    	//Border
    	g.setPaint(borderColor);
    	Shape border = new RoundRectangle2D.Float(lpad, tpad, width-pad-lpad, height+radius, radius, radius);     
    	g.fill(border);
    	
    	
    	//Bevel at top of button unless pressed
    	if (!(isPressed() || isSelected()) && !(isDisabled())) {
	    	g.setPaint(Color.WHITE);
	    	Shape bevel = new RoundRectangle2D.Float(lpad+1, tpad+1, width-pad-lpad-2, height-pad-bpad-2, radius, radius);     
	    	g.fill(bevel);
	    	bevelTop += 1;
    	}
    	
    	
    	//Main fill
    	pad += borderWidth;
    	lpad += borderWidth;
    	bpad += borderWidth;
    	tpad += borderWidth;
    	Shape fillArea = new RoundRectangle2D.Float(lpad, tpad+bevelTop, width-pad-lpad, height+radius, radius, radius);
    	g.setPaint(new LinearGradientPaint(0, tpad, 0, (height-tpad + height-bpad), points, colours));
    	g.fill(fillArea);

	}
	
	
}
