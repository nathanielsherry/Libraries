package fava.functionable;

import java.util.Iterator;

import fava.datatypes.Maybe;
import fava.signatures.FnGet;

public class Generator<T> extends Functionable<T>{

	private FnGet<Maybe<T>> generate;
	
	public Generator(FnGet<Maybe<T>> generate)
	{
		this.generate = generate;
	}
	
	public Iterator<T> iterator() {

		return new Iterator<T>() {

			Maybe<T> nextElement;
			
			public boolean hasNext() {
				
				if (nextElement == null)
				{
					nextElement = generate.f();
				}
				if (nextElement == null) throw new NullPointerException();
				
				
				return nextElement.is();
				
			}

			public T next() {
				
				if (nextElement == null)
				{
					nextElement = generate.f();
				}
				if (nextElement == null) throw new NullPointerException();
				
				
				if (nextElement.is()) {
					T element = nextElement.get();
					nextElement = null;
					return element;
				} else {
					nextElement = null;
					throw new IndexOutOfBoundsException();
				}
				
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	
	public String show()
	{
		return "[Generator]";
	}
	
	@Override
	public String show(String separator)
	{
		return "[Generator]";
	}	
	

}
