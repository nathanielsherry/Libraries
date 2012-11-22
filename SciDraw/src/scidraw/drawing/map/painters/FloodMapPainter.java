package scidraw.drawing.map.painters;


import java.awt.Color;

import scidraw.drawing.map.palettes.SingleColourPalette;
import scidraw.drawing.painters.PainterData;


/**
 * 
 * This class implements the drawing of a map using block pixel filling
 * 
 * @author Nathaniel Sherry, 2009
 */

public class FloodMapPainter extends MapPainter
{

	private Color c;
	
	public FloodMapPainter(Color c)
	{
		super(new SingleColourPalette(c));
		
		this.c = c;
		
	}


	@Override
	public void drawMap(PainterData p, float cellSize, float rawCellSize)
	{

		p.context.save();
	
			p.context.setSource(c);
			
			p.context.rectangle(0, 0, p.dr.dataWidth * cellSize, p.dr.dataHeight * cellSize);
			p.context.fill();

		p.context.restore();

	}

	
	@Override
	public void clearBuffer()
	{
	}


	@Override
	public boolean isBufferingPainter()
	{
		return false;
	}
	
}
