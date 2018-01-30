package swidget;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Toolkit;
import java.nio.file.FileSystemLoopException;
import java.util.Map;

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

import commonenvironment.Env;
import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import swidget.stratus.StratusLookAndFeel;
import swidget.stratus.components.StratusComboBoxUI;



public class Swidget
{

	
	
	public static void initialize()
	{
		initialize(false);
	}
	
	
	public static void initialize(boolean forceNativeLAF)
	{

		
		try {
			if (!Env.isMac() && !Env.isWindows() && !forceNativeLAF) {
				
				//StratusLookAndFeel.DISABLE_FONT_HINTING = false;
				UIManager.setLookAndFeel(new StratusLookAndFeel());
				//UIManager.setLookAndFeel(new NimbusLookAndFeel());

			} else {
				//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				UIManager.setLookAndFeel(new StratusLookAndFeel());
			}
		} catch (Exception e) {
		}
		
		//JFrame.setDefaultLookAndFeelDecorated(false);
		//JDialog.setDefaultLookAndFeelDecorated(false);
		

		
		
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



