package scidraw.drawing.common;



import java.awt.Color;
import java.util.ArrayList;
import java.util.List;




/**
 * This class provides methods for generating Color spectrums for displaying data.
 * 
 * @author Nathaniel Sherry, 2009
 */

public class Spectrums
{

	public static int	DEFAULT_STEPS	= 1000;


	
	/* spectum is encoded {{r, g, b, p}, ...}
	 * where p is the percent distance from the last colour stop 
	 */
	
	private final static double[][] thermal = { 
		{ 0.07, 0.16, 0.30, 0 }, 
		//{ 0.13, 0.29, 0.53, 0.3f },
		{ 0.16, 0.35, 0.65, 0.3f },
		{ 0.35, 0.69, 0.03, 0.2f }, 
		{ 0.89, 0.73, 0.00, 0.23f },
		{ 0.81, 0.36, 0.00, 0.12f }, 
		{ 0.64, 0.00, 0.00, 0.15f }
	};
	

	private final static double[][] ratioThermal = {
		{ 0.125,	0.29,	0.53,	0 },
		{ 0.063,	0.145,	0.265,	0.3f },
		{ 0.0,		0.0,	0.0,	0.2f },
		{ 0.32,		0.0,	0.0,	0.2f },
		{ 0.64,		0.0,	0.0,	0.3f }
	};
	
	private final static double[][] monochrome = {
		{0,	0,	0,	0},
		{1f, 1f, 1f, 1f}
	};
	
	private final static double[][] ratioMonochrome = { 
		{ 0.0, 0.0, 0.0, 0 }, 
		{ 0.2, 0.2, 0.2, 0.35f }, 
		{ 0.5, 0.5, 0.5, 0.15f }, 
		{ 0.8, 0.8, 0.8, 0.15f },
		{ 1.0, 1.0, 1.0, 0.35f } 
	};
	
	
	
	
	
	

	public static List<Color> ThermalScale()
	{
		return ThermalScale(DEFAULT_STEPS);
	}
	
	public static List<Color> RatioThermalScale()
	{
		return RatioThermalScale(DEFAULT_STEPS);
	}
	
	public static List<Color> MonochromeScale()
	{
		return MonochromeScale(DEFAULT_STEPS);
	}

	public static List<Color> MonochromeScale(Color c)
	{
		return MonochromeScale(DEFAULT_STEPS, c);
	}

	public static List<Color> RatioMonochromeScale()
	{
		return RatioMonochromeScale(DEFAULT_STEPS);
	}
	
	
	
	
	
	/**
	 * Creates a thermal scale spectrum
	 * 
	 * @param steps
	 *            the number of steps in the spectrumBright
	 * @return a thermal scale spectrum
	 */
	public static List<Color> ThermalScale(int steps)
	{
		return ThermalScale(steps, 1.0f, 1.0f);
	}


	/**
	 * Creates a thermal ratio scale spectrum
	 * 
	 * @param steps
	 *            the number of steps in the spectrum
	 * @return a thermal scale spectrum
	 */
	public static List<Color> RatioThermalScale(int steps)
	{
		return RatioThermalScale(steps, 1.0f, 1.0f);
	}


	/**
	 * Creates a monochrome scale spectrum
	 * 
	 * @param steps
	 *            the number of steps in the spectrum
	 * @return a thermal scale spectrum
	 */
	public static List<Color> MonochromeScale(int steps)
	{
		return MonochromeScale(steps, 1.0f, 1.0f);
	}
	
	/**
	 * Creates a monochrome scale spectrum
	 * 
	 * @param steps
	 *            the number of steps in the spectrum
	 * @param c
	 *            the colour of the monochrome scale
	 */
	public static List<Color> MonochromeScale(int steps, Color c)
	{
		return MonochromeScale(steps, 1.0f, 1.0f, c);
	}


	/**
	 * Creates a monochrome ratio scale spectrum
	 * 
	 * @param steps
	 *            the number of steps in the spectrum
	 * @return a thermal scale spectrum
	 */
	public static List<Color> RatioMonochromeScale(int steps)
	{
		return RatioMonochromeScale(steps, 1.0f, 1.0f);
	}

	
	


	
	
	
	
	/**
	 * Creates a thermal scale spectrum with {@link #DEFAULT_STEPS} steps
	 * 
	 * @return a new thermal scale
	 */
	public static List<Color> ThermalScale(int _steps, float brightness, float centreIntensity)
	{
		return generateSpectrum(_steps, thermal, brightness, centreIntensity);
	}

	

	/**
	 * Creates a ratio(red/blue) thermal scale spectrum with {@link #DEFAULT_STEPS} steps
	 * 
	 * @return a new thermal scale
	 */
	public static List<Color> RatioThermalScale(int _steps, float brightness, float centreIntensity)
	{
		return generateSpectrum(_steps, ratioThermal, brightness, centreIntensity);
	}


	/**
	 * Creates a monochrome scale spectrum
	 * 
	 * @param steps
	 *            the number of steps in the spectrum
	 * @return a monochrome scale spectrum
	 */
	public static List<Color> MonochromeScale(int _steps, float brightness, float centreIntensity)
	{
		return generateSpectrum(_steps, monochrome, brightness, centreIntensity);
	}
	
	
	/**
	 * Creates a monochrome scale spectrum ranging from black to the given colour 
	 * 
	 * @param c
	 *            the colour of the monochrome scale
	 * @return a monochrome scale spectrum
	 */
	public static List<Color> MonochromeScale(int _steps, float brightness, float centreIntensity, Color c)
	{

		double[][] monochrome = {
				{0,	0,	0,	0},
				{c.getRed() / 255.0, c.getGreen() / 255.0, c.getBlue() / 255.0, 1f}
		};

		return generateSpectrum(_steps, monochrome, brightness, centreIntensity);

	}


	/**
	 * Creates a ratio(red/blue) thermal scale spectrum with {@link #DEFAULT_STEPS} steps
	 * 
	 * @return a new thermal scale
	 */
	public static List<Color> RatioMonochromeScale(int _steps, float brightness, float centreIntensity)
	{
		return generateSpectrum(_steps, ratioMonochrome, brightness, centreIntensity);
	}


	
	
	


	public static List<Color> generateSpectrum(float totalSteps, double[][] values, float brightness, float centreIntensity)
	{

		//output list
		List<Color> spectrum = new ArrayList<Color>();
		
		
		/* 
		 * Number of steps between each colour stop
		 * stepcount[0] is the number of steps between
		 * values[0] and values[1]
		 */
		int stepcount[] = new int[values.length-1];
		
		
		/* 
		 * Fill the stepcount array with the number of steps 
		 * between each colour stop. To eliminate rounding 
		 * error, we count the number of steps taken so far, 
		 * and adjust the final segment in order to make the 
		 * real total equal the desired number of steps
		 */
		int realSteps = 0;
		for (int i = 1; i < values.length; i++)
		{
			stepcount[i-1] = (int)Math.round(values[i][3] * totalSteps);
			realSteps += stepcount[i-1]; 
		}
		stepcount[stepcount.length - 1] += (totalSteps - realSteps);


		
		/*
		 * For each entry in the stepcount array, we create the 
		 * required number of intermediate colours 
		 */
		double red, blue, green;
		int steps;
		double percent;
		for (int stage = 0; stage < stepcount.length; stage++)
		{

			steps = stepcount[stage];

			//create 'steps' intermediate colours
			for (int step = 0; step < steps; step++)
			{
				
				//how far along from the start colour to the end colour are we?
				percent = (double) step / (double) steps;

				//rgb vlaues here are just a mix -- 30% along means 70% start colour, 30% end colour
				red = values[stage][0] * (1.0 - percent) + values[stage + 1][0] * percent;
				green = values[stage][1] * (1.0 - percent) + values[stage + 1][1] * percent;
				blue = values[stage][2] * (1.0 - percent) + values[stage + 1][2] * percent;

				spectrum.add(new Color((float) red, (float) green, (float) blue));
			}

		}
		
		
		/*
		 * Now we get into the more tricky part of the algorithm. Now we have our
		 * complete colour spectrum generated, and we're going to adjust the colours
		 * in it based on two parameters.
		 * * brightness: Overall brightness. 1.0 is normal 
		 * * centre intensity: Parabolic brightness adjustment with 0 impact on edges
		 */
		
		
		
		/*
		 * We convert a Color object into an HSV triplet, and make adjustments
		 * to the value parameter before converting it back into an RGB-based
		 * Color object
		 */
		float hsv[] = new float[3];
		Color rgbColor;
		float adjust, x;
		
		//for each Color object
		for (int i = 0; i < spectrum.size(); i++) {
			
			//get the hsv values of this Color object
			rgbColor = spectrum.get(i);
			Color.RGBtoHSB(rgbColor.getRed(), rgbColor.getGreen(), rgbColor.getBlue(), hsv);
						
			/*
			 * This is a parabolic curve to brighten the inner part of a spectrum while leaving the outside dark.
			 * x represents how far along the spectrum this point is as a value between -1..1
			 * The resulting porabola will have roots at 0 and spectrum.size() - 1, and will reach
			 * a maximum of (centreIntensity-1f) at (spectrum.size() - 1)/2
			 */
			x = i / (float)spectrum.size() * 2f - 1f;
			adjust = (float)(    1d + (  -(x*x) + 1d  ) * (centreIntensity-1f)    );
	
	
			//apply the adjustments and bound the values to prevent overflow upon conversion to rgb resulting in
			//incorrect hues/sats
			hsv[2] *= (brightness * adjust);
			hsv[2] = Math.min(hsv[2], 1f);
			hsv[2] = Math.max(hsv[2], 0f);
			
			//replace with updated value
			spectrum.set(i, new Color(  Color.HSBtoRGB(hsv[0], hsv[1], hsv[2])  ));
		}
		
		return spectrum;
	
	}

	
}