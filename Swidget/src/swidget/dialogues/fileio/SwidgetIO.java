package swidget.dialogues.fileio;


import java.awt.Container;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import commonenvironment.AbstractFile;
import commonenvironment.Env;

import fava.functionable.FList;
import fava.signatures.FnMap;



public class SwidgetIO
{

	public static List<AbstractFile> openFiles(
			Container	parent, 
			String 		title, 
			String[][] 	exts, 
			String[] 	extDesc,
			String 		startDir)
	{
		return openFiles(parent, title, exts, extDesc, startDir, false);
	}
	
	public static List<AbstractFile> openFiles(
			Container	parent, 
			String 		title, 
			String[][] 	exts, 
			String[] 	extDesc,
			String 		startDir,
			boolean		allowFolders)
	{

		if (Env.isWebStart())
		{

			Set<String> allExts = new HashSet<String>();
			for (String extset[] : exts)
			{
				for (String ext : extset)
				{
					allExts.add(ext);
				}
			}
			
			try
			{
				return wsOpenFiles("~/", allExts.toArray(new String[]{}));
			}
			catch (UnavailableServiceException e)
			{
				JOptionPane.showMessageDialog(
						parent,
						"The Web Start File-Read Service is not Available.",
						"Read Failed.",
						JOptionPane.ERROR_MESSAGE,
						StockIcon.BADGE_WARNING.toImageIcon(IconSize.ICON));
				return null;
			}

		}
		else
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
			
			/*
			SimpleFileFilter filter = new SimpleFileFilter();
			for (String ext : exts)
			{
				filter.addExtension(ext);
			}
			filter.setDescription(extDesc);
			chooser.setFileFilter(filter);
			*/

			int returnVal = chooser.showOpenDialog(parent);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				return new FList<File>(chooser.getSelectedFiles()).map(new FnMap<File, AbstractFile>() {

					public AbstractFile f(File f)
					{
						return new AbstractFile(f.toString());
					}
				});

			}
			else
			{
				return null;
			}

		}

	}

	public static AbstractFile openFile(
			Container	parent, 
			String 		title, 
			String[][] 	exts, 
			String[] 	extDesc, 
			String 		startDir
		)
	{
		return openFile(parent, title, exts, extDesc, startDir, false);
	}
	
	public static AbstractFile openFile(
			Container	parent, 
			String 		title, 
			String[][] 	exts, 
			String[] 	extDesc, 
			String 		startDir,
			boolean		allowFolders
		)
	{

		if (Env.isWebStart())
		{

			Set<String> allExts = new HashSet<String>();
			for (String extset[] : exts)
			{
				for (String ext : extset)
				{
					allExts.add(ext);
				}
			}
			
			try
			{
				return wsOpenFile("~/", allExts.toArray(new String[]{}));
			}
			catch (UnavailableServiceException e)
			{
				JOptionPane.showMessageDialog(
						parent,
						"The Web Start File-Read Service is not Available.",
						"Read Failed.",
						JOptionPane.ERROR_MESSAGE,
						StockIcon.BADGE_WARNING.toImageIcon(IconSize.ICON));
				return null;
			}

		}
		else
		{

			JFileChooser chooser = new JFileChooser(startDir);
			chooser.setMultiSelectionEnabled(true);
			chooser.setDialogTitle(title);
			if (allowFolders) chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			
			/*
			if (exts.length > 0) 
			{
				SimpleFileFilter filter = new SimpleFileFilter();
				for (String ext : exts)
				{
					filter.addExtension(ext);
				}
				filter.setDescription(extDesc);
				chooser.setFileFilter(filter);
			}
			*/
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
				return new AbstractFile(chooser.getSelectedFile().toString());
			}
			else
			{
				return null;
			}

		}


	}


	public static String saveFile(Container parent, String title, String ext, String extDesc, String startDir,
			ByteArrayOutputStream outStream) throws IOException
	{
		outStream.close();
		InputStream inStream = new ByteArrayInputStream(outStream.toByteArray());
		String result = saveFile(parent, title, ext, extDesc, startDir, inStream);
		inStream.close();
		return result;
	}

	
	public static String saveFile(Container parent, String title, String ext, String extDesc, String startDir,
			InputStream inStream) throws IOException
	{
		

		if (Env.isWebStart())
		{

			try
			{
				wsSaveFile("~/", "", new String[] { ext }, inStream);
			}
			catch (UnavailableServiceException e)
			{
				JOptionPane.showMessageDialog(
						parent,
						"The Web Start File-Write Service is not Available.",
						"Write Failed.",
						JOptionPane.ERROR_MESSAGE,
						StockIcon.BADGE_WARNING.toImageIcon(IconSize.ICON));
				return null;
			}
			return "";

		}
		else
		{

			String saveFilename = SimpleIODialogues.chooseFileSave(parent, title, startDir, ext, extDesc);

			if (saveFilename != null)
			{
				File saveFile = new File(saveFilename);
				String savePictureFolder = saveFile.getParent();
				FileOutputStream fos = new FileOutputStream(saveFile);
				IOUtils.copy(inStream, fos);
				fos.flush();
				fos.close();

				return savePictureFolder;
			}

			return "";

		}


	}
	
	
	
	
	private static List<AbstractFile> wsOpenFiles(String path, String[] extensions) throws UnavailableServiceException
	{
		FileOpenService fos;
		try
		{
			fos = (FileOpenService) ServiceManager.lookup("javax.jnlp.FileOpenService");
			
			return new FList<FileContents>(fos.openMultiFileDialog(path, extensions)).map(new FnMap<FileContents, AbstractFile>(){

				
				public AbstractFile f(FileContents element)
				{
					return new AbstractFile(element);
				}});
			
		}
		catch (IOException e)
		{
			return null;
		}
	
	}
	
	private static AbstractFile wsOpenFile(String path, String[] extensions) throws UnavailableServiceException
	{
		FileOpenService fos;
		try
		{
			fos = (FileOpenService) ServiceManager.lookup("javax.jnlp.FileOpenService");
			
			return new AbstractFile(  fos.openFileDialog(path, extensions)  );			
		}
		catch (IOException e)
		{
			return null;
		}
	
	}
	

	private static FileContents wsSaveFile(String path, String name, String[] extensions, InputStream inputStream) throws UnavailableServiceException
	{
		FileSaveService fss;

		try
		{
			
			fss = (FileSaveService) ServiceManager.lookup("javax.jnlp.FileSaveService");
			return fss.saveFileDialog(  path, extensions, inputStream, name  );
			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}

}
