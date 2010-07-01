package scitypes;

import java.util.Iterator;

import fava.*;
import fava.signatures.FunctionCombine;
import fava.signatures.FunctionEach;
import fava.signatures.FunctionMap;

public class Spectrum extends Functionable<Float> implements Iterable<Float>
{

	private float	data[];
	private int		size;
	private int		maxIndex;


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


	public Spectrum(int size)
	{
		this(size, 0.0f);
	}
	
	public Spectrum(Spectrum copy)
	{
		this.data = copy.toArray();
		this.size = copy.size;
		this.maxIndex = copy.maxIndex;		
	}


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


	public void set(int i, float value)
	{
		data[i] = value;
	}


	public float get(int i)
	{
		return data[i];
	}


	public int size()
	{
		return size;
	}


	public float[] toArray()
	{
		return data.clone();
	}

	public Spectrum subSpectrum(int start, int stop)
	{
		
		int length = stop - start + 1;
		Spectrum target = new Spectrum(length);
		System.arraycopy(data, start, target.data, 0, length);
		target.maxIndex = length-1;
		
		return target; 
	}
	
	public float[] backingArray()
	{
		return data;
	}


	public Iterator<Float> iterator()
	{
		return new Iterator<Float>() {

			int	index	= 0;

			public boolean hasNext()
			{
				return (index < size-1);
			}


			public Float next()
			{
				return data[++index];
			}


			public void remove()
			{
				data[index] = 0.0f;
			}
		};
	}

	public void each(FunctionEach<Float> f)
	{
		for (int i = 0; i < size; i++)
		{
			f.f(data[i]);
		}
	}
	
	public Spectrum map(FunctionMap<Float, Float> f)
	{
		
		Spectrum newSpectrum = new Spectrum(size);
		for (int i = 0; i < size; i++)
		{
			newSpectrum.set(i, f.f(data[i]));
		}
		return newSpectrum;
		
	}
	
	public Float fold(FunctionCombine<Float, Float, Float> f)
	{
		
		if (this.size == 0) return 0f;
		
		Float result = this.get(0);
		
		for (int i = 0; i < size; i++)
		{
			result = f.f(get(i), result);
		}
		
		return result;
		
	}
	
	public <T2> T2 fold(T2 base, FunctionCombine<Float, T2, T2> f)
	{
		
		T2 result = base;
		
		if (this.size == 0) return result;
		
		for (int i = 0; i < size; i++)
		{
			result = f.f(get(i), result);
		}
		
		return result;
	}
	
	public Spectrum zipWith(Spectrum other, FunctionCombine<Float, Float, Float> f)
	{
		
		Spectrum newSpectrum = new Spectrum(size);
		for (int i = 0; i < size; i++)
		{
			newSpectrum.set(i, f.f(data[i], other.data[i]));
		}
		return newSpectrum;
		
	}


}
