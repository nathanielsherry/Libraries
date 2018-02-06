package swidget.stratus.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;


import javax.swing.JComponent;

import java.awt.BasicStroke;

import swidget.stratus.Stratus;
import swidget.stratus.Stratus.ButtonState;

//Fills the area of button style controls (no borders, etc)
public class ButtonPainter extends StatefulPainter {

	protected Color c1, c2;
	protected Color[] colours;
    protected Color borderColor;
    
    protected float[] points = new float[] {0f, 1.0f};
    protected float radius = Stratus.borderRadius;
    protected float borderWidth = 1;
    
    protected int margin = 1;
    
    

    
    public ButtonPainter(ButtonState... buttonStates) {
    	this(1, buttonStates);
    }
    
    public ButtonPainter(int margin, ButtonState... buttonStates) {
    	super(buttonStates);
    	setColours();
    	this.margin = margin;
    }
    
    private void setColours() {
    	   	
    	
    	Color base = Stratus.darken(Stratus.control, 0.02f);
    	if (isMouseOver()) {
    		base = Stratus.lighten(base);
    	}
    	
    	//ENABLED is default
    	this.c1 = Stratus.lighten(base, 0.06f);
    	this.c2 = Stratus.darken(base, 0.06f);
    	Color c0 = Stratus.lighten(base, 0.12f);
    	this.borderColor = Stratus.border;
    	
    	if (isPressed() || isSelected()) {
    		this.c1 = Stratus.darken(base, 0.12f);
    		this.c2 = Stratus.darken(base, 0.06f);
        	this.borderColor = Stratus.border;
    	}

    	
    	if (isDisabled()) {
    		this.c1 = Stratus.control;
    		this.c2 = Stratus.control;
    		this.borderColor = Stratus.border;
    		
    		//Disabled and selected, like toggle button
        	if (isSelected()) {
        		this.c1 = Stratus.darken(c1, 0.06f);
        		this.c2 = Stratus.darken(c2, 0.06f);
            	this.borderColor = Stratus.border;
        	}
    		
    	}
    	
		this.colours = new Color[] {c1, c2};
		this.points = new float[] {0, 1f};
    }
    

    @Override
    public void paint(Graphics2D g, JComponent object, int width, int height) {
   	
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    	
    	float pad = margin;
    	float bevelTop = 0;
    	
    	//Bevel under button
    	g.setPaint(new Color(1f, 1f, 1f, 0.5f));
    	Shape bevel = new RoundRectangle2D.Float(pad, pad+1, width-pad*2, height-pad*2, radius, radius);     
    	g.fill(bevel);
    	
    	//Border
    	g.setPaint(borderColor);
    	Shape border = new RoundRectangle2D.Float(pad, pad, width-pad*2, height-pad*2, radius, radius);     
    	g.fill(border);
    	
    	
    	//Bevel at top of button unless pressed
    	if (!(isPressed() || isSelected()) && !(isDisabled())) {
	    	g.setPaint(Color.WHITE);
	    	bevel = new RoundRectangle2D.Float(pad+1, pad+1, width-pad*2-2, height-pad*2-2, radius, radius);     
	    	g.fill(bevel);
	    	bevelTop += 1;
    	}
    	
    	
    	//Main fill
    	pad = margin + borderWidth;
    	Shape fillArea = new RoundRectangle2D.Float(pad, pad+bevelTop, width-pad*2, height-pad*2-bevelTop, radius, radius);
    	g.setPaint(new LinearGradientPaint(0, pad, 0, height-pad, points, colours));
    	g.fill(fillArea);
    	
    	//Focus dash if focused but not pressed
    	pad += 1;
    	if (isFocused() && !isPressed()) {
        	g.setPaint(new Color(0, 0, 0, 0.15f));
        	Shape focus = new RoundRectangle2D.Float(pad, pad, width-pad*2-1, height-pad*2-1, radius, radius);
        	Stroke old = g.getStroke();
        	g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[] {2, 2}, 0f));
        	g.draw(focus);
        	g.setStroke(old);
    	}
    	
    	
    }

}
