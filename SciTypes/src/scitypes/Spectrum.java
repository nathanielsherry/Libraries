package scitypes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fava.functionable.Functionable;
import fava.signatures.FnFold;
import fava.signatures.FnEach;
import fava.signatures.FnMap;

public class Spectrum extends Functionable<Float> implements Serializable
{

	private float	data[];
	private int		size;
	private int		maxIndex;



	/**
	 * Creates a new Spectrum with the given size
	 * @param size
	 */
	public Spectrum(int size)
	{
		this.data = new float[size];
		this.size = size;		
		maxIndex = 0;
	}
	
	/**
	 * Creates a new Spectrum of the given size, with all values set to the given value
	 * @param size
	 * @param initialize
	 */
	public Spectrum(int size, float initialize)
	{
		this.data = new float[size];
		this.size = size;
		maxIndex = 0;
		
		for (int i = 0; i < size; i++)
		{
			data[i] = initialize;
		}
		maxIndex = size - 1;
	}
	
	/**
	 * Creates a new Spectrum based on a copy of the given array
	 * @param fromArray
	 */
	public Spectrum(float[] fromArray)
	{
		this(fromArray, true);
	}
	
	/**
	 * Creates a new Spectrum based on the given array. If copy is 
	 * false, the Spectrum will be backed directly by the given array.
	 * @param fromArray
	 * @param copy
	 */
    public Spectrum(float[] fromArray, boolean copy)
    {
    	if(copy) {
    	
            this.data = new float[fromArray.length];
            this.size = fromArray.length;

            for (int i = 0; i < size; i++)
            {
                    data[i] = fromArray[i];
            }
            maxIndex = size - 1;
    	} else {
    		this.data = fromArray;
    		this.size = fromArray.length;
    		maxIndex = size - 1;
    	}
    }

    
	/**
	 * Creates a new Spectrum based on a copy of the given array.
	 * The values in the given array will be converted to floats.
	 * @param fromArray
	 */
	public Spectrum(double[] fromArray)
	{
        this.data = new float[fromArray.length];
        this.size = fromArray.length;

        for (int i = 0; i < size; i++)
        {
                data[i] = (float)fromArray[i];
        }
        maxIndex = size - 1;
	}

	/**
	 * Creates a new Spectrum based on the values in the given list.
	 * @param fromList
	 */
	public Spectrum(List<Float> fromList)
	{
		this.data = new float[fromList.size()];
		this.size = fromList.size();

		for (int i = 0; i < size; i++)
		{
			data[i] = fromList.get(i);
		}
		maxIndex = size - 1;

	}


	/**
	 * Creates a new spectrum copied from the given spectrum
	 * @param copy
	 */
	public Spectrum(Spectrum copy)
	{
		this.data = copy.toArray();
		this.size = copy.size;
		this.maxIndex = copy.maxIndex;		
	}
	
	
	/**
	 * Copies the values from the given spectrum into this one. 
	 * Values copied will be in the range of 0 .. min(size(), s.size())
	 * @param s
	 */
	public void copy(Spectrum s)
	{
		int maxindex;
		maxindex = Math.min(s.size(), size());
		
		for (int i = 0; i < maxindex; i++)
		{
			set(i, s.get(i));
		}
	}


	/**
	 * Adds a value to the Spectrum.  When a new spectrum is created 
	 * without being initialized with any values, it can have <tt>size</tt> 
	 * new values added to it. These values are added in order, just 
	 * as with {@link List#add(Object)}. The add method will return true
	 * if any new value was added to the spectrum, or false if there was
	 * no more space available. Calling {@link Spectrum#set(int, float)}
	 * does not have any effect on the add method
	 * @param value
	 */
	public boolean add(float value)
	{
		if (maxIndex < size - 1)
		{
			data[++maxIndex] = value;
			return true;
		}
		else
		{
			return false;
		}
	}


	/**
	 * Sets the value of the entry at index i.
	 * @param i
	 * @param value
	 */
	public void set(int i, float value)
	{
		data[i] = value;
	}


	/**
	 * Gets the value of the entry at index i
	 * @param i
	 */
	public float get(int i)
	{
		return data[i];
	}


	/**
	 * Gets the size of this Spectrum
	 */
	public int size()
	{
		return size;
	}


	/**
	 * Returns a copy of the data as an array
	 */
	public float[] toArray()
	{
		return Arrays.copyOf(data, data.length);
	}

	/**
	 * Returns a new Spectrum containing a copy of the data for a subsection of this spectrum.
	 * @param start
	 * @param stop
	 */
	public Spectrum subSpectrum(int start, int stop)
	{
		
		int length = stop - start + 1;
		Spectrum target = new Spectrum(length);
		System.arraycopy(data, start, target.data, 0, length);
		target.maxIndex = length-1;
		
		return target; 
	}
	
	/**
	 * Return the array which is backing this Spectrum. This method does not return a copy, 
	 * but the real array. Modifying the contents of this array will modify the contents 
	 * of the Spectrum.
	 */
	public float[] backingArray()
	{
		return data;
	}


	/**
	 * Returns an iterator over {@link Float} values for this Spectrum
	 */
	public Iterator<Float> iterator()
	{
		return new Iterator<Float>() {

			int	index	= 0;

			public boolean hasNext()
			{
				return (index < size);
			}


			public Float next()
			{
				return data[index++];
			}


			public void remove()
			{
				data[index] = 0.0f;
			}
		};
	}

	
	@Override
	public void each(FnEach<Float> f)
	{
		for (int i = 0; i < size; i++)
		{
			f.f(data[i]);
		}
	}
	
	public void map_i(FnMap<Float, Float> f)
	{
		for (int i = 0; i < size; i++)
		{
			set(i, f.f(data[i]));
		}
	}
	

	/*
	public Spectrum map(FnMap<Float, Float> f)
	{
		
		Spectrum newSpectrum = new Spectrum(size);
		for (int i = 0; i < size; i++)
		{
			newSpectrum.set(i, f.f(data[i]));
		}
		return newSpectrum;
		
	}
	*/
	
	
	
	@Override
	public Float fold(FnFold<Float, Float> f)
	{
		
		if (this.size == 0) return 0f;
		
		Float result = this.get(0);
		
		for (int i = 1; i < size; i++)
		{
			result = f.f(get(i), result);
		}
		
		return result;
		
	}
	
	@Override
	public <T2> T2 fold(T2 base, FnFold<Float, T2> f)
	{
		
		T2 result = base;
		
		if (this.size == 0) return result;
		
		for (int i = 0; i < size; i++)
		{
			result = f.f(get(i), result);
		}
		
		return result;
	}
	
	public Spectrum zipWith(Spectrum other, FnFold<Float, Float> f)
	{
		
		Spectrum newSpectrum = new Spectrum(size);
		for (int i = 0; i < size; i++)
		{
			newSpectrum.set(i, f.f(data[i], other.data[i]));
		}
		return newSpectrum;
		
	}

	public static FnMap<Spectrum, byte[]> getEncoder()
	{

		//Function to serialize a spectrum
		return  new FnMap<Spectrum, byte[]>()
		{

			public byte[] f(Spectrum s)
			{

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos;
				try
				{
					oos = new ObjectOutputStream(baos);
					oos.writeObject(s);
					oos.close();
					return baos.toByteArray();
				}
				catch (IOException e)
				{
					e.printStackTrace();
					return new byte[0];
				}

			}
		};
	}

	public static FnMap<byte[], Spectrum> getDecoder(){

		//Function to deserialize a spectrum

		return new FnMap<byte[], Spectrum>()
		{

			public Spectrum f(byte[] bs)
			{
				ByteArrayInputStream bais = new ByteArrayInputStream(bs);
				ObjectInputStream ois;
				try
				{
					ois = new ObjectInputStream(bais);
					Spectrum s = (Spectrum) ois.readObject();
					ois.close();
					return s;
				}
				catch (IOException e)
				{
					e.printStackTrace();
					return null;
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
					return null;
				}

			}
		};

	}
	
	@Override
	public boolean equals(Object oother)
	{
		if (oother instanceof Spectrum) {
			
			Spectrum other = (Spectrum)oother;
			
			if (other.size() != size()) return false;
			
			for (int i = 0; i < size(); i++) if (other.get(i) != get(i)) return false;
			
		} else {
			return false;
		}
		
		return true;
	}
	
	
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		boolean first = true;
				
		
		for (Float f : this)
		{
			if (first) {
				first = false;
			} else {
				sb.append(" ");
			}
			sb.append(f);
		}
				
		return sb.toString();
		
	}


}
