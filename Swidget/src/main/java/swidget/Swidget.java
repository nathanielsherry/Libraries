package swidget;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.nio.file.FileSystemLoopException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.Painter;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import commonenvironment.Env;
import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import swidget.stratus.StratusLookAndFeel;



public class Swidget
{

	
	
	public static void initialize()
	{
		initialize(false);
	}
	
	
	public static void initialize(boolean forceNativeLAF)
	{

		System.setProperty("swing.aatext", "true");
		//System.setProperty("awt.useSystemAAFontSettings", "gasp");
		
		
		try {
			if (!Env.isMac() && !Env.isWindows() && !forceNativeLAF) {

				UIManager.setLookAndFeel(new StratusLookAndFeel());

			} else {
				//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				UIManager.setLookAndFeel(new StratusLookAndFeel());
			}
		} catch (Exception e) {
		}
		
		//Java 5 doesn't seem to resize windows properly on linux (at least not with compiz)
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		
		
		
	}
	
	public static boolean isStratus()
	{
		
		try
		{
			return UIManager.getLookAndFeel().getClass() == StratusLookAndFeel.class;
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



