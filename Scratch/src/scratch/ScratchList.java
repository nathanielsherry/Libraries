package scratch;

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
 * ScratchList is an implementation of the List interface which writes 
 * out values to a temporary file, rather than store elements in memory. 
 * This means that get operations are idempotent when there are no 
 * intervening set operations, even if the accessed elements are 
 * modified externally.
 * @author Nathaniel Sherry, 2010
 *
 */

public class ScratchList<T extends Serializable> extends AbstractScratchList<T>
{


	public static void main(String args[])
	{
		Spectrum s1 = new Spectrum(10, 1);
		Spectrum s2 = new Spectrum(5, 2);
		Spectrum s3 = new Spectrum(20, 3);
		Spectrum s4 = new Spectrum(20, 4);
		Spectrum s5 = new Spectrum(9, 5);
		
		try {
			
			ScratchList<Spectrum> list = new ScratchList<Spectrum>("test");
			
			list.add(s1);
			list.add(s5);
			list.add(s2);
			
			list.set(0, s3);
			list.set(2, s4);
			
			list.add(s5);
			list.add(s4);
			
			list.remove(s3);
			//list.add(s3);
			
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
			return new ScratchList<T>(name);
		}
		catch (IOException e)
		{
			//FList can also behave sparsely
			e.printStackTrace();
			return new FList<T>();
		}
	}
	
	
	
	/**
	 * Create a new ScratchList
	 * @param name the name prefix to give the temporary file
	 * @throws IOException
	 */
	public ScratchList(String name) throws IOException
	{
		super(name);
	}
	
	public ScratchList(File file) throws IOException
	{
		super(file);
	}

	
	protected ScratchList()
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
		ScratchList<T> sublist = new ScratchList<T>();
		makeSublist(sublist, fromIndex, toIndex);
		return sublist;
	}

	

}
