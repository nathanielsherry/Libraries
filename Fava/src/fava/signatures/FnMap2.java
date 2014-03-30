package fava.signatures;

import java.util.function.BiFunction;

/**
 * Function signature for a function which accepts two values of (potentially) different types T1 and T2 and returns a result of a (potentially) different type T3
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T1>
 * @param <T2>
 * @param <T3>
 */

public interface FnMap2<T1, T2, T3> extends FnSignature, BiFunction<T1, T2, T3> {

	@Override
	default T3 apply(T1 t1, T2 t2) { return f(t1, t2); }
	
	T3 f(T1 v1, T2 v2);
	
}
