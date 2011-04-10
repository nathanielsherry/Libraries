package fava.datatypes;

import java.io.Serializable;

import fava.functionable.Functionable;


/**
 * 
 * This class provides a generic method of storing a pair of values. These two values do not need to be of the
 * same type. This is also useful as a workaround for Java's lack of tuples.
 * 
 * @author Nathaniel Sherry, 2009
 * @param <T> Type of first value in Pair
 * @param <S> Type of second value in Pair
 */

public class Pair<T, S> implements Serializable
{

	public T	first;
	public S	second;
	
	public Pair(T first, S second)
	{	
		this.first = first;
		this.second = second;
	}
	
	public Pair()
	{
		first = null;
		second = null;
	}
	
	public String show()
	{
		String firstString, secondString;
		firstString = (first instanceof Functionable<?>) ? ((Functionable<?>)first).show() : first.toString();
		secondString = (second instanceof Functionable<?>) ? ((Functionable<?>)second).show() : second.toString();
		return "(" + firstString + "," + secondString + ")";
	}
	
}
