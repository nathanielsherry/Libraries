package scidraw.swing;


import java.awt.Graphics;
import java.awt.Transparency;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JPanel;

import scidraw.drawing.backends.DrawingSurfaceFactory;
import scidraw.drawing.backends.SaveableSurface;
import scidraw.drawing.backends.Surface;
import scidraw.drawing.backends.SurfaceType;


/**
 * 
 * This is an abstract class for controllers which draw something using a supported graphics backend. The given graphics context will be wrapped in a related {@link Surface} to be drawn to
 * 
 * @author Nathaniel Sherry, 2009
 * 
 */

public abstract class GraphicsPanel extends JPanel
{

	private boolean buffer = false;
	private float bufferSlack = 1.2f;
	private VolatileImage bimage;

	public GraphicsPanel()
	{
		System.setProperty("sun.java2d.opengl", "True");
	}
	
	public GraphicsPanel(boolean buffered)
	{
		this();
		buffer = buffered;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if (buffer) {
			if (
					bimage == null || 
					bimage.getWidth() < getWidth() || bimage.getWidth() > getWidth() * (bufferSlack*bufferSlack) ||
					bimage.getHeight() < getHeight() || bimage.getHeight() > getHeight() * (bufferSlack*bufferSlack)
					){
				createBackBuffer();
			}
					
			Graphics bg = bimage.getGraphics();
			draw(bg);
			g.drawImage(bimage, 0, 0, this);
		
		} else {

			draw(g);
			
		}
	}
	
	private void createBackBuffer()
	{
		bimage = getGraphicsConfiguration().createCompatibleVolatileImage(
					(int)(getWidth()*bufferSlack), 
					(int)(getHeight()*bufferSlack), 
					Transparency.OPAQUE
				);
		bimage.setAccelerationPriority(1f);
	}
	
	private void draw(Object drawContext)
	{
		drawGraphics(DrawingSurfaceFactory.createScreenSurface(drawContext), false);
	}


	public void writePNG(OutputStream out) throws IOException
	{
		write(SurfaceType.RASTER, out);
	}


	public void writeSVG(OutputStream out) throws IOException
	{
		write(SurfaceType.VECTOR, out);
	}


	public void writePDF(OutputStream out) throws IOException
	{
		write(SurfaceType.PDF, out);
	}


	private void write(SurfaceType type, OutputStream out) throws IOException
	{

		boolean vector = false;
		if (type == SurfaceType.PDF || type == SurfaceType.VECTOR) vector = true;
		
		SaveableSurface b = DrawingSurfaceFactory.createSaveableSurface(type, (int) getUsedWidth(),
				(int) getUsedHeight());
		drawGraphics(b, vector);
		b.write(out);
	}



	/**
	 * Draw to the given Surface
	 * @param backend the surface to draw to
	 * @param vector indicates if this drawing is to a vector surface
	 */
	protected abstract void drawGraphics(Surface backend, boolean vector);


	/**
	 * Report on how wide the actual drawing is. Since drawings may not always fit 
	 * the window they are shown in, it is important for the export functionality to
	 * know how large the desired drawing actually is
	 * @return the actual width of the drawing
	 */
	public abstract float getUsedWidth();
	public abstract float getUsedWidth(float zoom);

	
	/**
	 * Report on how high the actual drawing is. Since drawings may not always fit 
	 * the window they are shown in, it is important for the export functionality to
	 * know how large the desired drawing actually is
	 * @return the actual height of the drawing
	 */
	public abstract float getUsedHeight();
	public abstract float getUsedHeight(float zoom);

}
