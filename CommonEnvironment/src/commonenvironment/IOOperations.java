package commonenvironment;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import fava.Functions;
import fava.functionable.FList;
import fava.functionable.FStringInput;







/**
 * This class provides convenience methods for dealing with fileIO
 * 
 * @author Nathaniel Sherry, 2009
 */

public class IOOperations
{

	
	/**
	 * Examines the list of filenames, and determines the largest prefix which all filenames share.
	 * 
	 * @param filenames
	 * @return the common portion of the given filenames
	 */
	public static String getCommonFileName(List<String> filenames)
	{

		String name = getFileNameLessExt(filenames.get(0));
		String fileStart;

		for (String filename : filenames)
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
	public static String getFileNameLessExt(String filename)
	{
		
		FList<String> parts = new FList<String>(new File(filename).getName().split("\\."));
		parts = parts.take(parts.size() - 1);
		return parts.fold(Functions.strcat("."));
	}
	
	public static String getFileExt(String filename)
	{
		FList<String> parts = new FList<String>(new File(filename).getName().split("\\."));
		return parts.last();
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

		text = FStringInput.contents(br);


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
