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
						
			//this must be java 5
			openDocument(url);
			
		}
		
	}
	

	private static void openDocument(final String location)
	{
		Env.perOS(new Env.PerOS(){
			
			public void windows()
			{
				try
				{
					//proper way of launching a webpage viewer
					Runtime.getRuntime().exec("start " + location);
				}
				catch (IOException e)
				{
				}
			}
			
		
			public void unix()
			{
				try
				{
					//proper way of launching a webpage viewer
					Runtime.getRuntime().exec("xdg-open " + location);
				}
				catch (IOException e)
				{						
				}
			}
			
		
			public void other()
			{					
			}
			
			
			public void mac()
			{
				try
				{
					//proper way of launching a webpage viewer
					Runtime.getRuntime().exec("open " + location);
				}
				catch (IOException e)
				{
				}
			}
		});
	}
	
}

