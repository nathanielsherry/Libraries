package commonenvironment;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;







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
		
		List<String> parts = new ArrayList<String>(Arrays.asList(new File(filename).getName().split("\\.")));
		parts = parts.subList(0, parts.size() - 1);
		if (parts.size() == 0) { return ""; }
		return parts.stream().reduce((a, b) -> a + "." + b).get();
	}
	
	public static String getFileExt(String filename)
	{
		List<String> parts = new ArrayList<String>(Arrays.asList(new File(filename).getName().split("\\.")));
		return parts.get(parts.size() - 1);
	}
	
    
	
}
