package fava.signatures;

import java.util.function.Consumer;

/**
 * Function signature for a function which accepts a single argument, and returns nothing
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T1>
 */

public interface FnEach<T1> extends FnSignature, Consumer<T1> {

	@Override
	default void accept(T1 v) { f(v); }
	
	void f(T1 v);
	
}
