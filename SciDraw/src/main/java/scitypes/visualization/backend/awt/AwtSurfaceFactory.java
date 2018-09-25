package scitypes.visualization.backend.awt;

import java.awt.Graphics;

import scitypes.visualization.SaveableSurface;
import scitypes.visualization.Surface;
import scitypes.visualization.SurfaceFactory;
import scitypes.visualization.SurfaceType;

/**
 * This factory contains the logic used to create a Surface from the preferred implementation
 * 
 * @author Nathaniel Sherry, 2009
 * 
 * @see Surface
 *
 */

public class AwtSurfaceFactory implements SurfaceFactory
{

	/**
	 * This creates a Surface backed by the given backend source
	 * 
	 * @param backendSource
	 *            the backend to create this Surface around
	 * @return a new Surface object which will wrap the given backend source
	 * 
	 * @see Surface
	 */
	public Surface createScreenSurface(Object backendSource)
	{
		if (backendSource instanceof Graphics) {
			return scitypes.visualization.backend.awt.Graphics2DDrawingSurfaceFactory.createScreenSurface(backendSource);
		}

		return null;
	}
	
	/**
	 * Creates a new surface of the given SurfaceType.
	 * 
	 * @param type
	 *            the type of surface to create
	 * @param width
	 *            the width of the new surface
	 * @param height
	 *            the height of the new surface
	 * @return a new Surface
	 * 
	 * @see Surface
	 * @see SurfaceType
	 */
	public SaveableSurface createSaveableSurface(SurfaceType type, int width, int height)
	{
		return scitypes.visualization.backend.awt.Graphics2DDrawingSurfaceFactory.createSaveableSurface(type, width, height);
	}
	
	
}
