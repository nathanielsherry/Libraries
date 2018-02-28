package swidget.dialogues.fileio;


import java.awt.Component;
import java.awt.Window;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;



public class SimpleIODialogues
{
	
	public static String chooseFileSave(Component parent, String title, File startingFolder, String fileExtention, String extDescription)
	{

		JFileChooser chooser = new JFileChooser(startingFolder);
		chooser.setMultiSelectionEnabled(false);
		

		chooser.setDialogTitle(title);
		chooser.setFileFilter(new SimpleFileFilter(fileExtention, extDescription));

		int returnVal = chooser.showSaveDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			String filename = chooser.getSelectedFile().toString();
			if (!filename.toLowerCase().endsWith("." + fileExtention.toLowerCase())) {
				filename += "." + fileExtention;
			}
			File file = new File(filename);

			if (warnFileExists(parent, file)) return filename;

		}

		return null;
		
	}


	public static String chooseFileOpen(Window parent, String title, File startingFolder, String fileExtention,
			String fileDescription)
	{
		JFileChooser chooser = new JFileChooser(startingFolder);
		chooser.setMultiSelectionEnabled(false);


		chooser.setDialogTitle(title);
		chooser.setFileFilter(new SimpleFileFilter(fileExtention, fileDescription));

		int returnVal = chooser.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			String filename = chooser.getSelectedFile().toString();
			if (!filename.toLowerCase().endsWith("." + fileExtention.toLowerCase())) {
				filename += "." + fileExtention;
			}

			return filename;

		}

		return null;
		
	}


	private static boolean warnFileExists(Component parent, File filename)
	{
		
		if (filename.exists()) {
			
			int response = JOptionPane.showConfirmDialog(parent,
					"The file you have selected already exists, are you sure you want to replace it?",
					"File Already Exists", JOptionPane.YES_NO_OPTION
					);
	
			if (response == JOptionPane.YES_OPTION) return true;
			return false;
		}
		return true;
		

	}

}
