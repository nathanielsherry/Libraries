package swidget.stratus.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;

import javax.swing.JComponent;
import javax.swing.Painter;

import swidget.stratus.Stratus;

public class TableHeaderPainter implements Painter<JComponent>{

	
	
	@Override
	public void paint(Graphics2D g, JComponent object, int width, int height) {
		// TODO Auto-generated method stub
		
		
		g.setPaint(new LinearGradientPaint(0, 0, 0, height, new float[] {0f, 1}, new Color[] {Color.WHITE, Stratus.control}));
		g.fillRect(-1, 0, width+1, height-1);
		
		g.setColor(Color.WHITE);
		g.fillRect(-1, 0, width, height-1);
		
		
		
	}
	
	

}
