package fava.signatures;

/**
 * Function signature for a function which accepts no parameters and returns no value
 * @author Nathaniel Sherry, 2010-2011
 *
 */

public interface FnCall extends FnSignature, Runnable {

	@Override
	default void run() { f(); } 
	
	void f();
	
}
