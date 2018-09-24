package scidraw.drawing.plot.painters.plot;


import scidraw.drawing.painters.PainterData;
import scidraw.drawing.plot.painters.SpectrumPainter;
import scitypes.ReadOnlySpectrum;
import scitypes.visualization.palette.PaletteColour;

public class LinePainter extends SpectrumPainter
{

	protected PaletteColour colour;
	
	
	public LinePainter(ReadOnlySpectrum data, PaletteColour colour)
	{
		super(data);
		this.colour = colour;
	}
	
	public LinePainter(ReadOnlySpectrum data)
	{
		super(data);
		this.colour = new PaletteColour(0xff000000);
	}
	
	@Override
	public void drawElement(PainterData p)
	{
		traceData(p, TraceType.CONNECTED);
		p.context.setSource(colour);
		p.context.stroke();
	}

}
