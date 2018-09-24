package scidraw.drawing.map.painters;


import java.util.List;
import java.util.stream.IntStream;

import scidraw.drawing.painters.PainterData;
import scitypes.Spectrum;
import scitypes.visualization.Buffer;
import scitypes.visualization.drawings.Rectangle;
import scitypes.visualization.palette.PaletteColour;
import scitypes.visualization.palette.palettes.AbstractPalette;

/**
 * 
 * This class implements the drawing of a map using block pixel filling
 * 
 * @author Nathaniel Sherry, 2009
 */

public class RasterSpectrumMapPainter extends SpectrumMapPainter
{

	protected Buffer buffer;

	
	public RasterSpectrumMapPainter(List<AbstractPalette> colourRules, Spectrum data)
	{
		super(colourRules, data);	
	}


	public RasterSpectrumMapPainter(AbstractPalette colourRule, Spectrum data)
	{
		super(colourRule, data);
	}
	

	@Override
	public void drawMap(PainterData p, float cellSize, float rawCellSize)
	{
		
		p.context.save();
	
			Spectrum modData = transformDataForMap(p.dr, data);
			
			float maxIntensity;
			if (p.dr.maxYIntensity <= 0) {
				maxIntensity = data.max();
			} else {
				maxIntensity = p.dr.maxYIntensity;
			}
	
			//GridPerspective<Float> grid = new GridPerspective<Float>(p.dr.dataWidth, p.dr.dataHeight, 0.0f);
			//modData = SpectrumCalculations.gridYReverse(modData, grid);
	
			if (p.dr.drawToVectorSurface) {
				drawAsScalar(p, modData, cellSize, maxIntensity);
			} else {
				if (buffer == null) {
					buffer = drawAsRaster(p, modData, maxIntensity, p.dr.dataHeight * p.dr.dataWidth);
				}
				p.context.compose(buffer, 0, 0, cellSize);
			}

		p.context.restore();

	}


	private Buffer drawAsRaster(PainterData p, final Spectrum data, final float maxIntensity,
			final int maximumIndex)
	{

		final Buffer b = p.context.getImageBuffer(p.dr.dataWidth, p.dr.dataHeight);

		IntStream.range(0, data.size()).parallel().forEach(ordinal -> {				
			float intensity = data.get(ordinal);
			
			if (maximumIndex > ordinal) {
				b.setPixelValue(ordinal, getColourFromRules(intensity, maxIntensity, p.dr.viewTransform));
			}
		});
		
		
		return b;
	}


	private void drawAsScalar(PainterData p, Spectrum data, float cellSize, final float maxIntensity)
	{
		float intensity;
		PaletteColour c;
		int index;

		p.context.save();
		
		
		// draw the map
		for (int y = 0; y < p.dr.dataHeight; y++) {
			for (int x = 0; x < p.dr.dataWidth; x++) {

				index = y * p.dr.dataWidth + x;
				intensity = data.get(index);

				c = getColourFromRules(intensity, maxIntensity, p.dr.viewTransform);

				p.context.addShape(new Rectangle(x * cellSize, y * cellSize, cellSize + 1, cellSize + 1));

				p.context.setSource(c);
				p.context.fill();
				
			}
		}
		
	
		p.context.restore();
	}


	@Override
	public boolean isBufferingPainter()
	{
		return true;
	}


	@Override
	public void clearBuffer()
	{
		buffer = null;
	}

	
}
