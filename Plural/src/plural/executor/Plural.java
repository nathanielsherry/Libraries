package plural.executor;

import java.util.List;

import plural.executor.eachindex.implementations.PluralEachIndexExecutor;
import plural.executor.filter.implementations.PluralFilterExecutor;
import plural.executor.fold.implementations.PluralFoldExecutor;
import plural.executor.map.implementations.PluralMapExecutor;

import fava.signatures.FnEach;
import fava.signatures.FnFold;
import fava.signatures.FnMap;

public class Plural {

	public static <T1> T1 fold(List<T1> elements, FnFold<T1, T1> fold)
	{
		return new PluralFoldExecutor<T1>(elements, fold).executeBlocking();
	}
	
	
	public static <T1, T2> List<T2> map(List<T1> elements, FnMap<T1, T2> map)
	{
		return new PluralMapExecutor<T1, T2>(elements, map).executeBlocking();
	}
	
	
	public static void eachIndex(int size, FnEach<Integer> each)
	{
		new PluralEachIndexExecutor(size, each).executeBlocking();
	}
	
	public static <T1> List<T1> filter(List<T1> elements, FnMap<T1, Boolean> filter)
	{
		return new PluralFilterExecutor<T1>(elements, filter).executeBlocking();
	}
	
}
