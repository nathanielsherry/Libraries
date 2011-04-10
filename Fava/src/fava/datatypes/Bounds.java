package fava.datatypes;

/**
 * 
 *  This class provides a method of storing a bounds using any datatype which extends Number
 *  The X and Y values are of the same type
 *
 * @author Nathaniel Sherry, 2009
 * @param <T> The type of data to be held in both slots of this range pair
 */

public class Bounds<T extends Number> {

	public T start, end;
	
	public Bounds(T start, T stop){
		this.start = start;
		this.end = stop;
	}
	
	
	
	public String show(String separator)
	{
		return "(" + start.toString() + separator + end.toString() + ")";
	}
		
	
}
