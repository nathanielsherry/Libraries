package swidget.stratus.painters.toolbar;

import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.Painter;

import swidget.stratus.Stratus;

public class ToolbarBorderPainter implements Painter<JComponent> {

	public enum Side {
		NORTH,
		SOUTH,
		EAST,
		WEST
	}
	
	private Side side;
	public ToolbarBorderPainter(Side side) {
		this.side = side;
	}
	@Override
	public void paint(Graphics2D g, JComponent object, int width, int height) {
		
		int startX=0, startY=0, endX=0, endY=0;
		switch (side) {
		case EAST:
		case SOUTH: startX = 0; startY = 0; endX = width; endY = 0; break;
		case WEST:
		case NORTH: startX = 0; startY = height-1; endX = width; endY = height-1; break;
		}
		
		
		g.setPaint(Stratus.border);
		g.drawLine(startX, startY, endX, endY);
		
	}
	
}
