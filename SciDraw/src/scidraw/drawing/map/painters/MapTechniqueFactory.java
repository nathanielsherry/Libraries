package scidraw.drawing.map.painters;

import java.util.ArrayList;
import java.util.List;

import scidraw.drawing.map.palettes.AbstractPalette;
import scidraw.drawing.map.palettes.ThermalScalePalette;
import scitypes.Spectrum;


public class MapTechniqueFactory
{

	public static MapPainter getTechnique(List<AbstractPalette> colourRules, Spectrum data, boolean contour, int contourSteps)
	{
		if (contour) return new ContourMapPainter(colourRules, data, contourSteps); //ContourMapPainter(colourRules, data, contourSteps);
		return new RasterSpectrumMapPainter(colourRules, data);
	}
	
	public static MapPainter getTechnique(AbstractPalette colourRule, Spectrum data, boolean contour, int contourSteps)
	{
		List<AbstractPalette> colourRules = new ArrayList<AbstractPalette>();
		colourRules.add(colourRule);
		
		return getTechnique(colourRules, data, contour, contourSteps);
	}
	
	public static MapPainter getDefaultTechnique(Spectrum data)
	{
		AbstractPalette palette = new ThermalScalePalette();
		List<AbstractPalette> paletteList = new ArrayList<AbstractPalette>();
		paletteList.add(palette);
		return new RasterSpectrumMapPainter(paletteList, data);
	}
	
}