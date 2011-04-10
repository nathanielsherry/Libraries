package fava.functionable;

import java.util.List;

import fava.Fn;
import fava.Functions;
import fava.signatures.FnCombine;
import fava.signatures.FnFold;
import fava.signatures.FnEach;
import fava.signatures.FnMap;

public abstract class Functionable<T1> implements Iterable<T1> {

	public void each(FnEach<T1> f)
	{
		Fn.each(this, f);
	}
	
	public <T2> Functionable<T2> map(FnMap<T1, T2> f)
	{
		return Fn.map(this, f);
	}
	
	public Functionable<T1> filter(FnMap<T1, Boolean> f)
	{
		return Fn.filter(this, f);
	}
	
	public T1 fold(FnFold<T1, T1> f)
	{
		return Fn.fold(this, f);
	}
	
	public <T2> T2 fold(T2 base, FnFold<T1, T2> f)
	{
		return Fn.fold(this, base, f);
	}
	
	public Functionable<T1> take(int number)
	{
		return Fn.take(this, number);
	}
	
	public Functionable<T1> takeWhile(FnMap<T1, Boolean>f)
	{
		return Fn.takeWhile(this, f);
	}
	
	public String show(String separator)
	{
		final StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		
		this.map(Functions.<T1>show()).each(new FnEach<String>() {

			public void f(String element) {
				sb.append(element);
				sb.append(",");
			}
		});
		
		
		return sb.substring(0, sb.length()-1) + "]";
	}
	
	public String show()
	{
		return show(",");
	}
	
	@Override
	public String toString()
	{
		return "Fava: " + this.getClass().getName();
	}
	
	/**
	 * Write the data in this Functionable<T> source data structure out to an FList<T> sink
	 * @return an FList<T> containing all values in this data structure
	 */
	public FList<T1> toSink()
	{
		return Fn.map(this, Functions.<T1>id());
	}
	
	public Functionable<Functionable<T1>> chunk(int size)
	{
		
		return Functionable.mapToFunctionable(Fn.chunk(this, size));		
	}
	
	public Functionable<Functionable<T1>> group()
	{
		return Functionable.mapToFunctionable(Fn.group(this));
	}
	
	public Functionable<Functionable<T1>> groupBy(FnCombine<T1, Boolean> f)
	{
		return Functionable.mapToFunctionable(Fn.groupBy(this, f));
	}
	
	
	protected static <T> Functionable<Functionable<T>> mapToFunctionable(Functionable<List<T>> list)
	{
		return list.map(new FnMap<List<T>, Functionable<T>>() {

			public Functionable<T> f(List<T> element) {
				return new FList<T>(element);
			}
		});
	}
	
	
	
}
