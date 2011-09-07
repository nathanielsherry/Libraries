package fava.functionable;

import java.util.List;

import fava.Fn;
import fava.Functions;
import fava.signatures.FnCombine;
import fava.signatures.FnFold;
import fava.signatures.FnEach;
import fava.signatures.FnMap;

/**
 * This is a base abstract class which provides a default implementation of some of the most common functional commands. 
 * Objects extending this class gain access to these methods, but will be required to implement the Iterable interface
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T1>
 */

public abstract class Functionable<T1> implements Iterable<T1> {

	/**
	 * Applies the given {@link FnEach} function to each contained element. 
	 * @param f
	 */
	public void each(FnEach<T1> f)
	{
		Fn.each(this, f);
	}
	
	/**
	 * Applies the given {@link FnMap} function to each contained element. Returns a Functionable object representing 
	 * the results of those applications. 
	 * @param <T2>
	 * @param f the mapping function to apply
	 * @return a Functionable object containing the results of applying the {@link FnMap} to the elements in this Functionable object
	 */
	public <T2> FList<T2> map(FnMap<T1, T2> f)
	{
		return Fn.map(this, f);
	}
	
	/**
	 * Applies the given {@link FnMap} function to each contained element. Returns a Functionable object containing the 
	 * elements of this object for which the given function returned true.
	 * @param f the condition function to apply
	 * @return a Functionable object containing only those elements in this Functionable object for which the given 
	 * function returned true
	 */
	public FList<T1> filter(FnMap<T1, Boolean> f)
	{
		return Fn.filter(this, f);
	}
	
	/**
	 * Applies the given {@link FnFold} function to consecutive contained elements, also threading a running sum or 
	 * result from call to call. When the function has been applied to every element, the result will be a single value. 
	 * @param f the folding function to apply
	 * @return the result of applying this function to all elements as if it were an n-ary function 
	 */
	public T1 fold(FnFold<T1, T1> f)
	{
		return Fn.fold(this, f);
	}
	
	/**
	 * Applies the given {@link FnFold} function to consecutive contained elements, also threading a running sum or 
	 * result from call to call. When the function has been applied to every element, the result will be a single value. 
	 * Since the return value may be of a different type than the contained elements, a starting value of that type is requried 
	 * @param f the folding function to apply
	 * @return the result of applying this function to all elements as if it were an n-ary function 
	 */
	public <T2> T2 fold(T2 base, FnFold<T1, T2> f)
	{
		return Fn.fold(this, base, f);
	}
	
	/**
	 * Remove and return the first 'number' elements from this Functionalbe object
	 * @param number the number of elements to take
	 * @return a Functionalbe object containing the first 'number' elements from this Functionable object
	 */
	public FList<T1> take(int number)
	{
		return Fn.take(this, number);
	}
	
	/**
	 * Remove and return elements from this Functionable object while the elements satisfy the given condition function.
	 * @param f the condition function to apply
	 * @return a Functionable object containing elements from this Functionable object up until the point where an element 
	 * fails to satisfy the given condition
	 */
	public FList<T1> takeWhile(FnMap<T1, Boolean> f)
	{
		return Fn.takeWhile(this, f);
	}
	
	/**
	 * Converts the contents of this object to a String representation, using the given separator. 
	 * @param separator the separator shown between elements 
	 * @return a String representation of the contained elements
	 */
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
	
	/**
	 * Converts the contents of this object to a String representation. 
	 * @return a String representation of the contained elements
	 */
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
	
	/**
	 * Group elements into collections of the given size based on their ordering. Eg: (1,2,3,4,5,6).chunk(2) => ((1, 2), (3, 4), (5, 6))
	 * @param size the size of a collection of elements
	 * @return nested collections of elements of the given size
	 */
	public FList<Functionable<T1>> chunk(int size)
	{
		
		return Functionable.mapToFunctionable(Fn.chunk(this, size));		
	}
	
	/**
	 * Group the elements into collections based on equality. Eg: (1, 2, 1, 2, 3).group() => ((1, 1), (2, 2), (3))
	 * @return nested collections of equivalent elements
	 */
	public FList<Functionable<T1>> group()
	{
		return Functionable.mapToFunctionable(Fn.group(this));
	}
	
	/**
	 * Group the elements into collections based on the supplied FnCombine, which should accept two elements and return 
	 * true if the elements belong in the same group
	 * @param f the {@link FnCombine} function for determining if two elements belong in the same group
	 * @return nested collections of elements which belong in the same group
	 */
	public FList<Functionable<T1>> groupBy(FnCombine<T1, Boolean> f)
	{
		return Functionable.mapToFunctionable(Fn.groupBy(this, f));
	}
	
	/**
	 * For internal use, maps a Functionable object containing {@link List}s to a Functionable object containing other Functionable objects
	 * @param <T>
	 * @param list
	 * @return
	 */
	protected static <T> FList<Functionable<T>> mapToFunctionable(Functionable<List<T>> list)
	{
		return list.map(new FnMap<List<T>, Functionable<T>>() {

			public Functionable<T> f(List<T> element) {
				return FList.<T>wrap(element);
			}
		});
	}
	
	
	
}
