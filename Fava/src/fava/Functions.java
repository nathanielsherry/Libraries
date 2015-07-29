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


public class Functions
{


	
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


	public static <T1> Consumer<T1> print()
	{
		return element -> System.out.println(element.toString());		
	}

	public static <T1> Function<T1, String> show()
	{
		return element -> element.toString();
	}
	
}
