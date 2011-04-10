package scidraw.drawing.map.painters;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import scidraw.drawing.map.MapDrawing;
import scidraw.drawing.map.palettes.AbstractPalette;
import scidraw.drawing.painters.Painter;
import scidraw.drawing.painters.PainterData;
import scitypes.Spectrum;

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

	protected Spectrum				data;
	protected List<AbstractPalette>	colourRules;


	public MapPainter(List<AbstractPalette> colourRules, Spectrum data)
	{
		this.colourRules = colourRules;
		this.data = data;

	}
	
	public MapPainter(AbstractPalette colourRule, Spectrum data)
	{
		List<AbstractPalette> rules = new ArrayList<AbstractPalette>();
		rules.add(colourRule);
		this.colourRules = rules;
		this.data = data;
	}


	@Override
	protected float getBaseUnitSize(scidraw.drawing.DrawingRequest dr)
	{
		// TODO Auto-generated method stub
		return 1;
	}
	
	public Color getColourFromRules(double intensity, double maximum)
	{

		Color c;
		
		for (AbstractPalette r : colourRules) {
			c = r.getFillColour(intensity, maximum);
			if (c != null) return c;
		}

		return new Color(0.0f, 0.0f, 0.0f, 0.0f);

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

	public void setData(Spectrum data)
	{
		this.data = data;
	}
	
	
	@Override
	public final void drawElement(PainterData p)
	{
		
		p.context.save();
			
			// get the size of the cells
			float cellSize = MapDrawing.calcInterpolatedCellSize(p.plotSize.x, p.plotSize.y, p.dr);
			float rawCellSize = MapDrawing.calcUninterpolatedCellSize(p.plotSize.x, p.plotSize.y, p.dr);
	
			// clip the region
			p.context.rectangle(0, 0, p.dr.dataWidth * cellSize, p.dr.dataHeight * cellSize);
			p.context.clip();
			
			drawMap(p, cellSize, rawCellSize);
			
		p.context.restore();
		
	}
	
	public abstract void drawMap(PainterData p, float cellSize, float rawCellSize);
	
	public abstract boolean isBufferingPainter();
	public abstract void clearBuffer();
	
}
