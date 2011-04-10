package fava.datatypes;


/**
 * 
 * This class provides a generic method of storing a pair of values. These two values do not need to be of the
 * same type. This is also useful as a workaround for Java's lack of tuples.
 * 
 * @author Nathaniel Sherry, 2009
 * @param <T> Type of first value in Pair
 * @param <S> Type of second value in Pair
 */

public class Triplet<T1, T2, T3>
{

	public T1	first;
	public T2	second;
	public T3	third;

	
	public Triplet(T1 first, T2 second, T3 third)
	{	
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	public Triplet()
	{
		first = null;
		second = null;
		third = null;
	}
	
	public String show()
	{
		return "(" + first.toString() + "," + second.toString() + "," + third.toString() + ")";
	}

}
