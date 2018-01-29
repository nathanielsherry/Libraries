package swidget.stratus.painters.checkbutton;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JComponent;
import javax.swing.Painter;

import swidget.stratus.Stratus;

public class CheckPainter implements Painter<JComponent>{

	private int pad = 0;
	private int voffset = 0;
	private boolean enabled = true;
	
	public CheckPainter(int pad, int voffset, boolean enabled) {
		this.pad = pad;
		this.voffset = voffset;
		this.enabled = false;
	}
	
	public CheckPainter(int pad, int voffset) {
		this.pad = pad;
		this.voffset = voffset;
	}

	
	@Override
	public void paint(Graphics2D g, JComponent object, int w, int h) {
		
		if (enabled) {
			g.setPaint(Stratus.controlText);
		} else {
			g.setPaint(Stratus.border);
		}
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		
		int height = (h-pad*2);
		int width = (w-pad*2);
		
		
		Stroke old = g.getStroke();
		g.setStroke(new BasicStroke(2));
		g.drawLine(pad, pad+height/2+voffset, pad+width/3, pad+height+voffset);
		g.drawLine(pad+width/3, pad+height+voffset, pad+width, pad+voffset);
		g.setStroke(old);
		
	}

}
