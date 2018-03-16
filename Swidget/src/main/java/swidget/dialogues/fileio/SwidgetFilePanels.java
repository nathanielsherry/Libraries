package swidget.dialogues.fileio;

import java.awt.Component;
import java.awt.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import swidget.widgets.tabbedinterface.TabbedInterfacePanel;

public class SwidgetFilePanels {

	private static void showChooser(Component parent, JFileChooser chooser, Runnable onAccept, Runnable onCancel) {
		if (parent instanceof TabbedInterfacePanel) {
			TabbedInterfacePanel tabPanel = (TabbedInterfacePanel) parent;
			tabPanel.pushModalComponent(chooser);
			chooser.addActionListener(e -> {
				 if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
					 tabPanel.popModalComponent();
					 onAccept.run();
				 }
				 
				 if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
					 tabPanel.popModalComponent();
					 onCancel.run();
				 }
				 
			});
		} else {
			int result = chooser.showSaveDialog(parent);
			if (result == JFileChooser.APPROVE_OPTION) {
				onAccept.run();
			} else if (result == JFileChooser.ERROR_OPTION || result == JFileChooser.CANCEL_OPTION) {
				onCancel.run();
			}
		}	
	}
	
	public static void saveFile(Component parent, String title, File startingFolder, SimpleFileExtension extension, Consumer<Optional<File>> callback)
	{

		JFileChooser chooser = new JFileChooser(startingFolder);
		chooser.setMultiSelectionEnabled(false);
		chooser.setDialogTitle(title);
		chooser.setFileFilter(extension.getFilter());
		chooser.setApproveButtonText("Save");
		chooser.setApproveButtonToolTipText("Save Selected File");
		
				
		//Run this when the dialog is accepted
		Runnable onAccept = () -> {
			String filename = chooser.getSelectedFile().toString();
			if (!extension.match(filename)) {
				filename += "." + extension.getExtensions().get(0);
			}
			File file = new File(filename);
			
			if (warnFileExists(parent, file)) {
				callback.accept(Optional.of(file));
			} else {
				callback.accept(Optional.empty());
			}
		};
		
		Runnable onCancel = () -> {
			callback.accept(Optional.empty());
		};
		
		showChooser(parent, chooser, onAccept, onCancel);
		

	}
	
	
	
	
	private static JFileChooser getOpener(String title, File startingFolder, List<SimpleFileExtension> extensions) {
		JFileChooser chooser = new JFileChooser(startingFolder);
		chooser.setDialogTitle(title);
		
		chooser.setApproveButtonText("Open");
		chooser.setApproveButtonToolTipText("Open Selected File(s)");
		
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
	
	
	
	public static void openFile(Component parent, String title, File startingFolder, SimpleFileExtension extension, Consumer<Optional<File>> callback) {
		openFile(parent, title, startingFolder, Collections.singletonList(extension), callback);
	}
	
	public static void openFile(Component parent, String title, File startingFolder, List<SimpleFileExtension> extensions, Consumer<Optional<File>> callback)
	{
		JFileChooser chooser = getOpener(title, startingFolder, extensions);
		chooser.setMultiSelectionEnabled(false);


		//Run this when the dialog is accepted
		Runnable onAccept = () -> {
			File file = chooser.getSelectedFile();
			callback.accept(Optional.of(file));
		};
		
		Runnable onCancel = () -> {
			callback.accept(Optional.empty());
		};
		
		
		showChooser(parent, chooser, onAccept, onCancel);
		
	}
	
	
	
	public static void openFiles(Component parent, String title, File startingFolder, SimpleFileExtension extension, Consumer<Optional<List<File>>> callback) {
		openFiles(parent, title, startingFolder, Collections.singletonList(extension), callback);
	}
	
	public static void openFiles(Component parent, String title, File startingFolder, List<SimpleFileExtension> extensions, Consumer<Optional<List<File>>> callback)
	{
		JFileChooser chooser = getOpener(title, startingFolder, extensions);
		chooser.setMultiSelectionEnabled(true);

		//Run this when the dialog is accepted
		Runnable onAccept = () -> {
			List<File> files = Arrays.asList(chooser.getSelectedFiles());
			callback.accept(Optional.of(files));
		};
		
		Runnable onCancel = () -> {
			callback.accept(Optional.empty());
		};
		
		showChooser(parent, chooser, onAccept, onCancel);
		
	}
	
	
	
	static boolean warnFileExists(Component parent, File filename)
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
