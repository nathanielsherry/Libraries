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
import swidget.dialogues.fileio.JNativeFileChooser;
import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import swidget.stratus.StratusLookAndFeel;



public class Swidget
{

	
	
	public static void initialize()
	{
		initialize(false);
	}
	

	private static void uiset(String key, Object value) {
		UIManager.put(key, value);
	}
	
	
	public static void initialize(boolean forceNativeLAF)
	{

		System.setProperty("swing.aatext", "true");
		
		
		//Initialize native JavaFX File Selection Dialogs
		new JNativeFileChooser();

		try {
			if (!Env.isMac() && !Env.isWindows() && !forceNativeLAF) {

				UIManager.setLookAndFeel(new StratusLookAndFeel());

			} else {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		} catch (Exception e) {
		}
		
		//Java 5 doesn't seem to resize windows properly on linux (at least not with compiz)
		JFrame.setDefaultLookAndFeelDecorated(false);
		JDialog.setDefaultLookAndFeelDecorated(false);
		
		
		
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



