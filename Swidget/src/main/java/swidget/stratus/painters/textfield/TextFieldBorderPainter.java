package swidget.stratus.painters.textfield;

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
import swidget.stratus.painters.StatefulPainter;

public class TextFieldBorderPainter extends StatefulPainter {

	protected int margin = 2;
	protected float radius = Stratus.borderRadius;
	protected float[] points = new float[] {0f, 0.25f};
	
	public TextFieldBorderPainter(ButtonState... buttonStates) {
		super(buttonStates);
	}
	
	@Override
	public void paint(Graphics2D g, JComponent object, int width, int height) {
		
		//EMPTY, SEE BACKGROUND PAINTER
	}

}
