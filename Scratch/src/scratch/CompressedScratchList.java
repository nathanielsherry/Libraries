package scratch;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import fava.functionable.FList;
import scitypes.ISpectrum;
import scitypes.Spectrum;

public class CompressedScratchList<T extends Serializable> extends ScratchList<T> {


	
	

	public static void main(String args[])
	{
		Spectrum s1 = new ISpectrum(10000, 4);
		Spectrum s2 = new ISpectrum(5000, 3);
		Spectrum s3 = new ISpectrum(20000, 2);
		Spectrum s4 = new ISpectrum(20000, 7);
		Spectrum s5 = new ISpectrum(9000, 20);
		
		try {
			
			CompressedScratchList<Spectrum> list = new CompressedScratchList<Spectrum>("test");
			
			list.add(s1);
			list.add(s5);
			list.add(s2);
			
			list.set(0, s3);
			list.set(2, s4);
			
			list.add(s5);
			list.add(s4);
			
			System.out.println(list.get(0).toString());
			System.out.println(list.get(2).toString());
			System.out.println(list.get(3).toString());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static <T extends Serializable> List<T> create(String name)
	{
		try
		{
			return new CompressedScratchList<T>(name);
		}
		catch (IOException e)
		{
			//FList can also behave sparsely
			e.printStackTrace();
			return new FList<T>();
		}
	}
	
	
	public CompressedScratchList(String name) throws IOException {
		super(name);
	}
	protected CompressedScratchList() {
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
		CompressedScratchList<T> sublist = new CompressedScratchList<T>();
		makeSublist(sublist, fromIndex, toIndex);
		return sublist;
	}

}
