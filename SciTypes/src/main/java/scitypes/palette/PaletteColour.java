package scitypes.palette;

public class PaletteColour {

	private int value;
	
	public PaletteColour() {
		this(0);
	}
	
	public PaletteColour(int value) {
		this.value = value;
	}
	
	public PaletteColour(int alpha, int red, int green, int blue) {
		value = (alpha<<24) | (red<<16) | (green<<8) | (blue<<0);
	}
	
	public int getAlpha() {
		return (value & 0xFF000000) >> 24;
	}
	
	public int getRed() {
		return (value & 0x00FF0000) >> 16;
	}
	
	public int getGreen() {
		return (value & 0x0000FF00) >> 8;
	}
	
	public int getBlue() {
		return (value & 0x000000FF) >> 0;
	}
	
	public int getARGB() {
		return value;
	}
	
	public PaletteColour blend(PaletteColour other, double percentOther) {
		int a, r, g, b;
		//rgb vlaues here are just a mix -- 30% along means 70% start colour, 30% end colour
		a = (int)Math.round(this.getAlpha()   * (1.0 - percentOther) + other.getAlpha()   * percentOther);
		r = (int)Math.round(this.getRed()     * (1.0 - percentOther) + other.getRed()     * percentOther);
		g = (int)Math.round(this.getGreen()   * (1.0 - percentOther) + other.getGreen()   * percentOther);
		b = (int)Math.round(this.getBlue()    * (1.0 - percentOther) + other.getBlue()    * percentOther);
		
		return new PaletteColour(a, r, g, b);
		
	}
	
	public String toString() {
		return Integer.toHexString(value);
	}
	
}
