package scidraw.drawing.map.painters;


import java.awt.Color;
import java.util.List;

import scidraw.datatypes.DataTypeFactory;
import scidraw.drawing.map.MapDrawing;
import scidraw.drawing.map.palettes.AbstractPalette;
import scidraw.drawing.painters.Painter;
import scitypes.Spectrum;

/**
 * 
 * A MapPainter is a specific way of drawing the {@link MapDrawing}
 * 
 * @author Nathaniel Sherry, 2009
 * @see MapDrawing
 *
 */

public abstract class MapPainter extends Painter
{

	protected Spectrum			data;
	protected List<AbstractPalette>	colourRules;


	public MapPainter(List<AbstractPalette> colourRules, Spectrum data)
	{
		this.colourRules = colourRules;
		this.data = data;

	}
	
	public MapPainter(AbstractPalette colourRule, Spectrum data)
	{
		List<AbstractPalette> rules = DataTypeFactory.<AbstractPalette>list();
		rules.add(colourRule);
		this.colourRules = rules;
		this.data = data;
	}


	@Override
	protected float getBaseUnitSize(scidraw.drawing.DrawingRequest dr)
	{
		// TODO Auto-generated method stub
		return 1;
	}
	
	public Color getColourFromRules(double intensity, double maximum)
	{

		Color c;
		
		for (AbstractPalette r : colourRules) {
			c = r.getFillColour(intensity, maximum);
			if (c != null) return c;
		}

		return new Color(0.0f, 0.0f, 0.0f, 0.0f);

	}
	
	public void setPalette(AbstractPalette palette)
	{
		colourRules.clear();
		colourRules.add(palette);
	}
	public void setPalettes(List<AbstractPalette> palettes)
	{
		colourRules.clear();
		colourRules.addAll(palettes);
	}

	
}
