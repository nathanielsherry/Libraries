package scitypes.visualization.backend.awt;

import java.awt.Graphics2D;

import scitypes.visualization.Surface;


class ScreenSurface extends AbstractGraphicsSurface
{

	public ScreenSurface(Graphics2D g)
	{
		super(g);
	}
	
	public Surface getNewContextForSurface()
	{
		return new ScreenSurface((Graphics2D)graphics.create());
	}

	public boolean isVectorSurface() {
		return false;
	}
	
}
