package scitypes.filebacked;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import scitypes.Spectrum;

import com.sun.org.apache.bcel.internal.util.ByteSequence;

import fava.datatypes.Pair;
import fava.lists.FList;

public class CompressedFileBackedList<T extends Serializable> extends FileBackedList<T> {


	
	

	public static void main(String args[])
	{
		Spectrum s1 = new Spectrum(10000, 4);
		Spectrum s2 = new Spectrum(5000, 3);
		Spectrum s3 = new Spectrum(20000, 2);
		Spectrum s4 = new Spectrum(20000, 7);
		Spectrum s5 = new Spectrum(9000, 20);
		
		try {
			
			CompressedFileBackedList<Spectrum> list = new CompressedFileBackedList<Spectrum>("test");
			
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
			return new CompressedFileBackedList<T>(name);
		}
		catch (IOException e)
		{
			//FList can also behave sparsely
			e.printStackTrace();
			return new FList<T>();
		}
	}
	
	
	public CompressedFileBackedList(String name) throws IOException {
		super(name);
	}
	protected CompressedFileBackedList() {
	}
	
	@Override
	protected T decodeObject(byte[] byteArray) throws IOException {

		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
		InflaterInputStream iin = new InflaterInputStream(bais);
		ByteArrayOutputStream bout = new ByteArrayOutputStream(512);
		
		int b;  
		while ((b = iin.read()) != -1) {
			bout.write(b);
		}
		iin.close();
		
		return super.decodeObject(  bout.toByteArray()  );
		
	}

	@Override
	protected byte[] encodeObject(T element) throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		Deflater d = new Deflater();
		DeflaterOutputStream dout = new DeflaterOutputStream(baos, d);
		dout.write(super.encodeObject(element));
		dout.close();
		
		return baos.toByteArray();
		
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		CompressedFileBackedList<T> sublist = new CompressedFileBackedList<T>();
		makeSublist(sublist, fromIndex, toIndex);
		return sublist;
	}

}
