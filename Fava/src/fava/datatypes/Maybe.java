package fava.datatypes;

/**
 * Inspired by the Haskell Maybe, this Maybe is a class for encapsulating a possible result. It 
 * allows, for example, a function to return any value including -1 or null as a legitimate value, 
 * while still enabling the function to report an error without throwing an exception. 
 * @author Nathaniel Sherry, 2010-2011
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
	
	/**
	 * Constructor for an empty (is() == false) Maybe
	 */
	public Maybe() {
		item = null;
		value = Value.NOTHING;
	}
	
	/**
	 * Constructor for a non-empty (is() == true) Maybe
	 * @param item the item this Maybe should contain
	 */
	public Maybe(T1 item)
	{
		this.item = item;
		this.value = Value.SOMETHING;
	}
	
	/**
	 * Sets the value of this Maybe; makes the Maybe non-empty
	 * @param item
	 */
	public void set(T1 item)
	{
		this.item = item;
		this.value = Value.SOMETHING;
	}
	
	/**
	 * Clears the value of this Maybe; makes the Maybe empty
	 */
	public void set()
	{
		this.item = null;
		this.value = Value.NOTHING;
		
	}
	
	/**
	 * Checks if the Maybe is non-empty -- if it has a value
	 * @return true if the Maybe has a value (including null), false otherwise
	 */
	public boolean is()
	{
		return value == Value.SOMETHING;
	}
	
	/**
	 * Gets the value of this Maybe. If this Maybe is empty, throws a NullPointerException
	 * @return The value of this Maybe
	 */
	public T1 get()
	{
		if (value == Value.SOMETHING) return item;
		throw new NullPointerException();
	}
	
	
}
