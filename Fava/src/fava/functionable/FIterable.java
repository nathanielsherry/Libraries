package fava.functionable;

import java.util.Enumeration;
import java.util.Iterator;

public class FIterable<T> extends Functionable<T>
{

	private Iterable<T> backing;
	
	/**
	 * Create a new FIterable out of an {@link Iterable}
	 * @param iterable
	 */
	public FIterable(Iterable<T> iterable) {
		backing = iterable;
	}

	/**
	 * Create a new FIterable out of an old {@link Enumeration}
	 * @param enumeration
	 */
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
	
	
	public FIterable(final Iterator<T> iterator) {
		backing = new Iterable<T>() {

			@Override
			public Iterator<T> iterator() {
				
				return iterator;
				
			}
		};
	}
	
	
	@Override
	public Iterator<T> iterator() {
		return backing.iterator();
	}
	
}
