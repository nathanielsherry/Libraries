package fava;



import java.util.List;
import java.util.function.BiFunction;

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

	
}
