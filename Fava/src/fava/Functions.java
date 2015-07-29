package fava;



import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import fava.datatypes.Pair;
import fava.functionable.FList;
import fava.signatures.FnCombine;


public class Functions
{


	//////////////////////////////////////////////////////////
	// STRING CONCAT
	//////////////////////////////////////////////////////////
	/**
	 * Returns a function for use in fold calls for concatenating strings
	 */
	public static <T1> BiFunction<T1, String, String> strcat()
	{
		return (s1, s2) -> s2 + s1.toString();
	}
	
	/**
	 * Returns a function for use in fold calls for concatenating objects as strings
	 */
	public static <T1> BiFunction<T1, String, String> strcat(final Function<T1, String> toString)
	{
		return (s1, s2) -> s2 + toString.apply(s1);
	}

	/**
	 * Returns a function for use in fold calls for concatenating strings with a separator
	 */
	public static BiFunction<String, String, String> strcat(final String separator)
	{
		return (s1, s2) -> s1 + separator + s2;
	}
	
	/**
	 * Returns a function for use in fold calls for concatenating objects as strings with a separator
	 */
	public static <T1> BiFunction<T1, String, String> strcat(final Function<T1, String> toString, final String separator)
	{
		return (s1, s2) -> s2 + separator + toString.apply(s1);
	}
	
	
	
	
	
	//////////////////////////////////////////////////////////
	// LIST CONCAT
	//////////////////////////////////////////////////////////
	public static <T1> BiFunction<List<T1>, List<T1>, List<T1>> listConcat()
	{
		return (l1, l2) -> { 
			l2.addAll(l1); 
			return l2; 
		};
	}
		
	public static <T1> BiFunction<FList<T1>, FList<T1>, FList<T1>> flistConcat()
	{
		return (l1, l2) -> { 
			l2.addAll(l1); 
			return l2; 
		};
	}

	

	public static <T1> Predicate<T1> notEquiv(final T1 item)
	{
		return compose(b -> !b, e -> item.equals(e));
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
	
	
	public static <T1 extends Number> Predicate<T1> lt(final Number compare)
	{
		return num -> num.doubleValue() < compare.doubleValue();
	}
	
	public static <T1 extends Number> Predicate<T1> gt(final Number compare)
	{
		return num -> num.doubleValue() > compare.doubleValue();
	}
	
}
