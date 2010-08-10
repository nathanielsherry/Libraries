package scidraw.drawing.map.painters;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.List;
import java.util.Stack;


import fava.datatypes.Range;

import scidraw.datatypes.DataTypeFactory;
import scidraw.drawing.backends.Surface.LineJoin;
import scidraw.drawing.map.palettes.AbstractPalette;
import scidraw.drawing.painters.PainterData;
import scitypes.Coord;
import scitypes.GridPerspective;
import scitypes.Spectrum;
import scitypes.SpectrumCalculations;


enum Threshold
{
	ABOVE, BELOW, OUTSIDE
}

public class ContourMapPainter extends MapPainter
{

	private int contourSteps;
	
	List<Shape> cache = DataTypeFactory.<Shape>list();
	
	
	public ContourMapPainter(AbstractPalette colourRule, Spectrum data, int contourSteps)
	{
		super(colourRule, data);
		this.contourSteps = contourSteps;
		
	}
	
	public ContourMapPainter(List<AbstractPalette> colourRules, Spectrum data, int contourSteps)
	{
		super(colourRules, data);
		this.contourSteps = contourSteps;
	}

	
	
	@Override
	public void drawMap(PainterData p, float cellSize, float rawCellSize)
	{
		
		GridPerspective<Float> grid = new GridPerspective<Float>(p.dr.dataWidth, p.dr.dataHeight, 0f);
		Spectrum flipData = SpectrumCalculations.gridYReverse(data, grid);

		
		traceLayers(p, grid, flipData, cellSize, false);
		
		
	}

	@Override
	public boolean isBufferingPainter()
	{
		return true;
	}


	private List<List<Threshold>> getThresholdCellsSet(PainterData p, final Spectrum list, final GridPerspective<Threshold> thresholdGrid)
	{
		final List<List<Threshold>> thresholds = DataTypeFactory.<Threshold> datasetInit(contourSteps);

		double listMax = p.dr.maxYIntensity; //ListCalculations.max(list);
		if (listMax == 0) listMax = 1; // make sure that a map full of 0 doesn't get painted as high
		final double increment = listMax / contourSteps;
		
		
		for (int i : new Range(0, contourSteps-1))
		{
			thresholds.set(i, getThresholdMap(list, increment * i, thresholdGrid));
		}
				
		return thresholds;
	}
	
	// generates a threshold map for a given intensity. result is boolean
	// list/map with true for all value
	// above intensity, false otherwise
	private static List<Threshold> getThresholdMap(Spectrum list, double threshold, final GridPerspective<Threshold> grid)
	{

		List<Threshold> result = DataTypeFactory.<Threshold> list();

		for (int i = 0; i < list.size(); i++) {
			result.add(list.get(i) >= threshold ? Threshold.ABOVE : Threshold.BELOW);
		}
		
		return result;
	}

	

	private void traceLayers(final PainterData p, GridPerspective<Float> grid, Spectrum list, float cellSize, boolean outline)
	{

		
		
		p.context.scale(1/cellSize, 1/cellSize);
		if (outline) p.context.setLineWidth(0.5f / cellSize);
		if (outline) p.context.setLineJoin(LineJoin.ROUND);
		
		if (cache.size() == 0){
			
			// generate a list of threshold maps, once for each step in the spectrum.
			final GridPerspective<Threshold> thresholdGrid = new GridPerspective<Threshold>(grid.width, grid.height,
					Threshold.OUTSIDE);
			final List<List<Threshold>> thresholds = getThresholdCellsSet(p, list, thresholdGrid);
										
			for (int i = 0; i < thresholds.size(); i++) {
				cache.add(traceLayer(thresholdGrid, thresholds.get(i)));
			}
		}
		
		for (int i = 0; i < cache.size(); i++)
		{
					
			Color colour = getColourFromRules(i, cache.size());
			
			p.context.addShape(cache.get(i));
			
			p.context.setSource(colour);
			if (outline) {
				p.context.fillPreserve();
				p.context.setSource(Color.black);
				p.context.stroke();
				
			} else {
				p.context.fill();
			}
			
		}
		
	}
	



	@Override
	public void clearBuffer()
	{
		cache.clear();
	}
	
	

	private Shape traceLayer(GridPerspective<Threshold> grid, List<Threshold> cells)
	{
		Area layerArea = new Area();
		Area linesArea = new Area();
		Area lineArea = new Area();
		

		int reach = 0, count = 0;
		
		
		for (int y = 0; y < grid.height; y++)
		{
		
			lineArea.reset();
			
			for (int x = 0; x < grid.width; x++)
			{
				
				
				if (grid.get(cells, x, y) == Threshold.ABOVE)
				{				
				
					reach = 1;
					while (true)
					{
						
						if (grid.get(cells, x+reach, y) == Threshold.ABOVE){
							reach++;
						} else {
							break;
						}
						
					}
					
					
					
					lineArea.add(new Area(new Rectangle(x, y, reach, 1)));
					x+=(reach-1);
	
					
				} //if ABOVE
				

				
			} //for X
			
			linesArea.add(lineArea);
			count++;
			if (count > 50)
			{
				layerArea.add(linesArea);
				linesArea.reset();
				count = 0;
			}
			
		} //for Y
		
		layerArea.add(linesArea);	
		return layerArea;
	}
	

}



