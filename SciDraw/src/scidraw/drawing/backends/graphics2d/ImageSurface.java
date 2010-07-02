package scidraw.drawing.backends.graphics2d;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import scidraw.drawing.backends.SaveableSurface;
import scidraw.drawing.backends.Surface;




class ImageSurface extends AbstractGraphicsSurface implements SaveableSurface 
{

	private BufferedImage image;
	
	public ImageSurface(BufferedImage i)
	{
		super(i.createGraphics());
		image = i;
	}

	public void write(OutputStream out) throws IOException
	{
		graphics.finalize();
		ImageIO.write(image, "PNG", out);
		
		
	}

	public Surface getNewContextForSurface()
	{
		return new ImageSurface(image);
	}

	public boolean isVectorSurface() {
		return false;
	}

}
