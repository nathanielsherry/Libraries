package swidget.stratus.painters.toolbar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;

import javax.swing.JComponent;
import javax.swing.Painter;

import swidget.stratus.Stratus;

public class ToolbarBackgroundPainter implements Painter<JComponent> {

    private Color c1, c2, c3, c4;
    protected float[] points = new float[] {0f, 0.75f, 0.9f, 1.0f};
    
    
    public ToolbarBackgroundPainter() {
    	this.c1 = Stratus.control;
    	this.c2 = Stratus.darken(c1, 0.01f);
    	this.c3 = Stratus.darken(c2, 0.01f);
    	this.c4 = Stratus.darken(c3, 0.01f);
    }

    @Override
    public void paint(Graphics2D g, JComponent object, int width, int height) {
   	
        g.setPaint(new LinearGradientPaint(0, 0, 0, height, points, new Color[] {c1, c2, c3, c4}));
        g.fillRect(0, 0, width-1, height-1);
    }

}
