package scidraw.drawing.common;

import java.awt.Color;
import java.util.List;

public enum Palette
{

		THERMAL {
			@Override
			public int[] getPaletteData()
			{
				return thermal;
			}
		},
		MONOCHROME {
			@Override
			public int[] getPaletteData()
			{
				return monochrome;
			}
		},
		MONOCHROME_INVERTED {
			@Override
			public int[] getPaletteData()
			{
				return inv_monochrome;
			}
		},
		THOUGHTFUL {
			@Override
			public int[] getPaletteData()
			{
				return thoughtful;
			}
		},
		TERRA {
			@Override
			public int[] getPaletteData()
			{
				return terra;
			}
		},
		OLIVE {
			@Override
			public int[] getPaletteData()
			{
				return olive;
			}
		},
		VINTAGE {
			@Override
			public int[] getPaletteData()
			{
				return vintage;
			}
		},
		GOLDFISH {
			@Override
			public int[] getPaletteData()
			{
				return goldfish;
			}
		},
		SUGAR {
			@Override
			public int[] getPaletteData()
			{
				return sugar;
			}
		},
		BROWN_SUGAR {
			@Override
			public int[] getPaletteData()
			{
				return brownsugar;
			}
		},
		BLACKBODY {
			@Override
			public int[] getPaletteData()
			{
				return blackbody;
			}
		}
		;
		public abstract int[] getPaletteData();

		public List<Color> toSpectrum()
		{
			return Spectrums.getScale(this);
		}
		
		public String toString()
		{
			String name = this.name();
			name = name.replace('_', ' ');
			
			
			
			return name;			
		}
		
		private final static int[] thermal = { 
			 18,  41,  77,   0,
			 41,  89, 166,  77,
			 89, 176,   8,  51, 
			224, 186,   0,  59,
			207,  92,   0,  31, 
			163,   0,   0,  37
		};
		

		private final static int[] monochrome = {
			  0,   0,   0,   0,
			255, 255, 255, 255
		};
		

		private final static int[] inv_monochrome = {
			255, 255, 255, 255,
			  0,   0,   0,   0
		};
		
		//nice
		private final static int[] thoughtful = {
			236, 208, 120,   0,
			217,  91,  67,  85,
			192,  41,  66,  85,
			 84,  36,  55,  85
		};
		
		//nice
		private final static int[] terra = {
			  3,  22,  52,   0,
			  3,  54,  73,  64,
			  3, 101, 100,  64,
			205, 179, 128,  64,
			232, 221, 203,  63
		};
		
		//good on unsubtracted, okay on subtracted
		private final static int[] olive = {
			 48,   0,  24,   0,
			 90,  61,  49,  64,
			131, 123,  71,  64,
			173, 184,  95,  64,
			229, 237, 184,  63
		};
		
		private final static int[] vintage = {
			140,  35,  24,   0,
			 94, 140, 106,  64,
			136, 166,  94,  64,
			191, 179,  90,  64,
			242, 196,  90,  63
		};
		
		private final static int[] goldfish = {
			105, 210, 231,   0,
			167, 219, 216,  80,
			
			224, 228, 204,  64,
			217, 214, 184,  24,
			
			243, 148,  73,  16,
			250, 105,   0,  48,
			255,  64,   0,  31
		};
		
		// based off of "sugar is three"
		private final static int[] sugar = {
			 44, 139, 154,   0,
			
			106, 195, 174,  64,
			217, 200, 167,  48,
			249, 205, 173,  48,
			
			252, 157, 154,  48,
			254,  67, 101,  32,
			254,  38,  77,  15
		};


		private final static int[] brownsugar = {
			 73,  10,  61,   0,
			189,  21,  80,  64,
			233, 127,   2,  64,
			248, 202,   0,  64,
			138, 155,  15,  63
		};


		private final static int[] blackbody = {
			248,  38,   0,   0,
			255, 177,  47,  64,
			255, 231, 191,  64,
			223, 230, 255,  64,
			166, 186, 255,  63
		};
	
}
