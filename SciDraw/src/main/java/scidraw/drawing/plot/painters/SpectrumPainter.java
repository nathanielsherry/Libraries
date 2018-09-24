package scidraw.drawing.plot.painters;



import scidraw.drawing.DrawingRequest;
import scidraw.drawing.painters.PainterData;
import scitypes.Coord;
import scitypes.ReadOnlySpectrum;
import scitypes.Spectrum;
import scitypes.visualization.Surface;


public abstract class SpectrumPainter extends PlotPainter
{

	protected ReadOnlySpectrum data;
	
	public SpectrumPainter(ReadOnlySpectrum data)
	{
		this.data = data;
	}

	protected void traceData(PainterData p)
	{
		traceData(p, data);
	}
	
	protected void traceData(PainterData p, TraceType traceType)
	{
		traceData(p, data, traceType);
	}
	
	protected void traceData(Surface context, DrawingRequest dr, Coord<Float> plotSize, Spectrum dataHeights, TraceType traceType)
	{
		traceData(context, dr, plotSize, dataHeights, traceType, data);
	}

	
}
