package fava.signatures;

/**
 * Function signature for a function which accepts a single value and returns a Boolean value. It is a convenience form of {@link FnMap}&lt;T1, Boolean&gt;
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T1>
 */

public interface FnCondition<T1> extends FnMap<T1, Boolean> {

}
