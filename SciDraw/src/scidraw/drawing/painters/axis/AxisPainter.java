package scidraw.drawing.painters.axis;


import java.util.List;

import fava.*;
import fava.datatypes.Bounds;
import fava.datatypes.Pair;


import scidraw.drawing.DrawingRequest;
import scidraw.drawing.backends.Surface;
import scidraw.drawing.painters.Painter;
import scidraw.drawing.painters.PainterData;
import scitypes.Coord;

/**
 * An AxisPainter is a special kind of painter used to paint Axes on a drawing. Unlike other {@link Painter}s,
 * AxisPainters reserve space for themselves, such that any further drawing should be done inside of the space
 * not used by the AxisPainter
 * 
 * @author Nathaniel Sherry, 2009
 * 
 */

public abstract class AxisPainter extends Painter
{


	public static final int	FONTSIZE_TICK	= 10;
	public static final int	FONTSIZE_TITLE	= 22;

	protected AxesData		axesData;


	public AxisPainter()
	{
		axesData = new AxesData(new Bounds<Float>(0.0f, 0.0f), new Bounds<Float>(0.0f, 0.0f));
	}


	public void setDimensions(Bounds<Float> xPositionBounds, Bounds<Float> yPositionBounds)
	{
		this.axesData.xPositionBounds = xPositionBounds;
		this.axesData.yPositionBounds = yPositionBounds;
	}


	public abstract Pair<Float, Float> getAxisSizeY(PainterData p);


	public abstract Pair<Float, Float> getAxisSizeX(PainterData p);


	protected static float getTickSize(float baseSize, DrawingRequest dr)
	{
		return baseSize * 5;
	}


	protected static float getTickFontHeight(Surface context, DrawingRequest dr)
	{
		return context.getFontHeight();
	}


	protected static float getTitleFontHeight(Surface context, DrawingRequest dr)
	{
		return getTitleFontHeight(context, dr, 1.0f);
	}


	protected static float getTitleFontHeight(Surface context, DrawingRequest dr, float titleScale)
	{
		float height;
		context.save();
		context.setFontSize(FONTSIZE_TITLE * titleScale);
		height = context.getFontHeight() + context.getFontLeading();
		context.restore();
		return height;
	}


	@Override
	protected float getBaseUnitSize(scidraw.drawing.DrawingRequest dr)
	{
		return (float)Math.min(dr.imageHeight, dr.imageWidth) / 350.0f;
	}


	protected float getPenWidth(float baseSize, scidraw.drawing.DrawingRequest dr)
	{
		float width;
		width = baseSize;
		return width;
	}
	

	public static Coord<Bounds<Float>> calcAxisBorders(PainterData p, List<AxisPainter> axisPainters)
	{
		Bounds<Float> availableX, availableY;
		availableX = new Bounds<Float>(0.0f, p.dr.imageWidth);
		availableY = new Bounds<Float>(0.0f, p.dr.imageHeight);

		if (axisPainters != null) {

			Pair<Float, Float> axisSizeX, axisSizeY;

			for (AxisPainter axisPainter : axisPainters) {


				axisPainter.setDimensions(

				new Bounds<Float>(availableX.start, availableX.end),
						new Bounds<Float>(availableY.start, availableY.end)

				);

				axisSizeX = axisPainter.getAxisSizeX(p);
				axisSizeY = axisPainter.getAxisSizeY(p);

				availableX.start += axisSizeX.first;
				availableX.end -= axisSizeX.second;
				availableY.start += axisSizeY.first;
				availableY.end -= axisSizeY.second;

			}

		}

		return new Coord<Bounds<Float>>(availableX, availableY);
	}

}
