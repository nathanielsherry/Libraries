package scitypes;

/**
 * 
 *  This class provides a method of storing an xy coordinate using any datatype
 *  The X and Y values are of the same type
 *
 * @author Nathaniel Sherry, 2009
 * @param <T> The type of data to be held in both slots of this coordinate pair
 */


public class Coord<T> {

	public T x, y;
	
	public Coord(T x, T y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString()
	{
		return "(" + x.toString() + "," + y.toString() + ")";
	}
	
}
