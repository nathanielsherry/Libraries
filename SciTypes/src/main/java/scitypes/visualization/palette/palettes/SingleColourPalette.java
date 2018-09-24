package scitypes.visualization.palette.palettes;

import scitypes.visualization.palette.PaletteColour;

public class SingleColourPalette extends AbstractPalette
{

	private PaletteColour colour;
	
	public SingleColourPalette(PaletteColour c)
	{
		colour = c;
	}
	
	@Override
	public PaletteColour getFillColour(double intensity, double maximum)
	{
		return colour;
	}

}
