package fava.functionable;


import fava.signatures.FnMap;


/**
 * 
 * Represents a range of integer values including a step size. The representation
 * of the range is inclusive of the upper bound value. 
 * <br><br>
 * While convention is to make the upper bound exclusive, this does not work as well 
 * when step sizes are introduced. A range of 1..5 representing [1, 2, 3, 4] may 
 * work well, but a range of 3..9:3 representing only [3, 6] and not [3, 6, 9] makes 
 * the exclusive upper bound a less appealing notation in this case.
 * 
 * @author Nathaniel Sherry, 2009-2011
 *
 */

public class Range extends Sequence<Integer>
{

	private int	start, stop, step;

	
	/**
	 * Creates an empty range representing no values. This is useful
	 * when attempting to create a a range which does not overlap 
	 * with any other range.
	 * <br><br>
	 * Note: Because the upper bound is inclusive to prevent odd 
	 * notation when dealing with step sizes other than 1, range(0,0)
	 * will represent 0<=x<=0 rather than the more conventional 
	 * 0<=x<0, and so range(0,0) will contain 0 as its only value.  
	 */
	public Range()
	{
		
	}

	/**
	 * Creates a new range from start to stop inclusive. While convention 
	 * is to make the upper bound exclusive, this does not work as well 
	 * when step sizes are introduced. A range of 1..5 representing 
	 * [1, 2, 3, 4] may work well, but a range of 3..9:3 representing only 
	 * [3, 6] and not [3, 6, 9] makes the exclusive upper bound a less 
	 * appealing notation in this case.
	 * <br><br> 
	 * Note: Because the upper bound is inclusive to prevent odd 
	 * notation when dealing with step sizes other than 1, range(0,0)
	 * will represent 0<=x<=0 rather than the more conventional 
	 * 0<=x<0, and so range(0,0) will contain 0 as its only value.  
	 * @param start the starting value of this range
	 * @param stop the stopping value of this range (inclusive)
	 */
	public Range(int start, int stop)
	{
		this(start, stop, 1);
	}

	/**
	 * Creates a new range from start to stop inclusive. While convention 
	 * is to make the upper bound exclusive, this does not work as well 
	 * when step sizes are introduced. A range of 1..5 representing 
	 * [1, 2, 3, 4] may work well, but a range of 3..9:3 representing only 
	 * [3, 6] and not [3, 6, 9] makes the exclusive upper bound a less 
	 * appealing notation in this case.
	 * <br><br> 
	 * Note: Because the upper bound is inclusive to prevent odd 
	 * notation when dealing with step sizes other than 1, range(0,0)
	 * will represent 0<=x<=0 rather than the more conventional 
	 * 0<=x<0, and so range(0,0) will contain 0 as its only value.  
	 * @param start the starting value of this range
	 * @param stop the stopping value of this range (inclusive)
	 * @param step the step size of this range
	 */
	public Range(int start, final int stop, final int step)
	{
		
		super();
		
		//always trust the step size -- reverse the numbers if they don't agree with the step size
		if (stop < start && step > 0)
		{
			//if the numbers are backwards, and we're not counting down
			this.start = stop;
			this.stop = start;
			
			this.step = step;
		}
		else if (stop > start && step < 0)
		{
			//we're counting down, but the numbers are in order
			this.start = stop;
			this.stop = start;
			
			this.step = step;
			
			
		} else {
			
			//if the numbers are forwards
			this.start = start;
			this.stop = stop;
			this.step = step;
		}
			
			
		
		
		
		super.setStartValue(this.start);
		super.setNextFunction(new FnMap<Integer, Integer>() {

			public Integer f(Integer element) {
				
				if (element == null) return null;
				Integer next = element+Range.this.step;			
				
				if (step < 0)
				{
					return next >= Range.this.stop ? next : null;
				} else {
					return next <= Range.this.stop ? next : null;	
				}
				
				
			}
		});
		
		
	}

	
	/**
	 * Creates a new range based on the length of the range 
	 * rather than the upper bound value. fromLength(1, 3) will
	 * create a range representing the values [1, 2, 3]
	 * @param start the starting value of the range
	 * @param length the length of the range
	 * @return a new Range representing the parameters given
	 */
	public static Range fromLength(int start, int length)
	{
		return new Range(start, start + length - 1);
	}
	
	/**
	 * Creates a new range based on the length of the range 
	 * rather than the upper bound value. fromLength(1, 5, 2) will
	 * create a range representing the values [1, 3, 5]
	 * @param start the starting value of the range
	 * @param length the length of the range
	 * @param stepsize the stepsize of the range
	 * @return a new Range representing the parameters given
	 */
	public static Range fromLength(int start, int length, int stepsize)
	{
		return new Range(start, start + length - 1, stepsize);
	}

	
	
	public int size()
	{
		return stop - start + 1;
	}



	public boolean isOverlapping(Range other)
	{
		
		//if neither of their ends lies within our ends, and none of our ends lies within theirs
		if (
				// check if their stop or start in contained in our range
				! ((other.stop >= start && other.stop <= stop) || (other.start >= start && other.start <= stop))
				
				&&
				
				// check if the other range complete engulfs us, since then neither of their ends would be in out range
				! (other.start < start && other.stop > stop)
				
		) return false;
		
		//if their step sizes aren't the same, they don't really overlap
		if (other.step != step) return false;
		
		//if they overlap, with the same step size, but are out of phase, they don't really overlap
		if (other.start % step != start % step) return false;
		
		
		//looks like they overlap
		return true;

	}
	
	
	public boolean isAdjacent(Range other)
	{
		if (other.step != step) return false;
		if (other.start % step != start % step) return false;
		return (other.start == stop + step || other.stop + step == start);
	}
	
	public boolean isTouching(Range other)
	{
		return isOverlapping(other) || isAdjacent(other);
	}
	
	public Range merge(Range other)
	{
		
		if (! (isOverlapping(other) || isAdjacent(other)) ) return null;
		
		return new Range(Math.min(other.start, start), Math.max(other.stop, stop), step);
		
	}
	
	public RangeSet difference(Range other)
	{
		RangeSet result = new RangeSet();
		
		if (! isOverlapping(other))
		{
			result.addRange(this);
			return result;
		}
		
		//other.start-1 because the range class uses an inclusive upper bound
		//other.stop+1 for similar reasons
		if (this.start < other.start) result.addRange(new Range(start, other.start-1, step));
		if (this.stop > other.stop) result.addRange(new Range(other.stop+1, stop, step));
		
		return result;
		
	}
	
	
	@Override
	public String show(String separator)
	{
		return "[" + start + ".." + stop + (step == 1 ? "" : ":" + step) + "]";
	}	

	
	public int getStart() {
		return start;
	}


	public int getStop() {
		return stop;
	}


	public int getStep() {
		return step;
	}
	
}
