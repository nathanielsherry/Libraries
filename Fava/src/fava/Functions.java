package fava;



import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import fava.datatypes.Pair;
import fava.functionable.FList;
import fava.signatures.FnCombine;
import fava.signatures.FnFold;


public class Functions
{


	//////////////////////////////////////////////////////////
	// STRING CONCAT
	//////////////////////////////////////////////////////////
	/**
	 * Returns a function for use in fold calls for concatenating strings
	 */
	public static <T1> FnFold<T1, String> strcat()
	{
		return new FnFold<T1, String>() {

			public String apply(T1 s1, String s2)
			{
				return s2 + s1.toString();
			}
		};
	}
	
	/**
	 * Returns a function for use in fold calls for concatenating objects as strings
	 */
	public static <T1> FnFold<T1, String> strcat(final Function<T1, String> toString)
	{
		
		return new FnFold<T1, String>() {

			public String apply(T1 s1, String s2)
			{
				return s2 + toString.apply(s1);
			}
		};
	}

	/**
	 * Returns a function for use in fold calls for concatenating strings with a separator
	 */
	public static FnFold<String, String> strcat(final String separator)
	{
		return new FnFold<String, String>() {

			public String apply(String s1, String s2)
			{
				return s2 + separator + s1;
			}
		};
	}
	
	/**
	 * Returns a function for use in fold calls for concatenating objects as strings with a separator
	 */
	public static <T1> FnFold<T1, String> strcat(final Function<T1, String> toString, final String separator)
	{
		
		return new FnFold<T1, String>() {

			public String apply(T1 s1, String s2)
			{
				return s2 + separator + toString.apply(s1);
			}
		};
	}
	
	
	
	
	
	//////////////////////////////////////////////////////////
	// LIST CONCAT
	//////////////////////////////////////////////////////////
	public static <T1> FnFold<List<T1>, List<T1>> listConcat()
	{

		return new FnFold<List<T1>, List<T1>>() {

			public List<T1> apply(List<T1> l1, List<T1> l2)
			{
				l2.addAll(l1);
				return l2;
			}

		};

	}
		
	public static <T1> FnFold<FList<T1>, FList<T1>> flistConcat()
	{

		return new FnFold<FList<T1>, FList<T1>>() {

			public FList<T1> apply(FList<T1> l1, FList<T1> l2)
			{
				l2.addAll(l1);
				return l2;
			}

		};

	}

	
	
	public static <T1> FnCombine<T1, Boolean> equiv()
	{
		return new FnCombine<T1, Boolean>() {

			public Boolean apply(T1 o1, T1 o2)
			{
				return o1.equals(o2);
			}
		};
	}

	public static <T1> Predicate<T1> notEquiv(final T1 item)
	{
		return compose(not(), e -> item.equals(e));
	}
	

	

	public static <T1, T2> Function<Pair<T1, T2>, T1> first()
	{
		return new Function<Pair<T1, T2>, T1>() {

			public T1 apply(Pair<T1, T2> element)
			{
				return element.first;
			}
		};
	}

	public static <T1, T2> Function<Pair<T1, T2>, T2> second()
	{
		return new Function<Pair<T1, T2>, T2>() {

			public T2 apply(Pair<T1, T2> element)
			{
				return element.second;
			}
		};
	}

	

	public static <T1> Predicate<T1> bTrue()
	{
		return e -> true;
	}

	public static <T1> Predicate<T1> bFalse()
	{
		return e -> false;
	}

	

	public static <T1> Function<T1, T1> id()
	{
		return new Function<T1, T1>() {

			public T1 apply(T1 element)
			{
				return element;
			}

		};
	}

	

	public static <T1> Predicate<T1> notNull()
	{
		return e -> e != null;
	}


	
	public static FnFold<Boolean, Boolean> and()
	{
		return new FnFold<Boolean, Boolean>() {

			public Boolean apply(Boolean b1, Boolean b2)
			{
				return b1 && b2;
			}
		};
	}

	public static FnFold<Boolean, Boolean> or()
	{
		return new FnFold<Boolean, Boolean>() {

			public Boolean apply(Boolean b1, Boolean b2)
			{
				return b1 || b2;
			}
		};
	}

	public static Predicate<Boolean> not()
	{
		return e -> !e;
	}

	

	public static <T1> Consumer<T1> print()
	{
		return element -> System.out.println(element.toString());		
	}

	public static <T1> Function<T1, String> show()
	{
		return new Function<T1, String>(){

			public String apply(T1 element) {
				return element.toString();
			}};
	}
	
	
	
	public static <T1, T2, T3> Function<T1, T3> compose(final Function<T2, T3> h, final Function<T1, T2> g)
	{
		return new Function<T1, T3>(){

			public T3 apply(T1 element) {
				return h.apply(g.apply(element));
			}};
	}
	
	public static <T1, T2> Predicate<T1> compose(final Predicate<T2> h, final Function<T1, T2> g)
	{
		return e -> h.test((g.apply(e)));
	}
	
	public static <T1, T2, T3> FnCombine<T1, T3> compose(final Function<T2, T3> h, final FnCombine<T1, T2> g)
	{
		return new FnCombine<T1, T3>() {

			public T3 apply(T1 v1, T1 v2) {
				return h.apply(g.apply(v1, v2));
			}

		};
	}
	
	public static <T1a, T1b, T2, T3> BiFunction<T1a, T1b, T3> compose(final Function<T2, T3> h, final BiFunction<T1a, T1b, T2> g)
	{
		return new BiFunction<T1a, T1b, T3>() {

			public T3 apply(T1a v1, T1b v2) {
				return h.apply(g.apply(v1, v2));
			}
		};
	}
	

	
	public static FnFold<Integer, Integer> addi()
	{
		return new FnFold<Integer, Integer>() {

			public Integer apply(Integer v1, Integer v2) {
				return v1 + v2;
			}
		};
	}
	public static FnFold<Long, Long> addl()
	{
		return new FnFold<Long, Long>() {

			public Long apply(Long v1, Long v2) {
				return v1 + v2;
			}
		};
	}
	public static FnFold<Short, Short> adds()
	{
		return new FnFold<Short, Short>() {

			public Short apply(Short v1, Short v2) {
				return (short)(v1 + v2);
			}
		};
	}
	public static FnFold<Float, Float> addf()
	{
		return new FnFold<Float, Float>() {

			public Float apply(Float v1, Float v2) {
				return v1 + v2;
			}
		};
	}
	public static FnFold<Double, Double> addd()
	{
		return new FnFold<Double, Double>() {

			public Double apply(Double v1, Double v2) {
				return v1 + v2;
			}
		};
	}
	public static FnFold<Byte, Byte> addb()
	{
		return new FnFold<Byte, Byte>() {

			public Byte apply(Byte v1, Byte v2) {
				return (byte)(v1 + v2);
			}
		};
	}
	public static FnFold<BigInteger, BigInteger> addbi()
	{
		return new FnFold<BigInteger, BigInteger>() {

			public BigInteger apply(BigInteger v1, BigInteger v2) {
				return v1.add(v2);
			}
		};
	}
	public static FnFold<BigDecimal, BigDecimal>addbd()
	{
		return new FnFold<BigDecimal, BigDecimal>() {

			public BigDecimal apply(BigDecimal v1, BigDecimal v2) {
				return v1.add(v2);
			}
		};
	}

	
	public static <T1 extends Number> Predicate<T1> lt(final Number compare)
	{
		return num -> num.doubleValue() < compare.doubleValue();
	}
	
	public static <T1 extends Number> Predicate<T1> gt(final Number compare)
	{
		return num -> num.doubleValue() > compare.doubleValue();
	}
	
}
