package fava.functionable;

import java.util.Enumeration;
import java.util.Iterator;

public class FIterable<T> extends Functionable<T>
{

	private Iterable<T> backing;
	
	public FIterable(Iterable<T> iterable) {
		backing = iterable;
	}

	
	public FIterable(final Enumeration<T> enumeration) {
		backing = new Iterable<T>() {

			@Override
			public Iterator<T> iterator() {
				
				return new Iterator<T>() {

					@Override
					public boolean hasNext() {
						return enumeration.hasMoreElements();
					}

					@Override
					public T next() {
						return enumeration.nextElement();
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
				
			}
		};
	}
	
	
	@Override
	public Iterator<T> iterator() {
		return backing.iterator();
	}
	
}
