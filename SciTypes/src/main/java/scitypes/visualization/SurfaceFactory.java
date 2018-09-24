package scitypes.visualization;

public interface SurfaceFactory {

	/**
	 * This creates a Surface backed by the given screen source
	 * 
	 * @param backing
	 *            the backend to create this Surface around
	 * @return a new Surface object which will wrap the given backend source
	 * 
	 * @see Surface
	 */
	public Surface createScreenSurface(Object backing);
	
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
	public SaveableSurface createSaveableSurface(SurfaceType type, int width, int height);
	
	
}
