package fava.functionable;

import java.util.Arrays;
import java.util.Iterator;


public final class FArray<T> extends Functionable<T>{

	private T[] internalArray;
	
	private FArray(T[] array) {
		internalArray = array;
	}
	
	public static <T> FArray<T> wrap(T[] array)
	{
		return new FArray<>(array);
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
		FList<Integer> list = new FList<>();
		for (int i : array)
		{
			list.add(i);
		}
		return list;
	}
	
	public static FList<Character> boxc(char[] array)
	{
		FList<Character> list = new FList<>();
		for (char i : array)
		{
			list.add(i);
		}
		return list;
	}
	
	public static FList<Short> boxs(short[] array)
	{
		FList<Short> list = new FList<>();
		for (short i : array)
		{
			list.add(i);
		}
		return list;
	}
	
	public static FList<Long> boxl(long[] array)
	{
		FList<Long> list = new FList<>();
		for (long i : array)
		{
			list.add(i);
		}
		return list;
	}
	
	public static FList<Float> boxf(float[] array)
	{
		FList<Float> list = new FList<>();
		for (float i : array)
		{
			list.add(i);
		}
		return list;
	}
	
	public static FList<Double> boxd(double[] array)
	{
		FList<Double> list = new FList<>();
		for (double i : array)
		{
			list.add(i);
		}
		return list;
	}


}
