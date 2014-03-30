package fava.signatures;

import java.util.function.Supplier;

/**
 * Function signature for a function which accepts no values and returns a value
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T1>
 */

public interface FnGet <T1> extends FnSignature, Supplier<T1> {

	@Override
	default T1 get() { return f(); }
	
	T1 f();

}
