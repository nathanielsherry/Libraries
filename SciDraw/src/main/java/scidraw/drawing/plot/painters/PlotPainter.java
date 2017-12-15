package scidraw.drawing.plot.painters;


import scidraw.drawing.DrawingRequest;
import scidraw.drawing.ViewTransform;
import scidraw.drawing.backends.Surface;
import scidraw.drawing.painters.Painter;
import scidraw.drawing.painters.PainterData;
import scidraw.drawing.plot.PlotDrawing;
import scitypes.Bounds;
import scitypes.Coord;
import scitypes.ISpectrum;
import scitypes.ReadOnlySpectrum;
import scitypes.Spectrum;
import scitypes.SpectrumCalculations;

/**
 * 
 * Painters define a method of drawing a given set of data to a plot.
 * 
 * @author Nathaniel Sherry, 2009
 * @see PainterData
 * @see PlotDrawing
 *
 */

public abstract class PlotPainter extends Painter{
	
	public static enum TraceType {CONNECTED, BAR, LINE}
	
	/**
	 * Causes this Painter to draw its data to the given Surface
	 * @param p the {@link PainterData} structure containing objects and information needed to draw to the plot
	 */
	@Override
	public abstract void drawElement(PainterData p);

	/**
	 * Traces a given list of doubles as points on a plot.
	 * @param p the {@link PainterData} structure containing objects and information needed to draw to the plot
	 * @param data the data series to trace
	 */
	protected void traceData(PainterData p, ReadOnlySpectrum data)
	{
		traceData(p, data, TraceType.CONNECTED);
	}
	
	/**
	 * Traces a given list of doubles as points on a plot.
	 * @param p the {@link PainterData} structure containing objects and information needed to draw to the plot
	 * @param data the data series to trace
	 * @param connected should the data points be drawn as part of a connected series, or should each point be shown as an individual line
	 */
	protected void traceData(PainterData p, ReadOnlySpectrum data, TraceType traceType)
	{
		traceData(p.context, p.dr, p.plotSize, p.dataHeights, traceType, data);
	}
	
	/**
	 * Traces a given list of doubles as points on a plot.
	 * @param context the Surface to draw to
	 * @param dr the DrawingRequest defining how this plot should look
	 * @param plotSize the dimensions of the actual plot, after decorations such as axes
	 * @param dataHeights the maximum height drawn to previously for a given column of data
	 * @param connected should the data points be drawn as part of a connected series, or should each point be shown as an individual line
	 * @param data the data series to trace
	 */
	protected void traceData(Surface context, DrawingRequest dr, Coord<Float> plotSize, Spectrum dataHeights, TraceType traceType, ReadOnlySpectrum data)
	{


		if (context == null) return;

		// copy the data
		Spectrum transformedData = transformDataForPlot(dr, data);

		// get the image bounds
		float plotWidth = plotSize.x;
		float plotHeight = plotSize.y;
		float pointWidth = plotWidth / dr.dataWidth;

		// draw the path
		context.moveTo(0.0f, plotHeight);
		float height = plotHeight;
		float pointStart, pointEnd, pointMiddle;
		for (int i = 0; i < transformedData.size(); i++) {
			
			pointStart = pointWidth*i;
			pointEnd = pointStart + pointWidth;
			pointMiddle = (pointStart + pointEnd) / 2.0f;
			height = (transformedData.get(i)) * plotHeight;
			if (dataHeights != null && dataHeights.get(i) < height) 
			{
				dataHeights.set(i, height);
			}
			height = plotHeight - height;
			
			
			if (traceType == TraceType.BAR) {
				
				
				context.moveTo(pointStart, plotHeight);
				if (plotHeight != height){
					context.lineTo(pointStart, height);
					context.lineTo(pointEnd, height);
					context.lineTo(pointEnd, plotHeight);
					context.lineTo(pointStart, plotHeight);
				}
				
			} else if (traceType == TraceType.LINE){
				
				
				context.moveTo(pointMiddle, plotHeight);
				if (plotHeight != height){
					context.lineTo(pointMiddle, height);
				}
				
			} else if (traceType == TraceType.CONNECTED){
				
				context.lineTo(pointMiddle, height);
				
			}

			
		}

		// line to end of plot
		if (traceType == TraceType.CONNECTED){
			context.lineTo(pointWidth * transformedData.size(), height);
			context.lineTo(pointWidth * transformedData.size(), plotHeight+100);	
			context.lineTo(0, plotHeight+100);
		}
	}
	
	

	
	protected Coord<Bounds<Float>> getTextLabelDimensions(PainterData p, String title, float energy)
	{
		DrawingRequest dr = p.dr;

		float textWidth = p.context.getTextWidth(title);

		float channelSize = p.plotSize.x / dr.dataWidth;
		float centreChannel = (energy / dr.unitSize);

		float titleStart = centreChannel * channelSize;
		titleStart -= (textWidth / 2.0);


		float titleHeight = p.context.getFontHeight();
		float penWidth = getPenWidth(getBaseUnitSize(dr), dr);
		float totalHeight = (titleHeight + penWidth * 2);
		
		float farLeft = titleStart - penWidth * 2;
		float width = textWidth + penWidth * 4;
		float farRight = farLeft + width;

		float leftChannel = (float)Math.max(0, Math.floor(farLeft / channelSize));
		float rightChannel = (float)Math.min(p.dr.dataWidth-1, Math.ceil(farRight / channelSize));
		
		
		
		return new Coord<Bounds<Float>>(new Bounds<Float>(leftChannel, rightChannel), new Bounds<Float>(penWidth, totalHeight));
		
	}
	
	/**
	 * Draws a text label on the plot
	 * @param p the {@link PainterData} structure containing objects and information needed to draw to the plot.
	 * @param title the title of the label
	 * @param energy the energy value at which to centre the label
	 */
	protected void drawTextLabel(PainterData p, String title, float energy, float xStart, float yStart)
	{
		drawTextLabel(p, title, energy, xStart, yStart, true);
	}
	protected void drawTextLabel(PainterData p, String title, float energy, float xStart, float yStart, boolean resetColour)
	{
		if (xStart > p.plotSize.x) return;

		if (title != null) {

			if (resetColour) p.context.setSource(0.0f, 0.0f, 0.0f);
			p.context.writeText(title, xStart, p.plotSize.y - yStart);
		}

	}
	
	
	protected Spectrum transformDataForPlot(DrawingRequest dr, ReadOnlySpectrum data)
	{

		Spectrum transformedData = new ISpectrum(data);
		if (dr.viewTransform == ViewTransform.LOG) transformedData = SpectrumCalculations.logList(transformedData);
		float dataMax = PlotDrawing.getDataScale(dr, data);
		transformedData = SpectrumCalculations.divideBy(transformedData, dataMax);

		return transformedData;
	}
	
	protected float transformValueForPlot(DrawingRequest dr, float value)
	{
		if (dr.viewTransform == ViewTransform.LOG) value = (float)Math.log1p(value);
		float dataMax = PlotDrawing.getDataScale(dr);
		value = value / dataMax;

		return value;
	}
	
	
	public float getChannelAtEnergy(DrawingRequest dr, float energy)
	{
		return energy / dr.unitSize;
	}

	public float getXForChannel(PainterData p, float channel)
	{
		float widthPerChannel = p.plotSize.x / p.dr.dataWidth;
		return channel * widthPerChannel;
	}
	
	@Override
	protected float getBaseUnitSize(DrawingRequest dr)
	{
		return dr.imageHeight / 350.0f;
	}
	
	protected float getPenWidth(float baseSize, DrawingRequest dr)
	{
		float width;
		width = baseSize;
		return width;
	}
	
}