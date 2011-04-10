package fava.datatypes;

/**
 * Inspired by the Haskell Maybe, this Maybe is a class for encapsulating a possible result. It 
 * allows, for example, a function to return any value including -1 or null as a legitimate value, 
 * while still enabling the function to report an error without throwing an exception. 
 * @author Nathaniel Sherry
 *
 * @param <T1>
 */

public class Maybe<T1> {

	public enum Value
	{
		SOMETHING, NOTHING;
	}
	
	private T1 item;
	private Value value;
	
	public Maybe() {
		item = null;
		value = Value.NOTHING;
	}
	
	public Maybe(T1 item)
	{
		this.item = item;
		this.value = Value.SOMETHING;
	}
	
	public void set(T1 item)
	{
		this.item = item;
		this.value = Value.SOMETHING;
	}
	
	public void set()
	{
		this.item = null;
		this.value = Value.NOTHING;
		
	}
	
	public boolean is()
	{
		return value == Value.SOMETHING;
	}
	
	public T1 get()
	{
		if (value == Value.SOMETHING) return item;
		throw new NullPointerException();
	}
	
	
}
