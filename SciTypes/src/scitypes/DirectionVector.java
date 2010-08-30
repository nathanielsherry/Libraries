package scitypes;

import java.util.List;


public class DirectionVector
{

	private Float direction;
	private Float distance;
		
	
	public DirectionVector(float distance, float direction)
	{
		this.direction = direction;
		this.distance = distance;
	}
	
	
	public DirectionVector(DirectionVector copy)
	{
		this.distance = copy.distance;
		this.direction = copy.direction;
	}
	
	
	public DirectionVector(Coord<Float> cartesian)
	{
		direction = (float)Math.toDegrees( Math.atan2(cartesian.y, cartesian.x) );
		distance = (float)Math.sqrt(cartesian.x*cartesian.x + cartesian.y*cartesian.y);
	}
	
	public Float getDirection()
	{
		return direction;
	}
	public Float getDistance()
	{
		return distance;
	}
	
	public Coord<Float> toCartesian()
	{
		float x, y;
		x = distance * (float)Math.cos(Math.toRadians(direction));
		y = distance * (float)Math.sin(Math.toRadians(direction));
		
		return new Coord<Float>(x, y);
		
	}

	
	public DirectionVector add(DirectionVector other)
	{
		Coord<Float> coord = this.toCartesian();
		Coord<Float> ocoord = other.toCartesian();
		
		return new DirectionVector(new Coord<Float>(coord.x + ocoord.x, coord.y + ocoord.y));
	}
	
	
	public static DirectionVector add(List<DirectionVector> vectors)
	{
		float x = 0;
		float y = 0;
		Coord<Float> coords;
		
		for (DirectionVector vector : vectors)
		{
			coords = vector.toCartesian();
			x += coords.x;
			y += coords.y;
		}
		
		return new DirectionVector(new Coord<Float>(x, y));

	}
	
	
	
	
	public static void main(String args[])
	{
		DirectionVector d1 = new DirectionVector(10, 0);
		DirectionVector d2 = new DirectionVector(5, 90);
		DirectionVector d3 = d1.add(d2);
		System.out.println(d3.toCartesian());
		
	}
	
	
}
