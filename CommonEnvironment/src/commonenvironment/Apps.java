package commonenvironment;

import java.io.IOException;
import java.lang.reflect.Method;



public class Apps
{

	public static void browser(String _url)
	{
		
		String url = _url.toLowerCase().startsWith("http://") ? _url : "http://" + _url;
		
		
		
		try {
						
			Class<?> desktop = Class.forName("java.awt.Desktop");
			
			Method browse = desktop.getDeclaredMethod("browse", new Class[] {java.net.URI.class});
			Method getDesktop = desktop.getDeclaredMethod("getDesktop");
			
			Object[] urlobjs = new Object[] {java.net.URI.create(url)};
			
			browse.invoke(getDesktop.invoke(null), urlobjs);
			
			
		} catch (Exception e)
		{
						
			//this must be java 5 or earlier
			openDocument(url);
			
		}
		
	}
	

	private static void openDocument(final String location)
	{
		switch (Env.getOS()) 
		{
			case WINDOWS:
				try
				{
					//proper way of launching a webpage viewer
					Runtime.getRuntime().exec("start " + location);
				}
				catch (IOException e){}
				break;
				
			case MAC:
				
				try
				{
					//proper way of launching a webpage viewer
					Runtime.getRuntime().exec("open " + location);
				}
				catch (IOException e){}
				break;
			
			case UNIX:
			case OTHER:
			default:
				
				try
				{
					//proper way of launching a webpage viewer
					Runtime.getRuntime().exec("xdg-open " + location);
				}
				catch (IOException e){}
				break;

			
		}

	}
	
}

