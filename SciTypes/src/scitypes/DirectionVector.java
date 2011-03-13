package scitypes;

import java.util.List;

import fava.Fn;
import fava.signatures.FnEach;
import fava.signatures.FnFold;
import fava.signatures.FnMap;

public class DirectionVector {

	private float distance, angle;

	public DirectionVector(float distance, float angle) {
		this.distance = distance;
		this.angle = angle % 360;
	}
	
	public DirectionVector(Coord<Float> coordinates)
	{
		angle = (float)Math.atan2(coordinates.x, coordinates.y);
		distance = (float)Math.sqrt(  coordinates.x*coordinates.x + coordinates.y*coordinates.y  );
	}

	
	public float getDistance() {
		return distance;
	}

	public float getAngle() {
		return angle;
	}
	
	public Coord<Float> toCartesian()
	{
		float x, y;
		float modangle = angle % 90;
		
		y = (float)Math.sin(modangle) * distance;
		x = (float)Math.cos(modangle) * distance;
		
		if (angle < 90)		{ return new Coord<Float>(x, y); }
		if (angle < 180)	{ return new Coord<Float>(-x, y); }
		if (angle < 270)		{ return new Coord<Float>(-x, -y); }
		if (angle < 360)		{ return new Coord<Float>(-x, y); }
		
		return null;
		
	}
	
	public DirectionVector add(DirectionVector other)
	{
		Coord<Float> c = toCartesian();
		Coord<Float> cother = other.toCartesian();
		return fromCartesian(c.x + cother.x, c.y + cother.y);	
	}
	
	
	public static DirectionVector fromCartesian(float x, float y)
	{
		DirectionVector d = new DirectionVector(0, 0);
		d.angle = (float)Math.atan2(x, y);
		d.distance = (float)Math.sqrt(  x*x + y*y  );
		return d;
		
	}
	
	public static DirectionVector sumVectors(List<DirectionVector> vs)
	{
		
		if (vs.size() == 0) return new DirectionVector(0, 0);
		
		return new DirectionVector(Fn.map(vs, new FnMap<DirectionVector, Coord<Float>>() {

			public Coord<Float> f(DirectionVector v) {
				return v.toCartesian();
			}
		}).fold(new FnFold<Coord<Float>, Coord<Float>>() {

			public Coord<Float> f(Coord<Float> v1, Coord<Float> v2) {
				return new Coord<Float>(v1.x+v2.x, v1.y+v2.y);
			}
		}));
		
	}
	
}
