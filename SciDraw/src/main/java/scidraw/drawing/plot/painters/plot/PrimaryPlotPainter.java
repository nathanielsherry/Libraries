package scidraw.drawing.plot.painters.plot;


import java.awt.Color;

import scitypes.ReadOnlySpectrum;




public class PrimaryPlotPainter extends AreaPainter
{

	public PrimaryPlotPainter(ReadOnlySpectrum data, boolean isMonochrome)
	{
		super(data, getTopColor(isMonochrome), getBottomColor(isMonochrome), getStrokeColor(isMonochrome));
	}
	public PrimaryPlotPainter(ReadOnlySpectrum data)
	{
		super(data, getTopColor(false), getBottomColor(false), getStrokeColor(false));
	}
	
	private static Color getTopColor(boolean isMonochrome)
	{
		if (isMonochrome) return new Color(0x606060);
		return new Color(0x388E3C); //material green 700
	}
	
	private static Color getBottomColor(boolean isMonochrome)
	{
		if (isMonochrome) return new Color(0x707070);
		return new Color(0x43A047); //material green 600
	}
	
	private static Color getStrokeColor(boolean isMonochrome)
	{
		if (isMonochrome) return new Color(0x202020);
		return new Color(0x1B5E20); //material green 900
	}
	

}
