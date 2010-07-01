package scidraw.drawing.map.painters.axis;



import java.awt.Color;
import java.util.List;

import scidraw.drawing.map.palettes.AbstractPalette;
import scidraw.drawing.painters.PainterData;
import scitypes.Coord;
import scitypes.SISize;
import scitypes.SigDigits;

import fava.*;
import fava.datatypes.Pair;
import fava.signatures.FunctionEach;
import static fava.Fn.*;



public class SpectrumCoordsAxisPainter extends AbstractKeyCoordAxisPainter
{
	private int							spectrumSteps;
	private List<AbstractPalette>		colourRules;
	private boolean						negativeValues;
	private List<Pair<Float, String>>	markings;


	public SpectrumCoordsAxisPainter(boolean drawCoords, Coord<Number> topLeftCoord, Coord<Number> topRightCoord,
			Coord<Number> bottomLeftCoord, Coord<Number> bottomRightCoord, SISize coordinateUnits,
			boolean drawSpectrum, int spectrumHeight, int spectrumSteps, List<AbstractPalette> palettes,
			boolean realDimensionsProvided, String descriptor)
	{
		super(
			drawCoords,
			topLeftCoord,
			topRightCoord,
			bottomLeftCoord,
			bottomRightCoord,
			coordinateUnits,
			drawSpectrum,
			spectrumHeight,
			realDimensionsProvided,
			descriptor);

		this.markings = null;
		this.negativeValues = false;
		
		this.spectrumSteps = spectrumSteps;
		this.colourRules = palettes;

	}
	
	public SpectrumCoordsAxisPainter(boolean drawCoords, Coord<Number> topLeftCoord, Coord<Number> topRightCoord,
			Coord<Number> bottomLeftCoord, Coord<Number> bottomRightCoord, SISize coordinateUnits,
			boolean drawSpectrum, int spectrumHeight, int spectrumSteps, List<AbstractPalette> palettes,
			boolean realDimensionsProvided, String descriptor, boolean negativeValues)
	{
		super(
			drawCoords,
			topLeftCoord,
			topRightCoord,
			bottomLeftCoord,
			bottomRightCoord,
			coordinateUnits,
			drawSpectrum,
			spectrumHeight,
			realDimensionsProvided,
			descriptor);

		this.markings = null;
		this.negativeValues = negativeValues;
		
		this.spectrumSteps = spectrumSteps;
		this.colourRules = palettes;
		
	}
	
	public SpectrumCoordsAxisPainter(boolean drawCoords, Coord<Number> topLeftCoord, Coord<Number> topRightCoord,
			Coord<Number> bottomLeftCoord, Coord<Number> bottomRightCoord, SISize coordinateUnits,
			boolean drawSpectrum, int spectrumHeight, int spectrumSteps, List<AbstractPalette> palettes,
			boolean realDimensionsProvided, String descriptor, boolean negativeValues, List<Pair<Float, String>> markings)
	{
		super(
			drawCoords,
			topLeftCoord,
			topRightCoord,
			bottomLeftCoord,
			bottomRightCoord,
			coordinateUnits,
			drawSpectrum,
			spectrumHeight,
			realDimensionsProvided,
			descriptor);

		this.markings = markings;
		this.negativeValues = negativeValues;

		this.spectrumSteps = spectrumSteps;
		this.colourRules = palettes;
	}


	protected void drawKey(final PainterData p)
	{

		p.context.save();

		int steps = spectrumSteps;

		Pair<Float, Float> spectrumBoundsX = getAxisSizeX(p);
		final float position = axesData.xPositionBounds.start + spectrumBoundsX.first;
		final float width = axesData.xPositionBounds.end - axesData.xPositionBounds.start - spectrumBoundsX.second
				- spectrumBoundsX.first;

		float increment = width / (steps * (negativeValues ? 2 : 1));

		float offsetY = axesData.yPositionBounds.end - getKeyBorderSize(p.context).y;
		if (drawCoords) offsetY += keyHeight;

		float spectrumPosition = position;
		for (int i = (negativeValues ? -steps : 0); i < steps; i++)
		{

			p.context.rectangle(spectrumPosition, offsetY, increment + 1.0f, keyHeight);
			p.context.setSource(getColourFromRules(i, steps));
			p.context.fill();
			spectrumPosition += increment;

		}
		p.context.setSource(0, 0, 0);

		final float textBaseline = offsetY + keyHeight + p.context.getFontLeading() + p.context.getFontAscent();
		float textLineHeight = p.context.getFontHeight();
		float fontSize = p.context.getFontSize();

		if (markings == null)
		{
			
			float maxIntensityFloat = p.dr.maxYIntensity;
			String maxIntensity;
			if (maxIntensityFloat < 1)
			{
				maxIntensity = SigDigits.roundFloatTo(p.dr.maxYIntensity, 1);
			} else  {
				maxIntensity = String.valueOf((int) p.dr.maxYIntensity);
			}
			String minIntensity = negativeValues ? "-" + maxIntensity : "0";

			while (width > 0.0 && fontSize > 1.0)
			{

				float expectedTextWidth = p.context.getTextWidth(minIntensity + " " + descriptor + " " + maxIntensity);
				if (expectedTextWidth < width) break;
				fontSize *= (width/expectedTextWidth) * 0.95;
				p.context.setFontSize(fontSize);
			}

			p.context.writeText(minIntensity, position, textBaseline);

			float rightEndWidth = p.context.getTextWidth(maxIntensity);
			p.context.writeText(maxIntensity, position + width - rightEndWidth, textBaseline);

		}
		else
		{
			

			//concatenate the list of strings to display so we can check the width of the total string
			String markingsText = foldr(map(markings, Functions.<Float, String>second()), Functions.strcat(" "));
			//keep shrinking the font size until all of the text until the font size is small enough that it fits
			while (width > 0.0 && fontSize > 1.0)
			{
				//get the width of the text for all of the markings
				float expectedTextWidth = p.context.getTextWidth(markingsText);
				if (expectedTextWidth < width) break;
				
				fontSize *= (width/expectedTextWidth) * 0.95; 
				p.context.setFontSize(fontSize);

			}

			each(markings, new FunctionEach<Pair<Float, String>>() {

				public void f(Pair<Float, String> element)
				{
					if (element.first > 1.0) element.first = 1.0f;
					float textWidth = p.context.getTextWidth(element.second);
					p.context.writeText(element.second, position + ((width - textWidth) * element.first), textBaseline);
				}
			});


		}
		
		float centerWidth = p.context.getTextWidth(descriptor);
		p.context.writeText(descriptor, position + (width - centerWidth) / 2.0f, textBaseline + textLineHeight);

		p.context.restore();

	}


	public Color getColourFromRules(float intensity, float maximum)
	{

		Color c;

		for (AbstractPalette r : colourRules)
		{
			c = r.getFillColour(intensity, maximum);
			if (c != null) return c;
		}

		return new Color(0.0f, 0.0f, 0.0f, 0.0f);

	}

}
