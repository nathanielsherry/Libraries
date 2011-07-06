package commonenvironment.zipfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZippedFile{

	private ZipEntry entry;
	private ZipFile file;
	
	private boolean isDummy;
	private String dummyName;
	
	public ZippedFile(String name)
	{
		isDummy = true;
		dummyName = name;
	}
	
	
	public ZippedFile(ZipEntry entry, ZipFile file) {
		this.entry = entry;
		this.file = file;
	}
	
	public ZipEntry getEntry(){
		return entry;
	}
	
	public InputStream getInputStream()
	{
		try {
			return file.getInputStream(entry);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public BufferedReader getBufferedReader()
	{
		InputStream is = getInputStream();
		if (is == null) return null;
		return new BufferedReader(new InputStreamReader(is));
	}
	
	public String getName()
	{
		if (isDummy) return dummyName;
		return entry.getName();
	}
	
	public boolean isDirectory()
	{
		if (isDummy) return true;
		return entry.isDirectory();
	}
	
	public String toString()
	{
		return getName();
	}
	
	public boolean isDummy()
	{
		return isDummy;
	}
	
}
