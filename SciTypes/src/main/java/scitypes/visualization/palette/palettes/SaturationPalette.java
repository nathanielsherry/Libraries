package scitypes.visualization.palette.palettes;

import scitypes.visualization.palette.PaletteColour;

public class SaturationPalette extends AbstractPalette
{

	private PaletteColour saturated, unsaturated;
	
	public SaturationPalette(PaletteColour saturated, PaletteColour unsaturated){
		
		this.saturated = saturated;
		this.unsaturated = unsaturated;
		
	}
	
	@Override
	public PaletteColour getFillColour(double intensity, double maximum)
	{
		if (intensity == maximum){
			return saturated;
		}
		return unsaturated;
	}

}
