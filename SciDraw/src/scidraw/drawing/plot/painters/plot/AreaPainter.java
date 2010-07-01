package scidraw.drawing.plot.painters.plot;


import java.awt.Color;

import scidraw.drawing.painters.PainterData;
import scidraw.drawing.plot.painters.SpectrumPainter;
import scitypes.Spectrum;


public class AreaPainter extends SpectrumPainter
{

	private Color topColour, bottomColour, strokeColour;

	public AreaPainter(Spectrum data, Color top, Color bottom, Color stroke)
	{
		super(data);
		topColour = top;
		bottomColour = bottom;
		strokeColour = stroke;
	}

	
	public AreaPainter(Spectrum data)
	{
		super(data);
		topColour = new Color(0.5f, 0.5f, 0.5f);
		bottomColour = new Color(0.35f, 0.35f, 0.35f);
		strokeColour = new Color(0.1f, 0.1f, 0.1f);
	}

	@Override
	public void drawElement(PainterData p)
	{

		traceData(p);

		if (p.dr.drawToVectorSurface) {
			// fill with flat green - this is due to a bug with pdf rendering in
			// Poppler when showing gradients
			p.context.setSource(bottomColour);
			p.context.fillPreserve();

		} else {

			p.context.setSourceGradient(0, 0, topColour, 0, p.plotSize.y, bottomColour);
			p.context.fillPreserve();

		}

		// stroke darker
		p.context.setSource(strokeColour);
		p.context.stroke();
		
	}


}
