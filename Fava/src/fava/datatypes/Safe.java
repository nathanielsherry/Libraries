package fava.datatypes;

import fava.functionable.FList;
import fava.signatures.FnMap;

/**
 * The Safe<T> datatype encapsulates a value of type T and ensures that it is never set
 * to null. If an attempt is made to set the value to null, a {@link NullPointerException}
 * is thrown. This prevents null values from being quietly introduced only to
 * cause an error later, making it difficult to trace the origin of the null value.
 * <br/><br/>
 * 
 * Note: This is a runtime check only, it cannot make any guarantees at compile-time. For
 * example, this code will compile without error, but throw a {@link NullPointerException} at runtime:
 * 
 * <pre>
 * {@code
 * 
 * Safe<Integer> int = new Safe<Integer>(3);
 * int.set(null);
 * 
 * }
 * </pre>
 * 
 * @author Nathaniel Sherry
 *
 * @param <T> type of value to encapsulate
 */

public class Safe<T> {

	/**
	 * This is the value that we are encapsulating with the {@link Safe} object.
	 * This value should only ever be modified through the {@link Safe#set(Object)} method 
	 * to ensure that it is always null-checked.
	 */
	private T value;

	public Safe(T initial) {
		set(initial);
	}
	
	public T get() {
		return value;
	}

	public void set(T value) throws NullPointerException {
		if (value == null) {
			throw new NullPointerException("Values in type Safe<T> cannot be null");
		}
		this.value = value;
	}
	
	@Override
	public String toString()
	{
		return "Safe(" + value.toString() + ")";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//basic testing
	public static void main(String args[])
	{
		
		Safe<Integer> val = new Safe<Integer>(3);
		
		System.out.println(val.get());
		
		
		
		val.set(7);
		
		System.out.println(val.get());
		
		try {
			val.set(null);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		FList<Safe<Integer>> list = new FList<Safe<Integer>>();
		
		list.add(new Safe<Integer>(3));
		list.add(new Safe<Integer>(7));
		list.add(new Safe<Integer>(1));
		
		list.sort(new FnMap<Safe<Integer>, Integer>() {

			public Integer f(Safe<Integer> v) {
				return v.get();
			}
		});
		
		System.out.println(list.show());
		
	}
	
	
	
}
