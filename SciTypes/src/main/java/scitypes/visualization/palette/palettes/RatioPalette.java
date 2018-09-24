package scitypes.visualization.palette.palettes;



import java.util.List;

import scitypes.visualization.palette.PaletteColour;
import scitypes.visualization.palette.Spectrums;



public class RatioPalette extends AbstractPalette
{

	private List<PaletteColour>	spectrum;


	public RatioPalette()
	{
		this.spectrum = Spectrums.RatioThermalScale();
	}


	public RatioPalette(boolean isMonochrome)
	{
		if (isMonochrome)
		{
			this.spectrum = Spectrums.RatioMonochromeScale();
		}
		else
		{
			this.spectrum = Spectrums.RatioThermalScale();
		}
	}


	public RatioPalette(int steps, boolean isMonochrome)
	{
		if (isMonochrome)
		{
			this.spectrum = Spectrums.RatioMonochromeScale(steps);
		}
		else
		{
			this.spectrum = Spectrums.RatioThermalScale(steps);
		}
	}


	@Override
	public PaletteColour getFillColour(double intensity, double maximum)
	{	
		double percentage = (intensity + maximum) / (2 * maximum);
				
		int index = (int) (spectrum.size() * percentage);
		if (index == spectrum.size()) index--;
		
		return spectrum.get(index);
	}

}
