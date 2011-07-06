package plural.executor;

import java.util.List;

import plural.executor.eachindex.implementations.PluralEachIndexExecutor;
import plural.executor.filter.implementations.PluralFilterExecutor;
import plural.executor.fold.implementations.PluralFoldExecutor;
import plural.executor.map.implementations.PluralMapExecutor;

import fava.signatures.FnCondition;
import fava.signatures.FnEach;
import fava.signatures.FnFold;
import fava.signatures.FnMap;

public class Plural {

	public static <T1> T1 fold(List<T1> elements, FnFold<T1, T1> fold)
	{
		return new PluralFoldExecutor<T1>(elements, fold).executeBlocking();
	}

	/**
	 * Due to the nature of the parallelism used, the FnFold<T1, T1> fold operator
	 * must be associative and commutative.  
	 * @param <T1>
	 * @param elements
	 * @param base
	 * @param fold
	 * @return
	 */
	public static <T1> T1 fold(List<T1> elements, T1 base, FnFold<T1, T1> fold)
	{
		return new PluralFoldExecutor<T1>(elements, base, fold).executeBlocking();
	}
	
	
	public static <T1, T2> List<T2> map(List<T1> elements, FnMap<T1, T2> map)
	{
		return new PluralMapExecutor<T1, T2>(elements, map).executeBlocking();
	}
	
	
	public static void eachIndex(int size, FnEach<Integer> each)
	{
		new PluralEachIndexExecutor(size, each).executeBlocking();
	}
	
	public static <T1> List<T1> filter(List<T1> elements, FnCondition<T1> filter)
	{
		return new PluralFilterExecutor<T1>(elements, filter).executeBlocking();
	}
	
}
