package swidget;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Toolkit;
import java.nio.file.FileSystemLoopException;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JToolTip;
import javax.swing.LookAndFeel;
import javax.swing.Painter;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.ToolTipUI;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import swidget.dialogues.SplashScreen;
import swidget.icons.IconFactory;
import swidget.icons.IconSize;
import swidget.icons.StockIcon;



public class Swidget
{

	private static SplashScreen splashWindow;
	
	private static Semaphore initWaiter = new Semaphore(1);
	public static void initializeAndWait() {
		try {
			initWaiter.acquire();
			initialize(() -> {
				initWaiter.release();
			});
			initWaiter.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void initialize(Runnable startupTasks)
	{
		initialize(null, null, startupTasks);
	}
	
	
	public static void initialize(String splashBackground, String splashIcon, Runnable startupTasks)
	{
		
		//Needed to work around https://bugs.openjdk.java.net/browse/JDK-8130400
		//NEED TO SET THESE RIGHT AT THE START BEFORE ANY AWT/SWING STUFF HAPPENS.
		//THAT INCLUDES CREATING ANY ImageIcon DATA FOR SPLASH SCREEN
		System.setProperty("sun.java2d.xrender", "false");
		System.setProperty("sun.java2d.pmoffscreen", "false");
		
				
		if (splashBackground != null && splashIcon != null) {
			SwingUtilities.invokeLater(() -> {
				splashWindow = new SplashScreen(IconFactory.getImageIcon(splashBackground), IconFactory.getImage(splashIcon));
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



