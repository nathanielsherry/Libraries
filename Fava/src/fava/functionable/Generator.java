package fava.functionable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;


/**
 * Generates a sequence of elements of type T. Similar to {@link Sequence}, but does not directly rely on the previous element to generate the next.
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T>
 */

public class Generator<T> extends Functionable<T>{

	private Supplier<Optional<T>> generate;
	
	/**
	 * Create a new Generator with the given {@link Supplier} function to generate new values
	 * @param generate the {@link Supplier} function to generate values
	 */
	public Generator(Supplier<Optional<T>> generate)
	{
		this.generate = generate;
	}
	
	public Iterator<T> iterator() {

		return new Iterator<T>() {

			Optional<T> nextElement;
			
			public boolean hasNext() {
				
				if (nextElement == null)
				{
					nextElement = generate.get();
				}
				if (nextElement == null) return false;
				
				
				return nextElement.isPresent();
				
			}

			public T next() {
				
				if (nextElement == null)
				{
					nextElement = generate.get();
				}
				if (nextElement == null) throw new NoSuchElementException();
				
				
				if (nextElement.isPresent()) {
					T element = nextElement.get();
					nextElement = null;
					return element;
				} else {
					nextElement = null;
					throw new NoSuchElementException();
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
