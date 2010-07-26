package scidraw.drawing.map.painters;

import java.util.List;

import scidraw.datatypes.DataTypeFactory;
import scidraw.drawing.map.palettes.AbstractPalette;
import scidraw.drawing.map.palettes.ThermalScalePalette;
import scitypes.Spectrum;


public class MapTechniqueFactory
{

	public static MapPainter getTechnique(List<AbstractPalette> colourRules, Spectrum data, boolean contour, int contourSteps)
	{
		//if (contour) return new ContourMapPainter(colourRules, data, contourSteps);
		return new ThreadedRasterMapPainter(colourRules, data);
	}
	
	public static MapPainter getTechnique(AbstractPalette colourRule, Spectrum data, boolean contour, int contourSteps)
	{
		List<AbstractPalette> colourRules = DataTypeFactory.<AbstractPalette>list();
		colourRules.add(colourRule);
		
		return getTechnique(colourRules, data, contour, contourSteps);
	}
	
	public static MapPainter getDefaultTechnique(Spectrum data)
	{
		AbstractPalette palette = new ThermalScalePalette();
		List<AbstractPalette> paletteList = DataTypeFactory.<AbstractPalette>list();
		paletteList.add(palette);
		return new ThreadedRasterMapPainter(paletteList, data);
	}
	
}
