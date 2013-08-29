package fava.signatures;

/**
 * Function signature for a function which accepts two values of (potentially) different types T1 and T2 and returns a result of a (potentially) different type T3
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T1>
 * @param <T2>
 * @param <T3>
 */

public interface FnMap2<T1, T2, T3> extends FnSignature {

	T3 f(T1 v1, T2 v2);
	
}
