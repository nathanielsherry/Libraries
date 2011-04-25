package scidraw.drawing.plot.painters.axis;


import java.util.ArrayList;
import java.util.List;


import scidraw.drawing.painters.axis.AxisPainter;
import scidraw.drawing.painters.axis.LineAxisPainter;
import scidraw.drawing.painters.axis.TitleAxisPainter;
import scitypes.Bounds;


public class AxisFactory
{

	public static List<AxisPainter> getAxisPainterSet(String titleX, String titleY, Bounds<Float> rightValueBounds,
			Bounds<Float> bottomValueBounds, Bounds<Float> topValueBounds, Bounds<Float> leftValueBounds,
			boolean yLeftLog, boolean yRightLog, boolean borderRight, boolean borderBottom, boolean borderTop, boolean borderleft)
	{

		List<AxisPainter> painters = new ArrayList<AxisPainter>();

		painters.add(new TitleAxisPainter(1.0f, titleX, null, null, titleY));
		
		painters.add(new TickMarkAxisPainter(rightValueBounds, bottomValueBounds, topValueBounds, leftValueBounds,
				yLeftLog, yRightLog));
		
		painters.add(new LineAxisPainter(borderleft, borderRight, borderTop, borderBottom));

		return painters;

	}

}
