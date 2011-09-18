package fava.functionable;

import java.util.Arrays;
import java.util.Iterator;


public class FArray<T> extends Functionable<T>{

	private T[] internalArray;
	
	private FArray(T[] array) {
		internalArray = array;
	}
	
	public static <T> FArray<T> wrap(T[] array)
	{
		return new FArray<T>(array);
	}
	
	@Override
	public Iterator<T> iterator() {
		return Arrays.asList(internalArray).iterator();
	}
	
	
	
	
	

	//////////////////////////////////////////////////////////
	// List auto-boxing
	//////////////////////////////////////////////////////////
	public static FList<Integer> boxi(int[] array)
	{
		FList<Integer> list = new FList<Integer>();
		for (int i : array)
		{
			list.add(i);
		}
		return list;
	}
	
	public static FList<Character> boxc(char[] array)
	{
		FList<Character> list = new FList<Character>();
		for (char i : array)
		{
			list.add(i);
		}
		return list;
	}
	
	public static FList<Short> boxs(short[] array)
	{
		FList<Short> list = new FList<Short>();
		for (short i : array)
		{
			list.add(i);
		}
		return list;
	}
	
	public static FList<Long> boxl(long[] array)
	{
		FList<Long> list = new FList<Long>();
		for (long i : array)
		{
			list.add(i);
		}
		return list;
	}
	
	public static FList<Float> boxf(float[] array)
	{
		FList<Float> list = new FList<Float>();
		for (float i : array)
		{
			list.add(i);
		}
		return list;
	}
	
	public static FList<Double> boxd(double[] array)
	{
		FList<Double> list = new FList<Double>();
		for (double i : array)
		{
			list.add(i);
		}
		return list;
	}


}
