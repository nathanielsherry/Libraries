package scidraw.drawing.map.painters;

import java.awt.Color;

import scidraw.drawing.map.palettes.SingleColourPalette;
import scidraw.drawing.painters.PainterData;
import scitypes.Coord;

/**
 * Draws a box around a region in the map
 * @author NAS
 *
 */
public class BoundedRegionPainter extends MapPainter
{

	private Coord<Integer> cstart, cend;
	private Color c;
	
	public BoundedRegionPainter(Color c, Coord<Integer> cstart, Coord<Integer> cend)
	{
		super(new SingleColourPalette(c));
		
		this.cstart = cstart;
		this.cend = cend;
		this.c = c;
	}

	@Override
	public void drawMap(PainterData p, float cellSize, float rawCellSize)
	{
				
		int startX, startY, endX, endY;
		
		startX 	= cstart.x;
		endX 	= cend.x;
		startY 	= cstart.y;
		endY 	= cend.y;
		
		if (startX > endX) 
			startX+=1;
		else
			endX +=1;
		

		if (startY > endY)
			startY +=1;
		else
			endY +=1;


		
		p.context.setLineWidth(Math.max(1, rawCellSize/4));
		
		p.context.rectangle(startX * rawCellSize, startY * rawCellSize, (endX - startX) * rawCellSize, (endY - startY) * rawCellSize);
		
		p.context.setSource(c.getRed(), c.getGreen(), c.getBlue(), 96);
		p.context.fillPreserve();
		
		p.context.setSource(c.getRed(), c.getGreen(), c.getBlue());
		p.context.stroke();
		
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
