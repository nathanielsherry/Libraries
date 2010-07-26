package scidraw.drawing.map.painters;

/*
import java.awt.Color;
import java.util.List;
import java.util.Stack;

import plural.workers.PluralEachIndex;
import plural.workers.PluralMap;
import plural.workers.executor.ThreadPoolUsers;
import plural.workers.executor.eachindex.implementations.PluralEachIndexExecutor;
import plural.workers.executor.maps.implementations.PluralMapExecutor;

import fava.Fn;
import fava.Functions;
import fava.datatypes.Pair;
import fava.datatypes.Range;
import fava.lists.FList;
import fava.signatures.FunctionMap;


import scidraw.datatypes.DataTypeFactory;
import scidraw.drawing.backends.Surface;
import scidraw.drawing.map.MapDrawing;
import scidraw.drawing.map.palettes.AbstractPalette;
import scidraw.drawing.painters.PainterData;
import scitypes.Coord;
import scitypes.GridPerspective;
import scitypes.Spectrum;
*/
/**
 * 
 * This class implements the drawing of a map using contour outlining
 * 
 * @author Nathaniel Sherry, 2009
 */



enum Threshold
{
	ABOVE, BELOW, OUTSIDE
}

public class ContourMapPainter// extends MapPainter
{
	/*
	private int contourSteps;
	

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

		
		//traceUsingBlobs(p, grid, list);
		
		
		
		// generate a list of threshold maps, once for each step in the spectrum.
		final List<List<Threshold>> thresholds = getThresholdCellsSet(p, list);
		final GridPerspective<Threshold> thresholdGrid = new GridPerspective<Threshold>(grid.width, grid.height,
				Threshold.OUTSIDE);

				
		// generate a list of bordercells from the threshold maps
		final List<List<Integer>> borderCellSet = getBorderCellsSet(grid, thresholds);
		final GridPerspective<Integer> borderGrid = new GridPerspective<Integer>(grid.width + 1, grid.height + 1, 0);

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
			new PluralMapExecutor<Integer, Pair<Boolean,Surface>>
			(
				new Range(0, contourSteps-1).map(Functions.<Integer>id()).toSink(), mapLayers
			).executeBlocking();
		
		
		//grab the surfaces from the result, and fill them sequentially
		//for (Pair<Boolean, Surface> pair : surfaces)
		for (int i = 0; i < surfaces.size(); i++)
		{
			if (i > 9) break;
			Pair<Boolean, Surface> pair = surfaces.get(i);
			if (pair.first) pair.second.fill();
		}

		


	}


	//
	// -------------------------------------------------------------------
	// 
	// METHODS FOR CREATING A LIST OF BORDER CELLS
	// 
	// -------------------------------------------------------------------
	//

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
	private List<List<Integer>> getBorderCellsSet(GridPerspective<Double> grid, final List<List<Threshold>> thresholds)
	{

		// list of grids of xy coords
		final List<List<Integer>> set = DataTypeFactory.<List<Integer>> listInit(contourSteps);

		
		
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


	private List<Integer> getBorderCells(GridPerspective<Threshold> grid, List<Threshold> thresholdList)
	{

		// create another map to hold the border cells
		List<Boolean> borderList = DataTypeFactory.<Boolean> listInit((grid.height + 1) * (grid.width + 1));
		List<Integer> borderCounts = DataTypeFactory.<Integer> listInit((grid.height + 1) * (grid.width + 1));
		GridPerspective<Integer> borderGrid = new GridPerspective<Integer>(grid.width + 1, grid.height + 1, 0);

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
						borderGrid.set(borderCounts, x, y, 1);
					} else {
						borderGrid.set(borderCounts, x, y, 0);
					}

				} else if (edgesAvailable == 2) {

					if (countsAbove == 2) {
						borderGrid.set(borderCounts, x, y, 1);
					} else {
						borderGrid.set(borderCounts, x, y, 0);
					}

				} else if (edgesAvailable == 1) {

					if (countsAbove == 1) {
						borderGrid.set(borderCounts, x, y, 1);
					} else {
						borderGrid.set(borderCounts, x, y, 0);
					}

				}


			}
		}

		//
		//for (int i = 0; i < borderList.size(); i++)
		//{
		//	if (borderList.get(i)) {
		//		borderCounts.set(i, getShareCount(borderGrid, borderList, grid.getXYFromIndex(i).first, grid.getXYFromIndex(i).second));
		//	}
		//	else {
		//		borderCounts.set(i, 0);
		//	}
		//}
		//
		//return borderList;
		return borderCounts;
	}



	//
	// -------------------------------------------------------------------
	// 
	// METHODS FOR TRACING AN OUTLINE OF A REGION
	// 
	// -------------------------------------------------------------------
	//

	private boolean traceAllRegionsFromBorderCells(PainterData p, Surface context, GridPerspective<Integer> grid, List<Integer> borderCells,
			GridPerspective<Threshold> thresholdGrid, List<Threshold> threshold)
	{

		int counts = 0;
		boolean trace = false;

		for (int y = 0; y < grid.height; y++) {
			for (int x = 0; x < grid.width; x++) {

				if (grid.get(borderCells, x, y) > 0) {
					borderCells = traceSingleRegionFromBorderCells(p, context, grid, borderCells, thresholdGrid, threshold, x, y);
					counts++;
					trace = true;
				}

			}
		}


		return trace;

	}


	private List<Integer> traceSingleRegionFromBorderCells(PainterData p, Surface context, GridPerspective<Integer> grid, List<Integer> borderCells,
			GridPerspective<Threshold> thresholdGrid, List<Threshold> threshold, int startX, int startY)
	{

		// tracks a list of points used to outline this region
		List<Integer> remainingBorderCells = DataTypeFactory.<Integer> listInit(borderCells);
		List<Integer> consideredBorderCells = DataTypeFactory.<Integer> listInit(borderCells);

		Pair<Coord<Integer>, Integer> nextPointResult;


		int x, y;
		x = startX;
		y = startY;
		
		grid.set(remainingBorderCells, x, y, grid.get(remainingBorderCells, x, y) - 1);
		grid.set(consideredBorderCells, x, y, 0);
		
		float cellSize = MapDrawing.calcCellSize(p.plotSize.x, p.plotSize.y, p.dr);

		float posX, posY;
		posX = (x) * cellSize;
		posY = ((grid.height - 1 - y)) * cellSize;
		context.moveTo(posX, posY);

		int direction = 0;
		Coord<Integer> point;

		while (true) {

			nextPointResult = pickNextPoint(grid, consideredBorderCells, thresholdGrid, threshold, x, y, direction);
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

			grid.set(remainingBorderCells, x, y, grid.get(remainingBorderCells, x, y) - 1);
			grid.set(consideredBorderCells, x, y, 0);

		}

		
		return consideredBorderCells;
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


	private static Pair<Coord<Integer>, Integer> pickNextPoint(GridPerspective<Integer> grid,
			List<Integer> borderCells, GridPerspective<Threshold> thresholdGrid, List<Threshold> threshold, int x,
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

			//
			// avoid this situation:
			// 
			// OXX
			// XXX
			// XXO
			// 
			// it will try to draw a line between the two Os, so we check to see if this cell and all four
			// directly adjacent cells are all above when moving on any diagonal line
			//
			
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
			
			
			//
			// 
			// avoid this situation:
			// 
			// edge
			// 
			// |
			// |
			// |XXXXX
			// |OOOOX
			// |XXXXX
			// 
			// it will try to draw a vertical line between the Os instead of following the Xs
			// 
			//
			//

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
			
			//
			// 
			// avoid this situation:
			//
			// XXX
			// XOX
			// XOX
			// XOX
			// XOX
			// --- edge
			// 
			// it will try to draw a horizontal line between the Os instead of following the Xs
			// 
			//
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
			
			
			// exit this loop if the specified cell is a border cell(>0), too
			if (grid.get(borderCells, newX, newY) > 0 && !isOvertopOfThreshold && !inUnderneathOfThreshold) break;

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

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private List<List<Threshold>> getThresholdCellsSetForBlobs(PainterData p, final Spectrum list, final GridPerspective<Threshold> thresholdGrid)
	{
		final List<List<Threshold>> thresholds = DataTypeFactory.<Threshold> datasetInit(contourSteps);

		double listMax = p.dr.maxYIntensity; //ListCalculations.max(list);
		if (listMax == 0) listMax = 1; // make sure that a map full of 0 doesn't get painted as high
		final double increment = listMax / contourSteps;
		
		
		PluralEachIndex eachThreshold = new PluralEachIndex() {
			
			public void f(Integer i) {
				thresholds.set(i, getThresholdMapForBlob(list, increment * i, thresholdGrid));
			}
		};
		
		new PluralEachIndexExecutor(contourSteps, eachThreshold).executeBlocking();		 
		
		return thresholds;
	}

	// generates a threshold map for a given intensity. result is boolean
	// list/map with true for all value
	// above intensity, false otherwise
	private static List<Threshold> getThresholdMapForBlob(Spectrum list, double threshold, final GridPerspective<Threshold> grid)
	{

		List<Threshold> result = DataTypeFactory.<Threshold> list();

		for (int i = 0; i < list.size(); i++) {
			result.add(list.get(i) >= threshold ? Threshold.ABOVE : Threshold.BELOW);
		}

		for (int y = 0; y < grid.height-1; y++) {
			for (int x = 0; x < grid.width-1; x++) {
				
				if (
						grid.get(result, x, y) == Threshold.ABOVE
						&&
						grid.get(result, x+1, y) == Threshold.BELOW
						&&
						grid.get(result, x, y+1) == Threshold.BELOW
						&&
						grid.get(result, x+1, y+1) == Threshold.ABOVE
				) {
					grid.set(result, x, y, Threshold.ABOVE);
					grid.set(result, x+1, y, Threshold.ABOVE);
					grid.set(result, x, y+1, Threshold.ABOVE);
					grid.set(result, x+1, y+1, Threshold.ABOVE);
				}
				
				
				if (
						grid.get(result, x, y) == Threshold.BELOW
						&&
						grid.get(result, x+1, y) == Threshold.ABOVE
						&&
						grid.get(result, x, y+1) == Threshold.ABOVE
						&&
						grid.get(result, x+1, y+1) == Threshold.BELOW
				) {
					grid.set(result, x, y, Threshold.ABOVE);
					grid.set(result, x+1, y, Threshold.ABOVE);
					grid.set(result, x, y+1, Threshold.ABOVE);
					grid.set(result, x+1, y+1, Threshold.ABOVE);
				}
				
			}
		}
		
		return result;
	}


	private void traceUsingBlobs(final PainterData p, GridPerspective<Double> grid, Spectrum list)
	{

		
		
		// generate a list of threshold maps, once for each step in the spectrum.
		final GridPerspective<Threshold> thresholdGrid = new GridPerspective<Threshold>(grid.width, grid.height,
				Threshold.OUTSIDE);
		final List<List<Threshold>> thresholds = getThresholdCellsSetForBlobs(p, list, thresholdGrid);
		
		

				
		// generate a list of bordercells from the threshold maps
		final List<List<Blob>> blobsForLayers = getBlobsForAllLayers(grid, thresholds);
		final GridPerspective<Boolean> borderGrid = new GridPerspective<Boolean>(grid.width + 1, grid.height + 1, false);

				
		
		
		Color colour;
		//for (int i = 0; i < blobsForLayers.size(); i++) {

			colour = getColourFromRules(10, blobsForLayers.size());
			p.context.setSource(colour.getRed(), colour.getGreen(), colour.getBlue());

			traceAllRegionsFromBlobs(p, p.context, borderGrid, blobsForLayers.get(10));
			
		//}
		
	}
	
	

	private static Pair<Coord<Integer>, Integer> pickNextPoint(GridPerspective<Boolean> grid,
			List<Boolean> borderCells, Blob blob, int x, int y, int direction)
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

			//
			// avoid this situation:
			// 
			// OXX
			// XXX
			// XXO
			// 
			// it will try to draw a line between the two Os, so we check to see if this cell and all four
			// directly adjacent cells are all above when moving on any diagonal line
			//
			
			int overlapX, overlapY;
			isOvertopOfThreshold = false;
			inUnderneathOfThreshold = false;
			if (offset.x != 0 && offset.y != 0) {
				
				overlapX = x;
				overlapY = y;
				if (offset.x == -1) overlapX--;
				if (offset.y == -1) overlapY--;
				
				if (
						blob.get(overlapX, overlapY) == Threshold.ABOVE
						&& blob.get(overlapX, overlapY - 1) == Threshold.ABOVE
						&& blob.get(overlapX, overlapY + 1) == Threshold.ABOVE
						&& blob.get(overlapX + 1, overlapY) == Threshold.ABOVE
						&& blob.get(overlapX - 1, overlapY) == Threshold.ABOVE
					) isOvertopOfThreshold = true;
				
				if (
						blob.get(overlapX, overlapY) == Threshold.BELOW
						&& blob.get(overlapX, overlapY - 1) == Threshold.BELOW
						&& blob.get(overlapX, overlapY + 1) == Threshold.BELOW
						&& blob.get(overlapX + 1, overlapY) == Threshold.BELOW
						&& blob.get(overlapX - 1, overlapY) == Threshold.BELOW
					) inUnderneathOfThreshold = true;
					

			}
			
			
			//
			// 
			// avoid this situation:
			// 
			// edge
			// 
			// |
			// |
			// |XXXXX
			// |OOOOX
			// |XXXXX
			// 
			// it will try to draw a vertical line between the Os instead of following the Xs
			// 
			//

			if (offset.x == 0 && offset.y != 0) {
				
				overlapX = x;
				overlapY = y;
				if (offset.x == -1) overlapX--;
				if (offset.y == -1) overlapY--;
				
				//because this is an artifact of a clockwise search, the direction of the other cell is fixed
				if (
						blob.get(overlapX, overlapY) == Threshold.BELOW
						&& 
						blob.get(overlapX-1, overlapY) == Threshold.BELOW
					) inUnderneathOfThreshold = true;				

			}
			
			//
			// 
			// avoid this situation:
			//
			// XXX
			// XOX
			// XOX
			// XOX
			// XOX
			// --- edge
			// 
			// it will try to draw a horizontal line between the Os instead of following the Xs
			// 
			//
			
			if (offset.x != 0 && offset.y == 0) {
				
				overlapX = x;
				overlapY = y;
				if (offset.x == -1) overlapX--;
				if (offset.y == -1) overlapY--;
				
				//because this is an artifact of a clockwise search, the direction of the other cell is fixed
				if (
						blob.get(overlapX, overlapY) == Threshold.BELOW
						&& 
						blob.get(overlapX, overlapY-1) == Threshold.BELOW
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


	
	
	
	private void traceAllRegionsFromBlobs(PainterData p, Surface context, GridPerspective<Boolean> grid, List<Blob> blobs)
	{

		for (Blob b : blobs)
		{
			List<Boolean> borderCells = b.getBorderCells();
			
			while (borderCells.contains(true)) {
				borderCells = traceSingleRegionFromBlob(p, context, grid, borderCells, b);
				
				p.context.stroke();
				
			}
			
			
			
		}
		

	}
	

	private List<Boolean> traceSingleRegionFromBlob(PainterData p, Surface context, GridPerspective<Boolean> grid, List<Boolean> borderCells, Blob blob)
	{

		
		int startX = -1, startY = -1;
		
		for (int i = 0; i < borderCells.size(); i++)
		{
			if (borderCells.get(i))
			{
				Pair<Integer, Integer> pair = grid.getXYFromIndex(i);
				startX = pair.first;
				startY = pair.second;
				break;
			}
		}
		
		
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

			nextPointResult = pickNextPoint(grid, borderCells, blob, x, y, direction);
			if (nextPointResult == null) {
				
				//posX = (startX) * cellSize;
				//posY = ((grid.height - 1 - startY)) * cellSize;
				//context.lineTo(posX, posY);
				break;
				
			}

			point = nextPointResult.first;
			direction = nextPointResult.second;
			
			
			x = point.x;
			y = point.y;

			// calculate the position scaled to cellsize
			posX = (x) * cellSize;
			posY = ((grid.height - 1 - y)) * cellSize;
			context.lineTo(posX, posY);

			if (point.x == startX && point.y == startY) break;
			
			
			grid.set(remainingBorderCells, x, y, false);

		}

		return remainingBorderCells;
	}

	

	private List<List<Blob>> getBlobsForAllLayers(GridPerspective<Double> grid, final List<List<Threshold>> thresholds)
	{

		// list of grids of xy coords
		final List<List<Blob>> set = DataTypeFactory.<List<Blob>> listInit(contourSteps);

		final GridPerspective<Threshold> thresholdGrid = new GridPerspective<Threshold>(grid.width, grid.height,
				Threshold.OUTSIDE);

		
		//
		//PluralEachIndex eachBorder = new PluralEachIndex() {
			
		//	public void f(Integer i) {
		//		set.set(i, getBlobs(thresholdGrid, thresholds.get(i)));
		//	}
		//};

		//new PluralEachIndexExecutor(contourSteps, eachBorder).executeBlocking();
		//
		
		
		for (int i = 0; i < contourSteps; i++)
		{
			set.set(i, getBlobs(thresholdGrid, thresholds.get(i)));
		}
		
		return set;

	}
	

	//isolate each blob from a grid of over/under/outside values
	private List<Blob> getBlobs(GridPerspective<Threshold> grid, List<Threshold> thresholdList)
	{
		
		List<Blob> blobs = DataTypeFactory.<Blob>list();
		
		Blob b;
		while (true) {
		
			b = getBlob(grid, thresholdList);
			if (b.isEmpty()) break;
			blobs.add( b );
			
			//System.out.println(b.show());

		}
		
		return blobs;
		
	}
	
	private Blob getBlob(GridPerspective<Threshold> grid, List<Threshold> thresholdList)
	{
		//Blob currentBlob = new Blob(grid.width, grid.height);
		
		for (int y = 0; y < grid.height; y++) {
			for (int x = 0; x < grid.width; x++) {
				
				if (  grid.get(thresholdList, x, y) != Threshold.BELOW  ) {
					return getBlobAtCoord(grid, thresholdList, x, y);				
				}
					
			}
		}
		
		return new Blob(grid.width, grid.height);
		
		
	}
	
	
	private Blob getBlobAtCoord(GridPerspective<Threshold> grid, List<Threshold> thresholdList, int x, int y)
	{
		Stack<Coord<Integer>> coords = new Stack<Coord<Integer>>();
		Coord<Integer> currentCoord;
		Blob currentBlob = new Blob(grid.width, grid.height);
		
		coords.add(new Coord<Integer>(x, y));
		
		
		while (! coords.isEmpty()) 
		{
			currentCoord = coords.pop();
			x = currentCoord.x;
			y = currentCoord.y;
			boolean added = getBlobHandlePoint(grid, thresholdList, currentBlob, x, y);
						
			if (added)
			{				
				coords.push(new Coord<Integer>(x+1, y));
				coords.push(new Coord<Integer>(x, y+1));
				coords.push(new Coord<Integer>(x-1, y));
				coords.push(new Coord<Integer>(x, y-1));
			}			
		}
		
				
		return currentBlob;		
		
	}
	
	//returns true if this cell was added to the blob
	private boolean getBlobHandlePoint(GridPerspective<Threshold> grid, List<Threshold> thresholdList, Blob b, int x, int y)
	{
		if (x < 0 || y < 0 || x >= grid.width || y >= grid.height) {
			return false;
		}
		
		if (  grid.get(thresholdList, x, y) != Threshold.BELOW  &&  (b.nextToPoint(x, y) || b.isEmpty())  )
		{
			//this cell has been accounted for
			b.addToBlob(x, y, grid.get(thresholdList, x, y));
			grid.set(thresholdList, x, y, Threshold.BELOW);
			return true;
		}
		
		
		return false;
		
	}
	
	
}




class Blob
{
	private List<Threshold> cells;
	private GridPerspective<Threshold> grid;
	private boolean empty;
	
	public Blob(int x, int y)
	{
		cells = DataTypeFactory.<Threshold>listInit(x*y);
		grid = new GridPerspective<Threshold>(x, y, Threshold.BELOW);
		empty = true;
		
	}
	
	public boolean isEmpty()
	{
		return empty;
	}
	
	//check to see if this blob is next to this point
	public boolean nextToPoint(int x, int y)
	{
		//if this is IN the blob, its not NEXT TO it
		if (get(x, y) != Threshold.BELOW) return false;
		
		if (get(x+1,y) != Threshold.BELOW) return true;
		if (get(x,y+1) != Threshold.BELOW) return true;
		if (get(x-1,y) != Threshold.BELOW) return true;
		if (get(x,y-1) != Threshold.BELOW) return true;
		return false;
	}
	
	public void addToBlob(int x, int y, Threshold value)
	{
		grid.set(cells, x, y, value);
		empty = false;
	}
	
	
	public Threshold get(int x, int y)
	{
		return grid.get(cells, x, y);
	}
	
	
	public String show()
	{
		String out = "";
		
		for (int y = 0; y < grid.height; y++)
		{
			for (int x = 0; x < grid.width; x++)
			{
				if (grid.get(cells, x, y) == Threshold.ABOVE) out += "█";
				if (grid.get(cells, x, y) == Threshold.OUTSIDE) out += "▒";
				if (grid.get(cells, x, y) == Threshold.BELOW) out += "░";
					
			}
			
			out += "\n";
		}
		
		return out;
		
	}
	
	public List<Boolean> getBorderCells()
	{
		
		// create another map to hold the border cells
		List<Boolean> borderList = DataTypeFactory.<Boolean> listInit((grid.height + 1) * (grid.width + 1));
		GridPerspective<Boolean> borderGrid = new GridPerspective<Boolean>(grid.width + 1, grid.height + 1, false);

		int countsAbove, edgesAvailable;

		for (int x = 0; x < borderGrid.width; x++) {
			for (int y = 0; y < borderGrid.height; y++) {

				countsAbove = 0;
				edgesAvailable = 4;

				if (get(x, y) == Threshold.ABOVE) countsAbove += 1;
				if (get(x - 1, y) == Threshold.ABOVE) countsAbove += 1;
				if (get(x, y - 1) == Threshold.ABOVE) countsAbove += 1;
				if (get(x - 1, y - 1) == Threshold.ABOVE) countsAbove += 1;

				if (get(x, y) == Threshold.OUTSIDE) edgesAvailable -= 1;
				if (get(x - 1, y) == Threshold.OUTSIDE) edgesAvailable -= 1;
				if (get(x, y - 1) == Threshold.OUTSIDE) edgesAvailable -= 1;
				if (get(x - 1, y - 1) == Threshold.OUTSIDE) edgesAvailable -= 1;


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

				} else {
					borderGrid.set(borderList, x, y, false);
				}


			}
		}

		return borderList;
		
	}
	
	*/
	
}



