package swidget;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import swidget.widgets.Spacing;

import commonenvironment.Env;


public class Swidget
{

	public static void initialize()
	{
		
		System.getProperties().setProperty("swing.aatext", "true");
		boolean isNimbus = false;
		//if this version of the JVM is new enough to support the Nimbus Look and Feel, use it
		try
		{
			if (! Env.isMac() && !Env.isWindows()){
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				isNimbus = true;
			}
			
			if (Env.isMac() || Env.isWindows()) {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		}
		catch (Exception e)
		{
		}
		
		
		//Java 5 doesn't seem to resize windows properly on linux (at least not with compiz)
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		
		
		
	}
	
	public static boolean isNimbus()
	{
		
		try
		{
			Class<?> laf = UIManager.getLookAndFeel().getClass();
			Class<?> nimbusLaf = ClassLoader.getSystemClassLoader().loadClass("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			return (laf == nimbusLaf);	
		}
		catch (Exception e)
		{
			return false;
		}
		
	}
	
	
	
}
