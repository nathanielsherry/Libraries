package scidraw.drawing.map.painters;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.List;

import scidraw.drawing.map.palettes.SingleColourPalette;
import scidraw.drawing.painters.PainterData;
import scitypes.GridPerspective;
import scitypes.Pair;

public class SelectionMaskPainter extends MapPainter {

	private Color c;
	private List<Integer> points;
	private int sizeX, sizeY;
	
	public SelectionMaskPainter(Color c, List<Integer> points, int sizeX, int sizeY) {
		super(new SingleColourPalette(c));
		this.c = c;
		this.points = points;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	@Override
	public void drawMap(PainterData p, float cellSize, float rawCellSize) {
		GridPerspective<Integer> grid = new GridPerspective<Integer>(sizeX, sizeY, null);
		Area area = new Area();
		
		
		for (int i : points) {
			Pair<Integer, Integer> coord = grid.getXYFromIndex(i);
			area.add(new Area(new Rectangle2D.Float((coord.first * rawCellSize)- 0.01f, (coord.second * rawCellSize) - 0.01f, rawCellSize+0.02f, rawCellSize+0.02f)));
		}
		
		p.context.setLineWidth(Math.max(1, rawCellSize/4));
		
		p.context.addShape(area);
		
		p.context.setSource(c.getRed(), c.getGreen(), c.getBlue(), 96);
		p.context.fillPreserve();
		
		p.context.setSource(c.getRed(), c.getGreen(), c.getBlue());
		p.context.stroke();
		
	}
	
	@Override
	public boolean isBufferingPainter() {
		return false;
	}
	
	@Override
	public void clearBuffer() {}
	
}
