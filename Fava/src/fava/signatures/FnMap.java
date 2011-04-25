package fava.signatures;

/**
 * Function signature for a function which accepts a single value and returns a value of a (potentially) different type
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T1>
 * @param <T2>
 */

public interface FnMap<T1, T2> extends FnSignature {

	public T2 f(T1 v);
	
}
