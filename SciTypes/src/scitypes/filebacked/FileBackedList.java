package scitypes.filebacked;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import scitypes.Spectrum;

import fava.functionable.FList;


/**
 * TempFileList is an implementation of the List interface which writes out values to a temporary file, rather than store elements in memory. This means that get operations are idempotent when there are no intervening set operations, even if the accessed elements are modified externally 
 * @author Nathaniel Sherry, 2010
 *
 */

public class FileBackedList<T extends Serializable> extends AbstractFileBackedList<T>
{


	public static void main(String args[])
	{
		Spectrum s1 = new Spectrum(10000, 4);
		Spectrum s2 = new Spectrum(5000, 3);
		Spectrum s3 = new Spectrum(20000, 2);
		Spectrum s4 = new Spectrum(20000, 7);
		Spectrum s5 = new Spectrum(9000, 20);
		
		try {
			
			FileBackedList<Spectrum> list = new FileBackedList<Spectrum>("test");
			
			list.add(s1);
			list.add(s5);
			list.add(s2);
			
			list.set(0, s3);
			list.set(2, s4);
			
			list.add(s5);
			list.add(s4);
			
			System.out.println(list.get(0).show());
			System.out.println(list.get(2).show());
			System.out.println(list.get(3).show());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static <T extends Serializable> List<T> create(String name)
	{
		try
		{
			return new FileBackedList<T>(name);
		}
		catch (IOException e)
		{
			//FList can also behave sparsely
			e.printStackTrace();
			return new FList<T>();
		}
	}
	
	
	
	/**
	 * Createa new TempFileList
	 * @param name the name prefix to give the temporary file
	 * @param encode a function which can take an element T, and serialize it for output to the temporary file
	 * @param decode a function which can take a serialized representation of T and deserialize it into an element T
	 * @throws IOException
	 */
	public FileBackedList(String name) throws IOException
	{
		super(name);				
	}
	
	public FileBackedList(File file) throws IOException
	{
		super(file);
	}

	
	protected FileBackedList()
	{
	}
	
	@Override
	protected byte[] encodeObject(T element) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		oos = new ObjectOutputStream(baos);
		oos.writeObject(element);
		return baos.toByteArray();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected T decodeObject(byte[] byteArray) throws IOException
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
		ObjectInputStream ois = new ObjectInputStream(bais);
		try {
			return (T)ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		FileBackedList<T> sublist = new FileBackedList<T>();
		makeSublist(sublist, fromIndex, toIndex);
		return sublist;
	}

	

}
