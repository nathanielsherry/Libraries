package swidget.stratus.painters.tabs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JComponent;
import javax.swing.Painter;

import swidget.stratus.Stratus;
import swidget.stratus.Stratus.ButtonState;
import swidget.stratus.painters.StatefulPainter;

public class TabPainter extends StatefulPainter{

	Color fill;
	Color bottom;
	Stroke bottomStroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	
	public TabPainter(ButtonState... buttonState) {
		super(buttonState);
		
		if (isSelected()) {
			fill = Stratus.darken(Stratus.control, 0.02f);
		} else {
			fill = Stratus.darken(Stratus.control, 0.08f);
		}

		if (isSelected()) {
			bottom = Stratus.highlight;
		} else {
			bottom = Stratus.darken(Stratus.border, 0.1f);
		}
		
	}

	
	@Override
	public void paint(Graphics2D g, JComponent object, int width, int height) {
		
		
		if (isFocused() || isSelected() || isMouseOver()) {
		
			g.setColor(fill);
			g.fillRect(0, 0, width, height);
			
			g.setColor(Stratus.border);
			g.drawRect(0, 0, width, height-1);
			
			Stroke old = g.getStroke();
			g.setStroke(bottomStroke);
			g.setColor(bottom);
			g.drawLine(0, height-2, width+1, height-2);
			g.setStroke(old);
		}
	}

}
