package fava.signatures;

/**
 * Function signature for a function which accepts a single argument, and returns nothing
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T1>
 */

public interface FnEach<T1> extends FnSignature {

	void f(T1 v);
	
}
