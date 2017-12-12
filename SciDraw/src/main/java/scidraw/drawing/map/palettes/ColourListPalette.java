package scidraw.drawing.map.palettes;

import java.awt.Color;
import java.util.List;

public class ColourListPalette extends AbstractPalette {

	private List<Color> spectrum;
	private boolean hasNegatives;
	
	public ColourListPalette(List<Color> palette, boolean hasNegatives) {
		this.spectrum = palette;
		this.hasNegatives = hasNegatives;
	}
	
	@Override
	public Color getFillColour(double intensity, double maximum) {
		
		double percentage;
		if (hasNegatives) {
			percentage = (intensity + maximum) / (2 * maximum);
		} else {
			percentage = intensity / maximum;
		}
		
		int index = (int)(spectrum.size() * percentage);
		if (index >= spectrum.size()) index = spectrum.size() - 1;
		if (index < 0) index = 0;
		
		
		return spectrum.get(index);
	}

}
