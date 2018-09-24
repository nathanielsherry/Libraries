package scitypes.visualization.drawing.painters;


import scitypes.Coord;
import scitypes.ISpectrum;
import scitypes.ReadOnlySpectrum;
import scitypes.Spectrum;
import scitypes.visualization.Surface;
import scitypes.visualization.drawing.Drawing;
import scitypes.visualization.drawing.DrawingRequest;

/**
 * 
 * This class is a structure for holding data needed by a {@link Painter} when drawing to a {@link Drawing}
 * 
 * @author Nathaniel Sherry, 2009
 * @see Painter
 * @see Drawing
 *
 */

public class PainterData
{
	public Surface context;
	public DrawingRequest dr;
	public Coord<Float> plotSize;
	public Spectrum dataHeights;
	public ReadOnlySpectrum originalHeights; //TODO: maybe dataHeights, decorationHeights?
	
	
	public PainterData(Surface context, DrawingRequest dr, Coord<Float> plotSize, Spectrum dataHeights)
	{
		this.context = context;
		this.dr = dr;
		this.plotSize = plotSize;
		this.dataHeights = dataHeights;
		this.originalHeights = new ISpectrum(dataHeights);
	}
	
	public double getChannelXValue(double channel)
	{
		double channelSize = plotSize.x / dr.dataWidth;
		return channel * channelSize;
	}
	
}
