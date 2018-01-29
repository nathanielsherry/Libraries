package swidget.stratus.painters.checkbutton;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import swidget.stratus.Stratus;
import swidget.stratus.Stratus.ButtonState;
import swidget.stratus.painters.ButtonPainter;

public class CheckButtonPainter extends ButtonPainter {

	public CheckButtonPainter(ButtonState... buttonStates) {
		super(buttonStates);
		//borderColor = Stratus.darken(Stratus.border, 0.1f);
		
		this.colours = new Color[] {Stratus.lighten(colours[0]), c1, c2};
		this.points = new float[] {0, 0.2f, 1f};
	}
	
	@Override
    public void paint(Graphics2D g, JComponent object, int width, int height) {
		//radius = width;
		super.paint(g, object, width, height);
	}
	
}
