package commonenvironment;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.jnlp.FileContents;

import commonenvironment.zipfs.ZippedFile;

//TODO: remake this using subclassing


public class AbstractFile implements Comparable<AbstractFile>
{

	private static AlphaNumericComparitor comparator = new AlphaNumericComparitor();
	
	public enum ReadType
	{
		STRING, FILE_CONTENTS, URL, ZIP
	}

	private ReadType	type;
	private Object		contents;


	public AbstractFile(String filename)
	{
		type = ReadType.STRING;
		contents = filename;
	}

	public AbstractFile(File file)
	{
		type = ReadType.STRING;
		contents = file.getAbsolutePath();
	}
	

	public AbstractFile(FileContents filecontents)
	{
		type = ReadType.FILE_CONTENTS;
		contents = filecontents;
	}
	
	public AbstractFile(URL url)
	{
		type = ReadType.URL;
		contents = url;
	}
	
	public AbstractFile(ZippedFile file) {
		type = ReadType.ZIP;
		contents = file;
	}
	

	public BufferedReader getReader()
	{
		InputStream is;
		try {
			is = getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return new BufferedReader(new InputStreamReader(is));

	}


	public InputStream getInputStream() throws IOException
	{
			
		switch (type) {
		
			case STRING:
				
				try
				{
					return new FileInputStream((String) contents);
				}
				catch (FileNotFoundException e)
				{
					throw new IOException();
				}
				
			case FILE_CONTENTS:
				
				return ((FileContents) contents).getInputStream();
				
			case URL:
				
				return ((URL)contents).openStream();
				
			case ZIP:
				
				return ((ZippedFile)contents).getInputStream();
		
		
		}
		
		return null;
	}


	public String getFileName()
	{
		if (type == ReadType.STRING)
		{
			return (String) contents;
		}
		else if (type == ReadType.FILE_CONTENTS)
		{
			try
			{
				return ((FileContents) contents).getName();
			}
			catch (IOException e)
			{
				return "";
			}
		}
		else if (type == ReadType.URL)
		{
			return ((URL)contents).getPath();
		}

		return "";

		
	}
	
	public long getFileSize()
	{
		if (type == ReadType.STRING)
		{
			return new File( (String) contents ).length();
		}
		else if (type == ReadType.FILE_CONTENTS)
		{
			try
			{
				return ((FileContents) contents).getLength();
			}
			catch (IOException e)
			{
				return 0;
			}
		}

		return 0;
	}

	public int compareTo(AbstractFile other) {
		return comparator.compare(this.getFileName(), other.getFileName());
	}
	

}
