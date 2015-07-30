package fava.functionable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * Produces a sequence of values by taking an initial value and a {@link Function} to produce the next value from the current one.
 * 
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T>
 */

public class Sequence<T> extends Functionable<T> 
{

	private Function<T, T> f;
	private T start;
		
	
	
	
	
	protected <T2> Collection<T2> getNewCollection()
	{
		return new ArrayList<>();
	}
	
	protected <T2> FList<T2> wrapNewCollection(Collection<T2> col)
	{
		if (! (col instanceof List)) throw new ClassCastException();
		return FList.wrap((ArrayList<T2>)col);
	}

	
	
	
	
	/**
	 * Creates a new Sequence with the given starting value and {@link Function} to compute the following values
	 * @param start the initial value
	 * @param f the sequence function to compute further values
	 */
	public Sequence(T start, Function<T, T> f) {
		
		this.f = f;
		this.start = start;
		
	}
	
	
	/**
	 * Applies the function for generating new elements to the given value, and returns the result 
	 * @param oldValue the value to apply the sequence function to
	 * @return the next value in the sequence as determined by the sequence function
	 */
	public T nextValue(T oldValue)
	{
		return f.apply(oldValue);
	}
	

	/**
	 * Empty constructor for subclassing
	 */
	protected Sequence()
	{
		
	}
	
	/**
	 * Sets the sequence function for generating new values from the current one
	 * @param next
	 */
	protected void setNextFunction(Function<T, T> next)
	{
		f = next;
	}
	
	/**
	 * Sets the start value for generating new values
	 * @param start
	 */
	protected void setStartValue(T start)
	{
		this.start = start;
	}
	
	
	public Iterator<T> iterator() {
		
		return new Iterator<T>() {

			private T current = start;
			
			
			
			public boolean hasNext() {
				
				return (current != null);
				
			}

			public T next() {
				
				//store the current value so that we can return it
				T cur = current;
												
				//the next element becomes the new current element
				current = f.apply(current);

				//return the current value
				return cur;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////
	// Overriding Functionable Methods
	////////////////////////////////////////////////

	public <T2> FList<T2> map(Function<T, T2> f)
	{
		
		Collection<T2> target = getNewCollection();		
		map(this, f, target);
		return wrapNewCollection(target);
		
	}
	

//	public FList<T> filter(Function<T, Boolean> f)
//	{
//		Collection<T> target = getNewCollection();		
//		filter(this, f, target);
//		return wrapNewCollection(target);
//	}
//	
	
	public FList<T> take(int number)
	{
		Collection<T> target = getNewCollection();
		take(this, number, target);
		return wrapNewCollection(target);
	}
	
	
	public FList<T> takeWhile(Function<T, Boolean> f)
	{
		Collection<T> target = getNewCollection();
		takeWhile(this, f, target);
		return wrapNewCollection(target);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	protected static <S1> Collection<S1> take(Iterable<S1> source, int number, Collection<S1> target)
	{
		int count = 0;
		for (S1 s : source)
		{
			target.add(s);
			count++;
			if (count == number) break;
		}
		
		return target;
	}
	
	
	protected static <S1> Collection<S1> takeWhile(Iterable<S1> source, Function<S1, Boolean> f, Collection<S1> target)
	{
		for (S1 s : source)
		{
			if (!f.apply(s)) break;
			target.add(s);
		}
		
		return target;
	}
}
