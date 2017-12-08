package swidget.dialogues.fileio;


import java.awt.Container;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.jnlp.FileContents;
import javax.jnlp.FileOpenService;
import javax.jnlp.FileSaveService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.IOUtils;

import swidget.icons.IconSize;
import swidget.icons.StockIcon;

import commonenvironment.Env;


public class SwidgetIO
{

	public static List<File> openFiles(
			Container	parent, 
			String 		title, 
			String[][] 	exts, 
			String[] 	extDesc,
			File 		startDir)
	{
		return openFiles(parent, title, exts, extDesc, startDir, false);
	}
	
	public static List<File> openFiles(
			Container	parent, 
			String 		title, 
			String[][] 	exts, 
			String[] 	extDesc,
			File 		startDir,
			boolean		allowFolders)
	{

		JFileChooser chooser = new JFileChooser(startDir);
		FileFilter defaultFilter = chooser.getFileFilter();
		chooser.setMultiSelectionEnabled(true);
		chooser.setDialogTitle(title);
		if (allowFolders) chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		SimpleFileFilter filter;
		for (int i = 0; i < exts.length; i++)
		{
			filter = new SimpleFileFilter();
			for (String ext : exts[i])
			{
				filter.addExtension(ext);
			}
			if (extDesc.length >= i) filter.setDescription(extDesc[i]);
			
			chooser.addChoosableFileFilter(filter);
		}
		
		chooser.setFileFilter(defaultFilter);
		
		int returnVal = chooser.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			return Arrays.asList(chooser.getSelectedFiles());

		}
		else
		{
			return null;
		}


	}

	public static File openFile(
			Container	parent, 
			String 		title, 
			String[][] 	exts, 
			String[] 	extDesc, 
			File 		startDir
		)
	{
		return openFile(parent, title, exts, extDesc, startDir, false);
	}
	
	public static File openFile(
			Container	parent, 
			String 		title, 
			String[][] 	exts, 
			String[] 	extDesc, 
			File 		startDir,
			boolean		allowFolders
		)
	{

	
		JFileChooser chooser = new JFileChooser(startDir);
		chooser.setMultiSelectionEnabled(true);
		chooser.setDialogTitle(title);
		if (allowFolders) chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		SimpleFileFilter filter;
		for (int i = 0; i < exts.length; i++)
		{
			filter = new SimpleFileFilter();
			for (String ext : exts[i])
			{
				filter.addExtension(ext);
			}
			if (extDesc.length >= i) filter.setDescription(extDesc[i]);
			
			chooser.addChoosableFileFilter(filter);
		}

		int returnVal = chooser.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			return chooser.getSelectedFile();
		}
		else
		{
			return null;
		}


	}


	public static File saveFile(Container parent, String title, String ext, String extDesc, File startDir,
			ByteArrayOutputStream outStream) throws IOException
	{
		outStream.close();
		InputStream inStream = new ByteArrayInputStream(outStream.toByteArray());
		File result = saveFile(parent, title, ext, extDesc, startDir, inStream);
		inStream.close();
		return result;
	}

	
	public static File saveFile(Container parent, String title, String ext, String extDesc, File startDir,
			InputStream inStream) throws IOException
	{
		
		String saveFilename = SimpleIODialogues.chooseFileSave(parent, title, startDir, ext, extDesc);

		if (saveFilename != null)
		{
			File saveFile = new File(saveFilename);
			File savePictureFolder = saveFile.getParentFile();
			FileOutputStream fos = new FileOutputStream(saveFile);
			IOUtils.copy(inStream, fos);
			fos.flush();
			fos.close();

			return savePictureFolder;
		}

		return new File("/");

	}
	
	
	
	

}
