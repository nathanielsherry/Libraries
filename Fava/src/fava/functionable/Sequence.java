package fava.functionable;

import java.util.Iterator;

import fava.signatures.FnMap;

public class Sequence<T> extends Functionable<T> 
{

	private FnMap<T, T> f;
	private T start;
		
	public Sequence(T start, FnMap<T, T> f) {
		
		this.f = f;
		this.start = start;
		
	}
	
	
	public T nextValue(T oldValue)
	{
		return f.f(oldValue);
	}
	
	
	protected Sequence()
	{
		
	}
	
	protected void setNextFunction(FnMap<T, T> next)
	{
		f = next;
	}
	
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
				current = f.f(current);

				//return the current value
				return cur;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

}
