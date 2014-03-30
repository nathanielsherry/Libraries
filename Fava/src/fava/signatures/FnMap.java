package fava.signatures;

import java.util.function.Function;

/**
 * Function signature for a function which accepts a single value and returns a value of a (potentially) different type
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T1>
 * @param <T2>
 */

public interface FnMap<T1, T2> extends FnSignature, Function<T1, T2> {

	@Override
	default T2 apply(T1 t) { return f(t); }
	
	T2 f(T1 v);
	
}
