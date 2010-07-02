package scidraw.drawing.map.painters;


import java.util.List;

import plural.workers.PluralEachIndex;
import plural.workers.executor.eachindex.implementations.PluralEachIndexExecutor;

import scidraw.drawing.backends.Buffer;
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

public class ThreadedRasterMapPainter extends MapPainter
{

	private Buffer buffer;
	public boolean useBuffer = false;

	public ThreadedRasterMapPainter(List<AbstractPalette> colourRules, Spectrum data)
	{
		super(colourRules, data);
	}


	public ThreadedRasterMapPainter(AbstractPalette colourRule, Spectrum data)
	{
		super(colourRule, data);
	}


	@Override
	public void drawElement(PainterData p)
	{

		p.context.save();

		Spectrum modData = data;
		float maxIntensity;
		if (p.dr.maxYIntensity <= 0) {
			maxIntensity = SpectrumCalculations.max(data);
		} else {
			maxIntensity = p.dr.maxYIntensity;
		}


		// get the size of the cells
		float cellSize = MapDrawing.calcCellSize(p.plotSize.x, p.plotSize.y, p.dr);

		// clip the region
		p.context.rectangle(0, 0, p.dr.dataWidth * cellSize, p.dr.dataHeight * cellSize);
		p.context.clip();

		GridPerspective<Float> grid = new GridPerspective<Float>(p.dr.dataWidth, p.dr.dataHeight, 0.0f);
		modData = SpectrumCalculations.gridYReverse(modData, grid);

		if (p.dr.drawToVectorSurface) {
			drawAsScalar(p, modData, cellSize, maxIntensity);
			buffer = null;
		} else {
			if (useBuffer && buffer != null){
				drawBuffer(p, buffer, cellSize);
			} else {
				buffer = drawAsRaster(p, modData, cellSize, maxIntensity, p.dr.dataHeight * p.dr.dataWidth);
			}
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

		p.context.compose(b, 0, 0, cellSize);
		
		return b;
	}

	private void drawBuffer(PainterData p, Buffer b, float cellSize)
	{
		p.context.compose(b, 0, 0, cellSize);
	}

	private void drawAsScalar(PainterData p, Spectrum data, float cellSize, final float maxIntensity)
	{
		float intensity;

		// draw the map
		for (int y = 0; y < p.dr.dataHeight; y++) {
			for (int x = 0; x < p.dr.dataWidth; x++) {

				p.context.save();

				p.context.rectangle(0, 0, p.plotSize.x, p.plotSize.y);
				p.context.clip();

				int index = y * p.dr.dataWidth + x;
				intensity = data.get(index);
				p.context.rectangle(x * cellSize, y * cellSize, cellSize + 1, cellSize + 1);
				p.context.setSource(getColourFromRules(intensity, maxIntensity));
				p.context.fill();

				p.context.restore();
			}
		}
	}

	
	public void clearBuffer()
	{
		buffer = null;
	}
	
}
