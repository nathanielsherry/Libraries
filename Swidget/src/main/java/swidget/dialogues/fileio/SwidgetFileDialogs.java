package swidget.dialogues.fileio;


import java.awt.Component;
import java.awt.Window;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;



public class SwidgetFileDialogs
{
	
	public static File saveFile(Component parent, String title, File startingFolder, SimpleFileExtension extension)
	{

		JFileChooser chooser = new JFileChooser(startingFolder);
		chooser.setMultiSelectionEnabled(false);
		

		chooser.setDialogTitle(title);
		chooser.setFileFilter(extension.getFilter());

		int returnVal = chooser.showSaveDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			String filename = chooser.getSelectedFile().toString();
			if (!extension.match(filename)) {
				filename += "." + extension.getExtensions().get(0);
			}
			File file = new File(filename);

			if (warnFileExists(parent, file)) return file;

		}

		return null;
		
	}


	private static JFileChooser getOpener(String title, File startingFolder, List<SimpleFileExtension> extensions) {
		JFileChooser chooser = new JFileChooser(startingFolder);
		chooser.setDialogTitle(title);
		
		//First all an All Formats filter
		if (extensions.size() > 1) {
			SimpleFileExtension all = new SimpleFileExtension("All Supported Formats");
			for (SimpleFileExtension extension : extensions) {
				all.exts.addAll(extension.getExtensions());
			}
			chooser.addChoosableFileFilter(all.getFilter());
		}
		//Then add all the extensions one at a time
		for (SimpleFileExtension extension : extensions) {
			chooser.addChoosableFileFilter(extension.getFilter());
		}
		//Then set All Formats (or the only format) option as default.
		//There will be an "All Files" option at [0]
		chooser.setFileFilter(chooser.getChoosableFileFilters()[1]);
		
		return chooser;
	}
	
	public static File openFile(Window parent, String title, File startingFolder, SimpleFileExtension extension) {
		return openFile(parent, title, startingFolder, Collections.singletonList(extension));
	}
	
	public static File openFile(Window parent, String title, File startingFolder, List<SimpleFileExtension> extensions)
	{
		JFileChooser chooser = getOpener(title, startingFolder, extensions);
		chooser.setMultiSelectionEnabled(false);

		int returnVal = chooser.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}

		return null;
		
	}
	
	public static List<File> openFiles(Window parent, String title, File startingFolder, SimpleFileExtension extension) {
		return openFiles(parent, title, startingFolder, Collections.singletonList(extension));
	}
	
	public static List<File> openFiles(Window parent, String title, File startingFolder, List<SimpleFileExtension> extensions)
	{
		JFileChooser chooser = getOpener(title, startingFolder, extensions);
		chooser.setMultiSelectionEnabled(true);

		int returnVal = chooser.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return Arrays.asList(chooser.getSelectedFiles());
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
