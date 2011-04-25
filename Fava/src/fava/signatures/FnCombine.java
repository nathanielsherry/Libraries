package fava.signatures;

/**
 * Function signature for a function which accepts two values of the same type and returns a value of a (potentially) different type
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T1>
 * @param <T2>
 */

public interface FnCombine<T1, T2> extends FnMap2<T1, T1, T2> {

}
