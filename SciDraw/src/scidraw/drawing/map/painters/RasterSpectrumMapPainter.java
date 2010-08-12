package scidraw.drawing.map.painters;


import java.awt.Color;
import java.util.List;

import plural.datatypes.DataTypeFactory;
import plural.workers.PluralEachIndex;
import plural.workers.executor.eachindex.implementations.PluralEachIndexExecutor;

import scidraw.drawing.backends.Buffer;
import scidraw.drawing.backends.Surface;
import scidraw.drawing.map.MapDrawing;
import scidraw.drawing.map.palettes.AbstractPalette;
import scidraw.drawing.painters.PainterData;
import scitypes.GridPerspective;
import scitypes.Spectrum;
import scitypes.SpectrumCalculations;

/**
 * 
 * This class implements the drawing of a map using block pixel filling
 * 
 * @author Nathaniel Sherry, 2009
 */

public class RasterSpectrumMapPainter extends MapPainter
{

	
	protected Buffer 				buffer;

	
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
	
			Spectrum modData = data;
			
			float maxIntensity;
			if (p.dr.maxYIntensity <= 0) {
				maxIntensity = SpectrumCalculations.max(data);
			} else {
				maxIntensity = p.dr.maxYIntensity;
			}
	
			GridPerspective<Float> grid = new GridPerspective<Float>(p.dr.dataWidth, p.dr.dataHeight, 0.0f);
			modData = SpectrumCalculations.gridYReverse(modData, grid);
	
			if (p.dr.drawToVectorSurface) {
				drawAsScalar(p, modData, cellSize, maxIntensity);
			} else {
				if (buffer == null) {
					buffer = drawAsRaster(p, modData, cellSize, maxIntensity, p.dr.dataHeight * p.dr.dataWidth);
				}
				p.context.compose(buffer, 0, 0, cellSize);
			}

		p.context.restore();

	}


	private Buffer drawAsRaster(PainterData p, final Spectrum data, float cellSize, final float maxIntensity,
			final int maximumIndex)
	{

		final Buffer b = p.context.getImageBuffer(p.dr.dataWidth, p.dr.dataHeight);

		final PluralEachIndex drawPixel = new PluralEachIndex() {

			public void f(Integer ordinal)
			{
				if (maximumIndex > ordinal) {
					b.setPixelValue(ordinal, getColourFromRules(data.get(ordinal), maxIntensity));
				}
			}
		};


		new PluralEachIndexExecutor(data.size(), drawPixel).executeBlocking();
		
		return b;
	}


	private void drawAsScalar(PainterData p, Spectrum data, float cellSize, final float maxIntensity)
	{
		float intensity;
		Color c, cp;
		int index;
		
		//int span = 1;
		
		p.context.save();
		
		List<Color> handledColours = DataTypeFactory.<Color>list();
		
		// draw the map
		for (int y = 0; y < p.dr.dataHeight; y++) {
			for (int x = 0; x < p.dr.dataWidth; x++) {
				
				
				
				
				
				index = y * p.dr.dataWidth + x;
				intensity = data.get(index);
				
				c = getColourFromRules(intensity, maxIntensity);
				
				
				p.context.rectangle(x * cellSize, y * cellSize, cellSize + 1, cellSize + 1);

					
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
