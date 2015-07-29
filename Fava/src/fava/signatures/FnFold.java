package fava.signatures;

import java.util.function.BiFunction;

/**
 * Function signature for a function which accepts two values of type T1 and T2, and returns a value of type T2. Named for its obvious use in the Fold function
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T1>
 * @param <T2>
 */

public interface FnFold<T1, T2> extends BiFunction<T1, T2, T2>{
	
}
