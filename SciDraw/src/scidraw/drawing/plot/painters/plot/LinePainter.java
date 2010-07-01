package scidraw.drawing.plot.painters.plot;


import java.awt.Color;

import scidraw.drawing.painters.PainterData;
import scidraw.drawing.plot.painters.SpectrumPainter;
import scitypes.Spectrum;

public class LinePainter extends SpectrumPainter
{

	protected Color colour;
	
	
	public LinePainter(Spectrum data, Color colour)
	{
		super(data);
		this.colour = colour;
	}
	
	public LinePainter(Spectrum data)
	{
		super(data);
		this.colour = new Color(0, 0, 0);
	}
	
	@Override
	public void drawElement(PainterData p)
	{
		traceData(p, TraceType.CONNECTED);
		p.context.setSource(colour);
		p.context.stroke();
	}

}
