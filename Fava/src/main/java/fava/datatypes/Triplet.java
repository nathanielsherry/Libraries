package fava.datatypes;

import fava.functionable.Functionable;


/**
 * 
 * This class provides a generic method of storing a pair of values. These two values do not need to be of the
 * same type. This is also useful as a workaround for Java's lack of tuples.
 * 
 * @author Nathaniel Sherry, 2009-2011
 * @param <T1> Type of first value in Triplet
 * @param <T2> Type of second value in Triplet
 * @param <T3> Type of third value in Triplet
 */

public class Triplet<T1, T2, T3>
{

	public T1	first;
	public T2	second;
	public T3	third;

	
	/**
	 * Constructor to create a Pair with preset values
	 * @param first
	 * @param second
	 * @param third
	 */
	public Triplet(T1 first, T2 second, T3 third)
	{	
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	/**
	 * Constructor to create an empty Triplet
	 */
	public Triplet()
	{
		first = null;
		second = null;
		third = null;
	}
	
	
	/**
	 * Converts this Triplet into a human-readable String representation
	 * @return a human-readable String representation of this Triplet
	 */
	public String show()
	{
		String firstString, secondString, thirdString;
		firstString = (first instanceof Functionable<?>) ? ((Functionable<?>)first).show() : first.toString();
		secondString = (second instanceof Functionable<?>) ? ((Functionable<?>)second).show() : second.toString();
		thirdString = (third instanceof Functionable<?>) ? ((Functionable<?>)third).show() : third.toString();
		
		return "(" + firstString + "," + secondString + "," + thirdString + ")";
	}

}
