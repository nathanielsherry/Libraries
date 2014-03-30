package fava.signatures;

/**
 * Function signature for a function which accepts three values of (potentially) different types T1, T2, and T3, and returns a result of a (potentially) different type T4
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T1>
 * @param <T2>
 * @param <T3>
 * @param <T4>
 */

public interface FnMap3<T1, T2, T3, T4> extends FnSignature {

	T4 f(T1 v1, T2 v2, T3 v3);
	
}
