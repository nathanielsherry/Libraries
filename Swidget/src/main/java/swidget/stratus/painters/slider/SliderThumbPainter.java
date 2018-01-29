package swidget.stratus.painters.slider;

import java.awt.Graphics2D;

import javax.swing.JComponent;

import swidget.stratus.Stratus;
import swidget.stratus.Stratus.ButtonState;
import swidget.stratus.painters.ButtonPainter;

public class SliderThumbPainter extends ButtonPainter {

	public SliderThumbPainter(ButtonState... buttonStates) {
		super(buttonStates);
		borderColor = Stratus.darken(Stratus.border, 0.15f);
	}
	
	@Override
    public void paint(Graphics2D g, JComponent object, int width, int height) {
		radius = width;
		super.paint(g, object, width, height);		
	}
	
}
