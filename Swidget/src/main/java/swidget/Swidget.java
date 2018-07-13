package swidget;


import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import swidget.dialogues.SplashScreen;
import swidget.icons.IconFactory;



public class Swidget
{

	private static SplashScreen splashWindow;
	
	private static Semaphore initWaiter = new Semaphore(1);
	public static void initializeAndWait(String appName) {
		try {
			initWaiter.acquire();
			initialize(() -> {
				initWaiter.release();
			}, appName);
			initWaiter.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void initialize(Runnable startupTasks, String appName)
	{
		initialize(null, null, appName, startupTasks);
	}
	
	
	public static void initialize(String splashBackground, String splashIcon, String appName, Runnable startupTasks)
	{
		
		//Needed to work around https://bugs.openjdk.java.net/browse/JDK-8130400
		//NEED TO SET THESE RIGHT AT THE START BEFORE ANY AWT/SWING STUFF HAPPENS.
		//THAT INCLUDES CREATING ANY ImageIcon DATA FOR SPLASH SCREEN
		System.setProperty("sun.java2d.xrender", "false");
		System.setProperty("sun.java2d.pmoffscreen", "false");
		
				
		if (splashBackground != null && splashIcon != null) {
			SwingUtilities.invokeLater(() -> {
				splashWindow = new SplashScreen(IconFactory.getImageIcon(splashBackground), IconFactory.getImage(splashIcon), appName);
				splashWindow.repaint();
				
				SwingUtilities.invokeLater(() -> {
					startupTasks.run();
					splashWindow.setVisible(false);
				});
			});
		} else {
			SwingUtilities.invokeLater(() -> {
				startupTasks.run();
			});
		}
		
	}

	public static boolean isNumbusDerivedLaF()
	{
		
		try
		{
			return NimbusLookAndFeel.class.isAssignableFrom(UIManager.getLookAndFeel().getClass());
		}
		catch (Exception e)
		{
			return false;
		}
		
	}
	
	
	public static void main(String[] args)
	{
		
	}
	
	public static Logger logger() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		Logger logger = Logger.getLogger( stElements[0].getClassName() );
		return logger;
	}
	
	
}



