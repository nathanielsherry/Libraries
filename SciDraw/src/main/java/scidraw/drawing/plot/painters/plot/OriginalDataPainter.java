package scidraw.drawing.plot.painters.plot;


import java.awt.Color;

import scidraw.drawing.painters.PainterData;
import scitypes.ReadOnlySpectrum;

public class OriginalDataPainter extends LinePainter
{
	
	public OriginalDataPainter(ReadOnlySpectrum data, boolean isMonochrome)
	{
		super(data, getColour(isMonochrome));
	}
	public OriginalDataPainter(ReadOnlySpectrum data)
	{
		super(data, getColour(false));
	}


	private static Color getColour(boolean isMonochrome)
	{
		if (! isMonochrome) {
			return new Color(0x60D32F2F, true);
		} else {
			return new Color(0x7f000000, true);
		}
	}
		
	@Override
	public void drawElement(PainterData p)
	{
		traceData(p, TraceType.CONNECTED);
		p.context.setSource(colour);
		p.context.stroke();
	}

}
