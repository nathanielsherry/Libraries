package swidget.stratus.painters;

import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.Painter;

import swidget.stratus.Stratus;

public class SplitPaneDividerPainter implements Painter<JComponent> {

	@Override
	public void paint(Graphics2D g, JComponent object, int width, int height) {
		
		g.setColor(Stratus.control);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Stratus.border);
		g.drawLine(0, 0, width, 0);
		g.drawLine(0, height-1, width, height-1);
		
		g.setColor(Stratus.lighten(Stratus.control));
		g.drawLine(0, 1, width, 1);
		g.drawLine(0, height-2, width, height-2);
		
	}

}
