package scidraw.drawing.map.painters;


import java.awt.Color;
import java.util.List;

import fava.signatures.FnEach;

import plural.executor.eachindex.implementations.PluralEachIndexExecutor;


import scidraw.drawing.backends.Buffer;
import scidraw.drawing.map.palettes.SingleColourPalette;
import scidraw.drawing.painters.PainterData;

/**
 * 
 * This class implements the drawing of a map using block pixel filling
 * 
 * @author Nathaniel Sherry, 2009
 */

public class RasterColorMapPainter extends MapPainter
{
	
	private List<Color> 			pixels;
	protected Buffer 				buffer;


	public RasterColorMapPainter()
	{
		super(new SingleColourPalette(new Color(0, 0, 0, 0)));
	}


	public void setPixels(List<Color> pixels)
	{
		this.pixels = pixels;
	}
	
	@Override
	public void drawMap(PainterData p, float cellSize, float rawCellSize)
	{

		p.context.save();

	
			if (p.dr.drawToVectorSurface) {
				drawAsScalar(p, pixels, cellSize);
				buffer = null;
			} else {
				
				if (buffer == null) {
					buffer = drawAsRaster(p, pixels, cellSize, p.dr.dataHeight * p.dr.dataWidth);
				}
				p.context.compose(buffer, 0, 0, cellSize);
				
			}

		p.context.restore();

	}


	private Buffer drawAsRaster(PainterData p, final List<Color> data, float cellSize, final int maximumIndex)
	{

		final Buffer b = p.context.getImageBuffer(p.dr.dataWidth, p.dr.dataHeight);

		final Color transparent = new Color(0, 0 ,0, 0);
		final FnEach<Integer> drawPixel = new FnEach<Integer>() {

			public void f(Integer ordinal)
			{			
				if (maximumIndex > ordinal) {
					Color c = data.get(ordinal);
					if (c == null) c = transparent;
					b.setPixelValue(ordinal, c);
				}
			}
		};


		new PluralEachIndexExecutor(data.size(), drawPixel).executeBlocking();
		

		p.context.compose(b, 0, 0, cellSize);
		
		return b;
	}


	private void drawAsScalar(PainterData p, List<Color> data, float cellSize)
	{

		p.context.save();

		p.context.rectangle(0, 0, p.plotSize.x, p.plotSize.y);
		p.context.clip();
		
		// draw the map
		for (int y = 0; y < p.dr.dataHeight; y++) {
			for (int x = 0; x < p.dr.dataWidth; x++) {



				int index = y * p.dr.dataWidth + x;
				p.context.rectangle(x * cellSize, y * cellSize, cellSize + 1, cellSize + 1);
				p.context.setSource(data.get(index));
				p.context.fill();

				
			}
		}
		
		p.context.restore();
	}

	
	public void clearBuffer()
	{
		buffer = null;
	}


	@Override
	public boolean isBufferingPainter()
	{
		return true;
	}
	
}
