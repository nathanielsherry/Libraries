package scidraw.drawing.map.painters;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import scidraw.drawing.map.palettes.SingleColourPalette;
import scidraw.drawing.painters.PainterData;
import scitypes.GridPerspective;
import scitypes.Pair;

public class SelectionMaskPainter extends RasterColorMapPainter {

	private Color c;
	
	public SelectionMaskPainter(Color c, List<Integer> points, int sizeX, int sizeY) {
		super();
		
		c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 96);
		Color t = new Color(0, 0, 0, 0);
		
		this.c = c;
		List<Color> colors = new ArrayList<>();
		for (int i = 0; i < (sizeX*sizeY); i++) {
			colors.add(t);
		}
		for (Integer i : points) {
			if (i >= sizeX*sizeY) continue;
			colors.set(i, c);
		}

		setPixels(colors);
	}
	
}
