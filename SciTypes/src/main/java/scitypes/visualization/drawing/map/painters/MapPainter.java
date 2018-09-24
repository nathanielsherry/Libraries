package scitypes.visualization.drawing.map.painters;


import java.util.ArrayList;
import java.util.List;

import scitypes.ISpectrum;
import scitypes.ReadOnlySpectrum;
import scitypes.Spectrum;
import scitypes.SpectrumCalculations;
import scitypes.visualization.drawing.DrawingRequest;
import scitypes.visualization.drawing.ViewTransform;
import scitypes.visualization.drawing.map.MapDrawing;
import scitypes.visualization.drawing.painters.Painter;
import scitypes.visualization.drawing.painters.PainterData;
import scitypes.visualization.palette.PaletteColour;
import scitypes.visualization.palette.palettes.AbstractPalette;
import scitypes.visualization.template.Rectangle;

/**
 * 
 * A MapPainter is a specific way of drawing the {@link MapDrawing}
 * 
 * @author Nathaniel Sherry, 2009
 * @see MapDrawing
 *
 */

public abstract class MapPainter extends Painter
{

	
	protected List<AbstractPalette>	colourRules;


	public MapPainter(List<AbstractPalette> colourRules)
	{
		this.colourRules = colourRules;
	}
	
	public MapPainter(AbstractPalette colourRule)
	{
		List<AbstractPalette> rules = new ArrayList<AbstractPalette>();
		rules.add(colourRule);
		this.colourRules = rules;
	}


	@Override
	protected float getBaseUnitSize(scitypes.visualization.drawing.DrawingRequest dr)
	{
		// TODO Auto-generated method stub
		return 1;
	}
	
	public PaletteColour getColourFromRules(double intensity, double maximum, ViewTransform transform)
	{

		PaletteColour c;
		
		if (transform == ViewTransform.LOG) {
			//intensity will already have been log'd, we just have to log the max
			maximum = Math.log1p(maximum);
		}
		
		for (AbstractPalette r : colourRules) {
			c = r.getFillColour(intensity, maximum);
			if (c != null) return c;
		}

		return new PaletteColour(0x00000000);

	}
	
	public void setPalette(AbstractPalette palette)
	{
		colourRules.clear();
		colourRules.add(palette);
	}
	public void setPalettes(List<AbstractPalette> palettes)
	{
		colourRules.clear();
		colourRules.addAll(palettes);
	}


	
	
	@Override
	public final void drawElement(PainterData p)
	{
		
		p.context.save();
			
			// get the size of the cells
			float cellSize = MapDrawing.calcInterpolatedCellSize(p.plotSize.x, p.plotSize.y, p.dr);
			float rawCellSize = MapDrawing.calcUninterpolatedCellSize(p.plotSize.x, p.plotSize.y, p.dr);
	
			// clip the region
			p.context.addShape(new Rectangle(0, 0, p.dr.dataWidth * cellSize, p.dr.dataHeight * cellSize));
			p.context.clip();
			
			drawMap(p, cellSize, rawCellSize);
			
		p.context.restore();
		
	}
	
	public abstract void drawMap(PainterData p, float cellSize, float rawCellSize);
	
	protected Spectrum transformDataForMap(DrawingRequest dr, ReadOnlySpectrum data)
	{
		Spectrum transformedData = new ISpectrum(data);
		if (dr.viewTransform == ViewTransform.LOG) transformedData = SpectrumCalculations.logList(transformedData);
		return transformedData;
	}
	
	public abstract boolean isBufferingPainter();
	public abstract void clearBuffer();
	
}
