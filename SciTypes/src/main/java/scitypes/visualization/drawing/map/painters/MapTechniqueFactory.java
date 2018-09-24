package scitypes.visualization.drawing.map.painters;

import java.util.ArrayList;
import java.util.List;

import scitypes.Spectrum;
import scitypes.visualization.palette.palettes.AbstractPalette;
import scitypes.visualization.palette.palettes.ThermalScalePalette;


public class MapTechniqueFactory
{

	public static SpectrumMapPainter getTechnique(List<AbstractPalette> colourRules, Spectrum data, int contourSteps)
	{
		return new RasterSpectrumMapPainter(colourRules, data);
	}
	
	public static SpectrumMapPainter getTechnique(AbstractPalette colourRule, Spectrum data, int contourSteps)
	{
		List<AbstractPalette> colourRules = new ArrayList<AbstractPalette>();
		colourRules.add(colourRule);
		
		return getTechnique(colourRules, data, contourSteps);
	}
	
	public static SpectrumMapPainter getDefaultTechnique(Spectrum data)
	{
		AbstractPalette palette = new ThermalScalePalette();
		List<AbstractPalette> paletteList = new ArrayList<AbstractPalette>();
		paletteList.add(palette);
		return new RasterSpectrumMapPainter(paletteList, data);
	}
	
}