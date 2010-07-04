package scidraw.drawing.map.painters;


import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import plural.workers.PluralEachIndex;
import plural.workers.PluralMap;
import plural.workers.executor.ThreadPoolUsers;
import plural.workers.executor.eachindex.implementations.PluralEachIndexExecutor;
import plural.workers.executor.maps.implementations.PluralMapExecutor;

import fava.Functions;
import fava.datatypes.Pair;
import fava.datatypes.Range;
import fava.datatypes.Triplet;


import scidraw.datatypes.DataTypeFactory;
import scidraw.drawing.backends.Surface;
import scidraw.drawing.map.MapDrawing;
import scidraw.drawing.map.palettes.AbstractPalette;
import scidraw.drawing.painters.PainterData;
import scitypes.Coord;
import scitypes.GridPerspective;
import scitypes.Spectrum;

/**
 * 
 * This class implements the drawing of a map using contour outlining
 * 
 * @author Nathaniel Sherry, 2009
 */

public class ContourMapPainter extends MapPainter
{

	private int contourSteps;
	
	private enum Threshold
	{
		ABOVE, BELOW, OUTSIDE
	}


	public ContourMapPainter(List<AbstractPalette> colourRules, Spectrum data, int steps)
	{
		super(colourRules, data);
		this.contourSteps = steps;
	}


	@Override
	public void drawElement(PainterData p)
	{
		boolean oldState = ThreadPoolUsers.isPersistent();
		ThreadPoolUsers.setPersistent(true);
		
		GridPerspective<Double> grid = new GridPerspective<Double>(p.dr.dataWidth, p.dr.dataHeight, 0.0);
		trace(p, grid, data);
		
		ThreadPoolUsers.setPersistent(oldState);
		
		
	}


	private void trace(final PainterData p, GridPerspective<Double> grid, Spectrum list)
	{

		
		
		// generate a list of threshold maps, once for each step in the spectrum.
		final List<List<Threshold>> thresholds = getThresholdCellsSet(p, list);
		final GridPerspective<Threshold> thresholdGrid = new GridPerspective<Threshold>(grid.width, grid.height,
				Threshold.OUTSIDE);

		// generate a list of bordercells from the threshold maps
		final List<List<Boolean>> borderCellSet = getBorderCellsSet(grid, thresholds);
		final GridPerspective<Boolean> borderGrid = new GridPerspective<Boolean>(grid.width + 1, grid.height + 1, false);

		//Color colour;

		
		
		for (int i = 0; i < borderCellSet.size(); i++) {

			//colour = getColourFromRules(i, borderCellSet.size());
			
			
			//p.context.setSource(colour.getRed(), colour.getGreen(), colour.getBlue());

			//traceAllRegionsFromBorderCells(p, borderGrid, borderCellSet.get(i), thresholdGrid, thresholds.get(i));
		}

		
		
		
		PluralMap<Integer, Pair<Boolean, Surface>> mapLayers = new PluralMap<Integer, Pair<Boolean,Surface>>() {

			public Pair<Boolean, Surface> f(Integer i) {
			
				Color colour = getColourFromRules(i, borderCellSet.size());
				
				Surface context = p.context.getNewContextForSurface();
				
				context.setSource(colour.getRed(), colour.getGreen(), colour.getBlue());

				boolean trace = traceAllRegionsFromBorderCells(p, context, borderGrid, borderCellSet.get(i), thresholdGrid, thresholds.get(i));
				
				return new Pair<Boolean, Surface>(trace, context);
				
			}
		};
		
		
		//execute the map and get the result
		List<Pair<Boolean, Surface>> surfaces = 
			new PluralMapExecutor<Integer, Pair<Boolean,Surface>>(
					new Range(0, contourSteps-1).map(Functions.<Integer>id()), 
					mapLayers
			).executeBlocking();
		
		
		//grab the surfaces from the result, and fill them sequentially
		for (Pair<Boolean, Surface> pair : surfaces)
		{
			if (pair.first) pair.second.fill();
		}

		


	}


	/*
	 * -------------------------------------------------------------------
	 * 
	 * METHODS FOR CREATING A LIST OF BORDER CELLS
	 * 
	 * -------------------------------------------------------------------
	 */

	private List<List<Threshold>> getThresholdCellsSet(PainterData p, final Spectrum list)
	{
		final List<List<Threshold>> thresholds = DataTypeFactory.<Threshold> datasetInit(contourSteps);

		double listMax = p.dr.maxYIntensity; //ListCalculations.max(list);
		if (listMax == 0) listMax = 1; // make sure that a map full of 0 doesn't get painted as high
		final double increment = listMax / contourSteps;
		
		
		PluralEachIndex eachThreshold = new PluralEachIndex() {
			
			public void f(Integer i) {
				thresholds.set(i, getThresholdMap(list, increment * i));
			}
		};
		
		new PluralEachIndexExecutor(contourSteps, eachThreshold).executeBlocking();		 
		
		return thresholds;
	}


	// gets a collection of bordercell lists for tracing outlines at a set of
	// intensities
	private List<List<Boolean>> getBorderCellsSet(GridPerspective<Double> grid, final List<List<Threshold>> thresholds)
	{

		// list of grids of xy coords
		final List<List<Boolean>> set = DataTypeFactory.<List<Boolean>> listInit(contourSteps);

		final GridPerspective<Threshold> thresholdGrid = new GridPerspective<Threshold>(grid.width, grid.height,
				Threshold.OUTSIDE);

		
		PluralEachIndex eachBorder = new PluralEachIndex() {
			
			public void f(Integer i) {
				set.set(i, getBorderCells(thresholdGrid, thresholds.get(i)));
			}
		};

		
		new PluralEachIndexExecutor(contourSteps, eachBorder).executeBlocking();

		return set;

	}


	// gets a bordercell list( List<Pair<Integer, Integer>> ) for a specific
	// threshold map
	private List<Boolean> getBorderCells(GridPerspective<Threshold> grid, List<Threshold> thresholdList)
	{

		// create another map to hold the border cells
		List<Boolean> borderList = DataTypeFactory.<Boolean> listInit((grid.height + 1) * (grid.width + 1));
		GridPerspective<Boolean> borderGrid = new GridPerspective<Boolean>(grid.width + 1, grid.height + 1, false);

		int countsAbove, edgesAvailable;

		for (int x = 0; x < borderGrid.width; x++) {
			for (int y = 0; y < borderGrid.height; y++) {

				countsAbove = 0;
				edgesAvailable = 4;

				if (grid.get(thresholdList, x, y) == Threshold.ABOVE) countsAbove += 1;
				if (grid.get(thresholdList, x - 1, y) == Threshold.ABOVE) countsAbove += 1;
				if (grid.get(thresholdList, x, y - 1) == Threshold.ABOVE) countsAbove += 1;
				if (grid.get(thresholdList, x - 1, y - 1) == Threshold.ABOVE) countsAbove += 1;

				if (grid.get(thresholdList, x, y) == Threshold.OUTSIDE) edgesAvailable -= 1;
				if (grid.get(thresholdList, x - 1, y) == Threshold.OUTSIDE) edgesAvailable -= 1;
				if (grid.get(thresholdList, x, y - 1) == Threshold.OUTSIDE) edgesAvailable -= 1;
				if (grid.get(thresholdList, x - 1, y - 1) == Threshold.OUTSIDE) edgesAvailable -= 1;


				if (edgesAvailable == 4) {

					if (countsAbove == 2 || countsAbove == 3) {
						borderGrid.set(borderList, x, y, true);
					} else {
						borderGrid.set(borderList, x, y, false);
					}

				} else if (edgesAvailable == 2) {

					if (countsAbove == 2) {
						borderGrid.set(borderList, x, y, true);
					} else {
						borderGrid.set(borderList, x, y, false);
					}

				} else if (edgesAvailable == 1) {

					if (countsAbove == 1) {
						borderGrid.set(borderList, x, y, true);
					} else {
						borderGrid.set(borderList, x, y, false);
					}

				}


			}
		}

		return borderList;
	}



	/*
	 * -------------------------------------------------------------------
	 * 
	 * METHODS FOR TRACING AN OUTLINE OF A REGION
	 * 
	 * -------------------------------------------------------------------
	 */

	private boolean traceAllRegionsFromBorderCells(PainterData p, Surface context, GridPerspective<Boolean> grid, List<Boolean> borderCells,
			GridPerspective<Threshold> thresholdGrid, List<Threshold> threshold)
	{

		int counts = 0;
		boolean trace = false;

		for (int y = 0; y < grid.height; y++) {
			for (int x = 0; x < grid.width; x++) {

				if (grid.get(borderCells, x, y) == true) {
					borderCells = traceSingleRegionFromBorderCells(p, context, grid, borderCells, thresholdGrid, threshold, x, y);
					counts++;
					trace = true;
				}

			}
		}


		return trace;

	}


	private List<Boolean> traceSingleRegionFromBorderCells(PainterData p, Surface context, GridPerspective<Boolean> grid, List<Boolean> borderCells,
			GridPerspective<Threshold> thresholdGrid, List<Threshold> threshold, int startX, int startY)
	{

		// tracks a list of points used to outline this region
		List<Boolean> remainingBorderCells = DataTypeFactory.<Boolean> listInit(borderCells);

		Pair<Coord<Integer>, Integer> nextPointResult;


		int x, y;
		x = startX;
		y = startY;
		grid.set(remainingBorderCells, x, y, false);
		float cellSize = MapDrawing.calcCellSize(p.plotSize.x, p.plotSize.y, p.dr);

		float posX, posY;
		posX = (x) * cellSize;
		posY = ((grid.height - 1 - y)) * cellSize;
		context.moveTo(posX, posY);

		int direction = 0;
		Coord<Integer> point;

		while (true) {

			nextPointResult = pickNextPoint(grid, borderCells, thresholdGrid, threshold, x, y, direction);
			if (nextPointResult == null) break;

			point = nextPointResult.first;
			direction = nextPointResult.second;
			if (point.x == startX && point.y == startY) break;
			x = point.x;
			y = point.y;

			// calculate the position scaled to cellsize
			posX = (x) * cellSize;
			posY = ((grid.height - 1 - y)) * cellSize;
			context.lineTo(posX, posY);

			grid.set(remainingBorderCells, x, y, false);

		}

		return remainingBorderCells;
	}



	// generates a threshold map for a given intensity. result is boolean
	// list/map with true for all value
	// above intensity, false otherwise
	private static List<Threshold> getThresholdMap(Spectrum list, double threshold)
	{

		List<Threshold> result = DataTypeFactory.<Threshold> list();

		for (int i = 0; i < list.size(); i++) {
			result.add(list.get(i) >= threshold ? Threshold.ABOVE : Threshold.BELOW);
		}

		return result;
	}


	private static Pair<Coord<Integer>, Integer> pickNextPoint(GridPerspective<Boolean> grid,
			List<Boolean> borderCells, GridPerspective<Threshold> thresholdGrid, List<Threshold> threshold, int x,
			int y, int direction)
	{

		// find the reverse of the current direction. This is the last direction
		// we want to consider
		// we will now swing though all directions in a clockwise manner,
		// starting with the one just past
		// this direction calculated below
		direction = (direction + 5) % 8;

		// for comparisons sake, this is the first direction we will try, don't
		// keep trying if nothing is found
		int firstDirection = direction;
		int newX = -1, newY = -1;

		boolean isOvertopOfThreshold = false, inUnderneathOfThreshold = false;
		while (true) {

			// get the xy offsets for this direction.
			Coord<Integer> offset = getDirectionValues(direction);
			newX = x + offset.x;
			newY = y + offset.y;

			/*
			 * avoid this situation:
			 * 
			 * OXX
			 * XXX
			 * XXO
			 * 
			 * it will try to draw a line between the two Os, so we check to see if this cell and all four
			 * directly adjacent cells are all above when moving on any diagonal line
			 */
			
			int overlapX, overlapY;
			isOvertopOfThreshold = false;
			inUnderneathOfThreshold = false;
			if (offset.x != 0 && offset.y != 0) {
				
				overlapX = x;
				overlapY = y;
				if (offset.x == -1) overlapX--;
				if (offset.y == -1) overlapY--;
				
				if (
						thresholdGrid.get(threshold, overlapX, overlapY) == Threshold.ABOVE
						&& thresholdGrid.get(threshold, overlapX, overlapY - 1) == Threshold.ABOVE
						&& thresholdGrid.get(threshold, overlapX, overlapY + 1) == Threshold.ABOVE
						&& thresholdGrid.get(threshold, overlapX + 1, overlapY) == Threshold.ABOVE
						&& thresholdGrid.get(threshold, overlapX - 1, overlapY) == Threshold.ABOVE
					) isOvertopOfThreshold = true;
				
				if (
						thresholdGrid.get(threshold, overlapX, overlapY) == Threshold.BELOW
						&& thresholdGrid.get(threshold, overlapX, overlapY - 1) == Threshold.BELOW
						&& thresholdGrid.get(threshold, overlapX, overlapY + 1) == Threshold.BELOW
						&& thresholdGrid.get(threshold, overlapX + 1, overlapY) == Threshold.BELOW
						&& thresholdGrid.get(threshold, overlapX - 1, overlapY) == Threshold.BELOW
					) inUnderneathOfThreshold = true;
					

			}
			
			
			/*
			 * 
			 * avoid this situation:
			 * 
			 * edge
			 * 
			 * |
			 * |
			 * |XXXXX
			 * |OOOOX
			 * |XXXXX
			 * 
			 * it will try to draw a vertical line between the Os instead of following the Xs
			 * 
			 */
			if (offset.x == 0 && offset.y != 0) {
				
				overlapX = x;
				overlapY = y;
				if (offset.x == -1) overlapX--;
				if (offset.y == -1) overlapY--;
				
				//because this is an artifact of a clockwise search, the direction of the other cell is fixed
				if (
						thresholdGrid.get(threshold, overlapX, overlapY) == Threshold.BELOW
						&& thresholdGrid.get(threshold, overlapX-1, overlapY) == Threshold.BELOW
					) inUnderneathOfThreshold = true;				

			}
			
			/*
			 * 
			 * avoid this situation:
			 *
			 * XXX
			 * XOX
			 * XOX
			 * XOX
			 * XOX
			 * --- edge
			 * 
			 * it will try to draw a horizontal line between the Os instead of following the Xs
			 * 
			 */
			if (offset.x != 0 && offset.y == 0) {
				
				overlapX = x;
				overlapY = y;
				if (offset.x == -1) overlapX--;
				if (offset.y == -1) overlapY--;
				
				//because this is an artifact of a clockwise search, the direction of the other cell is fixed
				if (
						thresholdGrid.get(threshold, overlapX, overlapY) == Threshold.BELOW
						&& thresholdGrid.get(threshold, overlapX, overlapY-1) == Threshold.BELOW
					) inUnderneathOfThreshold = true;				

			}
			
			
			// exit this loop if the specified cell is a border cell(true), too
			if (grid.get(borderCells, newX, newY) == true && !isOvertopOfThreshold && !inUnderneathOfThreshold) break;

			// next direction
			direction += 1;
			direction %= 8;
			// if we've come full circle, fail out
			if (direction == firstDirection) {
				newX = -1;
				newY = -1;
				break;
			}

		}


		if (newX == -1) return null;
		return new Pair<Coord<Integer>, Integer>(new Coord<Integer>(newX, newY), direction);


	}


	private static Coord<Integer> getDirectionValues(int direction)
	{

		int x = 0, y = 0;

		switch (direction) {
			case 0: // ->
				x = 1;
				y = 0;
				break;
			case 1: // \
				x = 1;
				y = 1;
				break;
			case 2: // \/
				x = 0;
				y = 1;
				break;
			case 3: // /
				x = -1;
				y = 1;
				break;
			case 4: // <-
				x = -1;
				y = 0;
				break;
			case 5: // \
				x = -1;
				y = -1;
				break;
			case 6: // ^
				x = 0;
				y = -1;
				break;
			case 7: // /
				x = 1;
				y = -1;
				break;
		}

		return new Coord<Integer>(x, y);

	}

}
