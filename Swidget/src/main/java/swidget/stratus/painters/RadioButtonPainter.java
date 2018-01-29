package swidget.stratus.painters;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import swidget.stratus.Stratus;
import swidget.stratus.Stratus.ButtonState;

public class RadioButtonPainter extends ButtonPainter {

	private boolean selected;
	public RadioButtonPainter(boolean selected, ButtonState... buttonStates) {
		super(buttonStates);
		this.selected = selected;
		borderColor = Stratus.darken(Stratus.border, 0.1f);
		
		this.colours = new Color[] {Stratus.lighten(colours[0]), c1, c2};
		this.points = new float[] {0, 0.2f, 1f};
	}
	
	@Override
    public void paint(Graphics2D g, JComponent object, int width, int height) {
		radius = width;
		super.paint(g, object, width, height);
		
		if (selected) {
			if (isDisabled()) {
				g.setColor(Stratus.border);
			} else {
				g.setColor(Stratus.controlText);				
			}
			
			g.fillArc(6, 6, width-12, height-12, 0, 360);
		}
	}
	
}
