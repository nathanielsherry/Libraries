package commonenvironment;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.jnlp.FileContents;
import javax.jnlp.FileOpenService;
import javax.jnlp.FileSaveService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

import fava.Fn;
import fava.Functions;
import fava.functionable.FList;
import fava.signatures.FnEach;
import fava.signatures.FnFold;
import fava.signatures.FnMap;







/**
 * This class provides convenience methods for dealing with fileIO
 * 
 * @author Nathaniel Sherry, 2009
 */

public class IOOperations
{

	/**
	 * Given a file name, it will return a String representation of the contents of the file
	 * 
	 * @param filename
	 * @return the contents of filename
	 */
	public static String fileToString(String filename)
	{

		try
		{
			BufferedReader in = new BufferedReader(new FileReader(filename));

			String readString = readerToString(in);

			in.close();

			return readString;

		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}

	}
	
	
	/**
	 * Given a file name, it will return a String representation of the contents of the file
	 * 
	 * @param filename
	 * @return the contents of filename
	 */
	public static String fileToString(AbstractFile file)
	{
		return readerToString(file.getReader());
	}
	
	/**
	 * Given a {@link File}, it will return a String representation of the contents of the file
	 * 
	 * @param filename
	 * @return the contents of filename
	 */
	public static String fileToString(File file)
	{
		return fileToString(new AbstractFile(file));
	}
	

	/**
	 * Given a BufferedReader, it will return a String representation of the contents of the Reader, and will close the BufferedReader
	 * 
	 * @param reader
	 * @return the contents of the BufferedReader
	 */
	public static String readerToString(BufferedReader reader)
	{
		
		final StringBuilder file = new StringBuilder();
		fileEachLine(reader, new FnEach<String>() {

			public void f(String line)
			{
				file.append(line + System.getProperty("line.separator"));
			}});
		
		return file.toString();
		
	}

	
	/**
	 * Given a file name, it will return a list of Strings representing the lines in the file
	 * 
	 * @param filename
	 * @return a list of strings representing the lines of filename
	 */
	public static List<String> fileToLines(String filename)
	{
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(filename));

			List<String> readString = readerToLines(in);

			in.close();

			return readString;

		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Given an {@link AbstractFile}, it will return a list of Strings representing the lines read from the file.
	 * 
	 * @param file
	 * @return the contents of the file
	 */
	public static FList<String> fileToLines(AbstractFile file)
	{
		return readerToLines(file.getReader());
	}
	
	
	/**
	 * Given a BufferedReader, it will return a list of Strings representing the lines read from the reader, and will close the BufferedReader
	 * 
	 * @param reader
	 * @return the contents of the BufferedReader
	 */
	public static FList<String> readerToLines(BufferedReader reader)
	{
		final FList<String> lines = new FList<String>();
		fileEachLine(reader, new FnEach<String>() {

			public void f(String line)
			{
				lines.add(line);
			}});
		
		return lines;
	}
	
	

	/**
	 * Given a filename, this method will call eachline once for each line read from the file
	 * @param filename the file to read
	 * @param eachline the function to call with each line
	 */
	public static void fileEachLine(String filename, FnEach<String> eachline)
	{

		fileEachLine(new AbstractFile(filename), eachline);
		
	}
	
	
	/**
	 * Given an {@link AbstractFile}, this method will call eachline once for each line read from the file
	 * @param file the AbstractFile to read from
	 * @param eachline the function to call with each line
	 */
	public static void fileEachLine(AbstractFile file, FnEach<String> eachline)
	{
		fileEachLine(file.getReader(), eachline);
	}
	
	/**
	 * Given a {@link BufferedReader}, this method will call eachline once for each line read from the reader
	 * @param reader the reader to read from
	 * @param eachline the function to call with each line
	 */
	public static void fileEachLine(BufferedReader reader, FnEach<String> eachline)
	{
		if (reader == null) return;
		String line;

		try
		{

			while (true)
			{

				line = reader.readLine();
				if (line == null) break;
				eachline.f(line);
			}

		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		try	{ reader.close(); } catch (IOException e) {}
		
	}
	
	
	

	/**
	 * Given a filename and a list of type T1, this will write the output of calling toString()
	 * on each element in the list
	 * @param <T1> the type of elements in the list
	 * @param filename the name of the file to write to
	 * @param list the list to write
	 */
	public static <T1> void listToFile(String filename, List<T1> list)
	{
		StringBuilder stringbuilder = new StringBuilder();

		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));

			out.write(Fn.foldr(list, stringbuilder, new FnFold<T1, StringBuilder>() {

				
				public StringBuilder f(T1 varT1, StringBuilder builder)
				{
					return builder.append(varT1.toString() + "\n");
				}
			}).toString());

			out.close();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	
	

	
	
	/**
	 * Counts the occurances of the given character in the given file
	 */
	public static int characterCount(AbstractFile file, char character) throws IOException 
	{
		
		InputStream is = file.getInputStream();
		byte[] c = new byte[1024];
		int count = 0;
		int readChars = 0;
		while ((readChars = is.read(c)) != -1) {
			for (int i = 0; i < readChars; ++i) {
				if (c[i] == character)
				++count;
			}
		}
		return count;
	}

	
	/**
	 * Sorts a list of filenames in alphanumeric order; "A2" will come before "A10"
	 * 
	 * @param filenames
	 */
	public static void sortAbstractFiles(List<AbstractFile> filenames)
	{

		Collections.sort(filenames);
		
		/*
		Fn.sortBy(filenames, new AlphaNumericComparitor(), new FunctionMap<AbstractFile, String>() {

			
			public String f(AbstractFile file)
			{
				return file.getFileName();
			}
		});
		*/

	}


	/**
	 * Sorts a list of filenames in alphanumeric order; "A2" will come before "A10"
	 * 
	 * @param filenames
	 */
	public static void sortFilenames(List<String> filenames)
	{

		Fn.sortBy(filenames, new AlphaNumericComparitor(), Functions.<String> id());

	}


	/**
	 * A standard way or retrieving the file path.
	 * 
	 * @param filename
	 * @return the path of filename
	 */
	public static String getFilePath(String filename)
	{
		
		List<String> parts = Fn.map(filename.split(separator()), Functions.<String> id());
		parts.remove(parts.size() - 1);
		return Fn.foldl(parts, Functions.strcat(separator())) + separator();

	}


	public static String separator()
	{
		String separator = File.separator;
		if ( "\\".equals(separator) ) separator = "\\\\";
		return separator;
	}
	
	/**
	 * A standard way or retrieving the file title.
	 * 
	 * @param filename
	 * @return the path of filename
	 */
	public static String getFileTitle(String filename)
	{
		
		String[] parts = filename.split(separator());
		return parts[parts.length - 1];

		// File f = new File(filename);
		// return f.getAbsolutePath();

	}


	/**
	 * A standard way or retrieving the file path.
	 * 
	 * @param filename
	 * @return the path of filename
	 */
	public static String getParentFolder(String filename)
	{

		
		
		String parts[] = filename.split(separator());
		if (parts.length == 1) return null;
		return parts[parts.length - 2];

		// File f = new File(filename);
		// return f.getAbsolutePath();

	}


	/**
	 * Examines the list of filenames, and determines the largest substring (starting at 0) which all filenames share.
	 * 
	 * @param filenames
	 * @return the common portion of the given filenames
	 */
	public static String getCommonFileName(List<String> filenames)
	{

		
		List<String> titles = Fn.map(filenames, new FnMap<String, String>() {

			
			public String f(String element)
			{
				return getFileTitle(element);
			}
		});
		
		

		String name = getFileNameLessExt(titles.get(0));
		String fileStart;

		for (String filename : titles)
		{
			while (true)
			{

				fileStart = getFileNameLessExt(filename).substring(0, name.length());
				if (name.equals(fileStart)) break;
				name = name.substring(0, name.length() - 1);

			}

		}

		return name;
	}


	/**
	 * Returns the file name with the file extension removed
	 * 
	 * @param name
	 * @return file name without extension
	 */
	private static String getFileNameLessExt(String name)
	{
		String[] parts = name.split("\\.", 2);
		return parts[0];
	}


	/**
	 * Checks to see if a file's extension is the same as the given one
	 * 
	 * @param filename
	 * @param extension
	 * @return true if the extensions match, false otherwise
	 */
	public static boolean checkFileExtension(String filename, String extension)
	{

		return filename.toLowerCase().endsWith(extension.toLowerCase());

	}
	
	public static List<AbstractFile> wsOpenFiles(String path, String[] extensions) throws UnavailableServiceException
	{
		FileOpenService fos;
		try
		{
			fos = (FileOpenService) ServiceManager.lookup("javax.jnlp.FileOpenService");
			
			return Fn.map(fos.openMultiFileDialog(path, extensions), new FnMap<FileContents, AbstractFile>(){

				
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
	
	public static AbstractFile wsOpenFile(String path, String[] extensions) throws UnavailableServiceException
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
	
	public static FileContents wsSaveFile(String path, String name, String[] extensions, InputStream inputStream) throws UnavailableServiceException
	{
		FileSaveService fos;

		try
		{
			
			fos = (FileSaveService) ServiceManager.lookup("javax.jnlp.FileSaveService");
			
			return fos.saveFileDialog(  path, extensions, inputStream, name  );
			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}
	
	/**
	 * Reads a file from inside the current jar file
	 * @param s the name of the file to read
	 * @return the contents of the file
	 */
    public static String readTextFromJar(String s)
	{
		InputStream is = null;
		BufferedReader br = null;
		String text = "";

		try {
			is = IOOperations.class.getResourceAsStream(s);
			br = new BufferedReader(new InputStreamReader(is));
		} catch (Exception e) {
			return "";
		}

		text = IOOperations.readerToString(br);


		return text;
	}
    
    public static URL getURLFromJar(String s)
    {
    	return IOOperations.class.getResource(s);
    }
    
    public static AbstractFile getFileFromJar(String s)
    {
    	return new AbstractFile(getURLFromJar(s));
    }
	
}
