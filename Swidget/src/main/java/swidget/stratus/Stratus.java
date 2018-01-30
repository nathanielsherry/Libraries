package swidget.stratus;

import java.awt.Color;

public class Stratus {

    public enum ButtonState {
    	DISABLED,
    	ENABLED,
    	MOUSEOVER,
    	FOCUSED,
    	PRESSED,
    	DEFAULT,
    	SELECTED
    }
	
    
    
	public static Color highlight = new Color(0x498ed8);
	public static Color control = new Color(0xe9e9e9);
	public static Color controlText = new Color(0x303030);
	public static Color border = new Color(0xBABABA);
	

	
	public static float borderRadius = 5;
	
	public static Color lighten(Color src) {
		return lighten(src, 0.05f);
	}
	
	public static Color lighten(Color src, float amount) {
    	float[] hsb = new float[3];
    	Color.RGBtoHSB(src.getRed(), src.getGreen(), src.getBlue(), hsb);
    	hsb[2] += amount;
    	if (hsb[2] > 1f) { hsb[2] = 1f; }
    	return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }
	
	
    
	public static Color darken(Color src) {
		return darken(src, 0.05f);
	}
	
    public static Color darken(Color src, float amount) {
    	float[] hsb = new float[3];
    	Color.RGBtoHSB(src.getRed(), src.getGreen(), src.getBlue(), hsb);
    	hsb[2] -= amount;
    	return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }
    
    public static Color saturate(Color src, float amount) {
    	float[] hsb = new float[3];
    	Color.RGBtoHSB(src.getRed(), src.getGreen(), src.getBlue(), hsb);
    	hsb[1] += amount;
    	return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }
    
    public static Color desaturate(Color src, float amount) {
    	float[] hsb = new float[3];
    	Color.RGBtoHSB(src.getRed(), src.getGreen(), src.getBlue(), hsb);
    	hsb[1] -= amount;
    	return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }
	
}
