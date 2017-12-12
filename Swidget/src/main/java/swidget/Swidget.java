package swidget;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import commonenvironment.Env;


public class Swidget
{

	public static void initialize()
	{
		initialize(false);
	}
	
	public static void initialize(boolean forceNativeLAF)
	{
		
		System.getProperties().setProperty("swing.aatext", "true");
		//if this version of the JVM is new enough to support the Nimbus Look and Feel, use it
		try
		{
			if (! Env.isMac() && !Env.isWindows() && !forceNativeLAF){
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			} else {
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
	
	
	public static void main(String[] args)
	{
		
	}
	
	
}
