package fava;


import static fava.Functions.and;
import static fava.Functions.equiv;
import static fava.Functions.or;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import fava.datatypes.Pair;
import fava.functionable.FList;
import fava.signatures.FnCombine;
import fava.signatures.FnFold;
import fava.signatures.FnMap;
import fava.signatures.FnMap2;

public class Fn
{

	private static <T1> FList<T1> list()
	{
		return new FList<T1>();
	}
		
	
	//////////////////////////////////////////////////////////
	// MAP
	//////////////////////////////////////////////////////////
	
	/**
	 * Transforms the elements of type T1 in 'list' using the given {@link FnMap} function, and returns the results
	 * @param <T1>
	 * @param <T2>
	 * @param list the input list
	 * @param f the transformation function
	 * @return an FList<T2> containing the results of the transformation 
	 */
	public static <T1, T2> FList<T2> map(Iterable<T1> list, FnMap<T1, T2> f)
	{

		if (list == null) return null;
		FList<T2> newlist = Fn.<T2> list();

		map_target(list, newlist, f);
		
		return newlist;

	}

	/**
	 * Transforms the elements of type T1 in 'array' using the given {@link FnMap} function, and returns the results
	 * @param <T1>
	 * @param <T2>
	 * @param array the input array
	 * @param f the transformation function
	 * @return an FList<T2> containing the results of the transformation 
	 */
	public static <T1, T2> FList<T2> map(T1 array[], FnMap<T1, T2> f)
	{

		if (array == null) return null;
		FList<T2> newlist = Fn.<T2> list();

		map_target(Arrays.asList(array), newlist, f);
		
		return newlist;

	}

	
	public static <T1, T2> FList<T2> mapIndex(List<T1> list, FnMap<Integer, T2> f)
	{
		if (list == null) return null;
		FList<T2> newlist = Fn.<T2> list();

		map_index_target(list, newlist, f);
		
		return newlist;

	}
	
	public static <T1, T2> FList<T2> mapIndex(Iterable<T1> iterable, FnMap<Integer, T2> f)
	{
		if (iterable == null) return null;
		FList<T2> newlist = Fn.<T2> list();

		map_index_target(iterable, newlist, f);
		
		return newlist;

	}

	
	public static <T1, T2> FList<T2> mapWithIndex(List<T1> list, FnMap2<T1, Integer, T2> f)
	{
		if (list == null) return null;
		FList<T2> newlist = Fn.<T2> list();

		map_with_index_target(list, newlist, f);
		
		return newlist;

	}
	
	public static <T1, T2> FList<T2> mapWithIndex(Iterable<T1> iterable, FnMap2<T1, Integer, T2> f)
	{
		if (iterable == null) return null;
		FList<T2> newlist = Fn.<T2> list();

		map_with_index_target(iterable, newlist, f);
		
		return newlist;

	}
	
	
	public static <T1, T2> FList<T2> map_target(Iterable<T1> list, List<T2> target, FnMap<T1, T2> f)
	{

		if (list == null) return null;

		for (T1 t : list)
		{
			target.add(f.f(t));
		}
		
		return asFList(target);

	}
	
	public static <T1, T2> FList<T2> map_index_target(List<T1> list, List<T2> target, FnMap<Integer, T2> f)
	{

		if (list == null) return null;

		target.clear();

		for (int i = 0; i < list.size(); i++)
		{
			target.add(f.f(i));
		}
		
		return asFList(target);

	}
	
	@SuppressWarnings("unused")
	public static <T1, T2> FList<T2> map_index_target(Iterable<T1> iterable, List<T2> target, FnMap<Integer, T2> f)
	{

		if (iterable == null) return null;

		target.clear();

		int i = 0;
		for (T1 t1 : iterable) {
			target.add(f.f(i));
			i++;
		}
		
		return asFList(target);

	}

	
	public static <T1, T2> FList<T2> map_with_index_target(List<T1> list, List<T2> target, FnMap2<T1, Integer, T2> f)
	{

		if (list == null) return null;

		target.clear();

		for (int i = 0; i < list.size(); i++)
		{
			target.add(f.f(list.get(i), i));
		}
		
		return asFList(target);

	}
	
	public static <T1, T2> FList<T2> map_with_index_target(Iterable<T1> iterable, List<T2> target, FnMap2<T1, Integer, T2> f)
	{

		if (iterable == null) return null;

		target.clear();

		int i = 0;
		for (T1 t1 : iterable) {
			target.add(f.f(t1, i));
			i++;
		}
		
		return asFList(target);

	}
	
	//////////////////////////////////////////////////////////
	// EACH
	//////////////////////////////////////////////////////////
	public static <T1> void each(T1[] list, Consumer<T1> f)
	{

		for (T1 element : list)
		{
			f.accept(element);
		}

	}

	public static <T1> void each(Iterable<T1> list, Consumer<T1> f)
	{

		for (T1 element : list)
		{
			f.accept(element);
		}

	}

	
	//////////////////////////////////////////////////////////
	// FILTER
	//////////////////////////////////////////////////////////
	public static <T1> FList<T1> filter(Iterable<T1> list, FnMap<T1, Boolean> f)
	{

		if (list == null) return null;
		FList<T1> newlist = Fn.<T1> list();

		filter_target(list, newlist, f);
		
		return newlist;

	}
	

	public static <T1> FList<T1> filter_index(List<T1> list, FnMap<Integer, Boolean> f)
	{
		if (list == null) return null;
		FList<T1> newlist = Fn.<T1> list();

		filter_index_target(list, newlist, f);
		
		return newlist;

	}
	

	
	
	public static <T1> FList<T1> filter_target(Iterable<T1> list, List<T1> target, FnMap<T1, Boolean> f)
	{

		if (list == null) return null;

		for (T1 element : list)
		{
			if (f.f(element)) target.add(element);
		}
		
		return asFList(target);

	}
	
	public static <T1> FList<T1> filter_index_target(List<T1> list, List<T1> target, FnMap<Integer, Boolean> f)
	{
		if (list == null) return null;

		for (int i = 0; i <= list.size(); i++)
		{
			if (f.f(i)) target.add(list.get(i));
		}
		
		return asFList(target);

	}

	
	//////////////////////////////////////////////////////////
	// FOLD
	//////////////////////////////////////////////////////////
	public static <T1, T2> T2 fold(T1 list[], T2 base, FnFold<T1, T2> f)
	{
		return foldl(Arrays.asList(list), base, f);
	}

	public static <T1> T1 fold(T1 list[], FnFold<T1, T1> f)
	{

		return foldl(Arrays.asList(list), f);
	}

	public static <T1, T2> T2 fold(List<T1> list, T2 base, FnFold<T1, T2> f)
	{
		return foldl(list, base, f);
	}
	
	public static <T1> T1 fold(List<T1> list, FnFold<T1, T1> f)
	{
		return foldl(list, f);
	}
	
	public static <T1, T2> T2 fold(Iterable<T1> list, T2 base, FnFold<T1, T2> f)
	{
		T2 result = base;
		if (list == null) return base;

		for (T1 elem : list)
		{
			result = f.f(elem, result);
		}

		return result;
	}

	public static <T1> T1 fold(Iterable<T1> list, FnFold<T1, T1> f)
	{

		if (list == null) return null;
		boolean needsAssigning = true;
		
		T1 result = null;
		for (T1 elem : list)
		{
			if (needsAssigning) {
				result = elem;
				needsAssigning = false;
			} else {
				result = f.f(elem, result);
			}
		}

		return result;
	}

	
	//////////////////////////////////////////////////////////
	// FOLDR
	//////////////////////////////////////////////////////////
	public static <T1, T2> T2 foldr(T1[] list, T2 base, FnFold<T1, T2> f)
	{
		return foldr(Arrays.asList(list), base, f);
	}
	
	public static <T1> T1 foldr(T1[] list, FnFold<T1, T1> f)
	{
		return foldr(Arrays.asList(list), f);
	}
	
	public static <T1, T2> T2 foldr(List<T1> list, T2 base, FnFold<T1, T2> f)
	{
		T2 result = base;

		if (list == null) return base;

		// order matters for foldr/foldl so we use a counter variable instead of an iterator
		for (int i = list.size() - 1; i >= 0; i--)
		{
			result = f.f(list.get(i), result);
		}

		return result;
	}

	public static <T1> T1 foldr(List<T1> list, FnFold<T1, T1> f)
	{
		if (list == null) return null;
		if (list.size() == 0) return null;

		T1 result = list.get(list.size() - 1);

		// order matters for foldr/foldl so we use a counter variable instead of an iterator
		for (int i = list.size() - 2; i >= 0; i--)
		{
			result = f.f(list.get(i), result);
		}

		return result;
	}


	
	
	//////////////////////////////////////////////////////////
	// FOLDL
	//////////////////////////////////////////////////////////
	public static <T1, T2> T2 foldl(T1[] list, T2 base, FnFold<T1, T2> f)
	{
		return foldl(Arrays.asList(list), base, f);
	}
	
	public static <T1> T1 foldl(T1[] list, FnFold<T1, T1> f)
	{
		return foldl(Arrays.asList(list), f);
	}
	
	public static <T1, T2> T2 foldl(List<T1> list, T2 base, FnFold<T1, T2> f)
	{
		T2 result = base;
		if (list == null) return base;

		// order matters for foldr/foldl so we use a counter variable instead of an iterator
		for (int i = 0; i < list.size(); i++)
		{
			result = f.f(list.get(i), result);
		}

		return result;
	}

	public static <T1> T1 foldl(List<T1> list, FnFold<T1, T1> f)
	{
		if (list == null) return null;
		if (list.size() == 0) return null;

		T1 result = list.get(0);

		// order matters for foldr/foldl so we use a counter variable instead of an iterator
		for (int i = 1; i < list.size(); i++)
		{
			result = f.f(list.get(i), result);
		}

		return result;
	}

	
	//////////////////////////////////////////////////////////
	// INCLUDE
	//////////////////////////////////////////////////////////
	public static <T1> boolean include(Iterable<T1> list, T1 item)
	{
		if (list == null) return false;

		return foldr(map(list, equiv(item)), false,	or());
	}

	public static <T1> boolean includeBy(Iterable<T1> list, FnMap<T1, Boolean> f)
	{
		FList<Boolean> blist = map(list, f);
		if (blist.size() == 0) return false;
		return foldr(blist, or());
	}

		

	//////////////////////////////////////////////////////////
	// ZIP
	//////////////////////////////////////////////////////////
	public static <T1, T2, T3> FList<T3> zipWith(List<T1> l1, List<T2> l2, FnMap2<T1, T2, T3> f)
	{

		FList<T3> l3 = Fn.<T3>list();

		return zipWith_target(l1, l2, l3, f);

	}
	
	public static <T1, T2, T3> FList<T3> zipWith_target(Iterable<T1> l1, Iterable<T2> l2, List<T3> target, FnMap2<T1, T2, T3> f)
	{

		if (l1 == null || l2 == null) return null;

		Iterator<T1> t1i = l1.iterator();
		Iterator<T2> t2i = l2.iterator();
		
		while (t1i.hasNext() && t2i.hasNext())
		{
			target.add(f.f(t1i.next(), t2i.next()));
		}

		return asFList(target);

	}
	
	public static <T1> FList<Boolean> zipEquiv(List<T1> l1, List<T1> l2)
	{
		return zipWith(l1, l2, Functions.<T1>equiv());
	}

	public static <T1> FList<Boolean> zipEquiv_target(List<T1> l1, List<T1> l2, List<Boolean> target)
	{
		return zipWith_target(l1, l2, target, Functions.<T1>equiv());
	}
	
	public static <T1, T2> FList<Pair<T1, T2>> zipPair(List<T1> l1, List<T2> l2)
	{
		return zipWith(l1, l2, new FnMap2<T1, T2, Pair<T1, T2>>(){

			public Pair<T1, T2> f(T1 o1, T2 o2) {
				return new Pair<T1, T2>(o1, o2);
			}});
	}
	
	public static <T1, T2> FList<Pair<T1, T2>> zipPair_target(Iterable<T1> l1, Iterable<T2> l2, List<Pair<T1, T2>> target)
	{
		return zipWith_target(l1, l2, target, new FnMap2<T1, T2, Pair<T1, T2>>(){

			public Pair<T1, T2> f(T1 o1, T2 o2) {
				return new Pair<T1, T2>(o1, o2);
			}});
	}
	
 	public static <T1, T2, T3> FList<T3> zipWith(T1[] l1, T2[] l2, FnMap2<T1, T2, T3> f)
	{
 		
		if (l1 == null || l2 == null) return null;
		FList<T3> l3 = Fn.<T3> list();

		return zipWith_target(Arrays.asList(l1), Arrays.asList(l2), l3, f);

	}

	
 	
	//////////////////////////////////////////////////////////
	// UNIQUE
	//////////////////////////////////////////////////////////
	public static <T1> FList<T1> unique(Iterable<T1> list)
	{

		FList<T1> newlist = Fn.<T1> list();
		return unique_target(list, newlist);

	}
	
	public static <T1> FList<T1> unique_target(Iterable<T1> list, List<T1> target)
	{

		
		/*
		boolean inlist;
		for (T1 elem : list)
		{
			inlist = false;
			for (T1 newelem : target)
			{
				inlist |= elem.equals(newelem);
				if (inlist) break;
			}

			if (!inlist) target.add(elem);

		}
		*/

		Set<T1> hash = new LinkedHashSet<T1>();
		
		for (T1 t : list){
			if (!hash.contains(t)) hash.add(t);
		}
		
		return new FList<T1>(hash);

	}

	public static <T1> FList<T1> uniqueBy(Iterable<T1> list, FnCombine<T1, Boolean> f)
	{

		FList<T1> newlist = Fn.<T1> list();

		return uniqueBy_target(list, newlist, f);

	}
	
	public static <T1> FList<T1> uniqueBy_target(Iterable<T1> sourceIter, List<T1> target, FnCombine<T1, Boolean> f)
	{
	
		boolean inlist;
		for (T1 elem : sourceIter)
		{
			inlist = false;
			for (T1 newelem : target)
			{
				inlist |= f.f(elem, newelem);
				if (inlist) break;
			}

			if (!inlist) target.add(elem);

		}

		return asFList(target);

	}
		
		
	
	
	
	//////////////////////////////////////////////////////////
	// SORT
	//////////////////////////////////////////////////////////
	public static <T1, T2> void sortBy(List<T1> list, final Comparator<T2> c, final FnMap<T1, T2> f)
	{

		Collections.sort(list, new Comparator<T1>() {

			public int compare(T1 o1, T1 o2)
			{
				return c.compare(f.f(o1), f.f(o2));
			}
		});

	}
	
	public static <T1, T2 extends Comparable<T2>> void sortBy(List<T1> list, final FnMap<T1, T2> f)
	{

		Collections.sort(list, new Comparator<T1>() {

			public int compare(T1 o1, T1 o2)
			{
				return f.f(o1).compareTo(f.f(o2));
			}
		});

	}


	

	
	
	//////////////////////////////////////////////////////////
	// GROUP
	//////////////////////////////////////////////////////////
	
	public static <T1> FList<List<T1>> group(final Iterable<T1> list)
	{
		return groupBy(list, Functions.<T1>equiv());
	}
	
	public static <T1> FList<List<T1>> group_target(final Iterable<T1> list, final List<List<T1>> target, final FnMap<T1, List<T1>> getSublist)
	{
		return groupBy_target(list, target, Functions.<T1>equiv(), getSublist);
	}
	
	public static <T1> FList<List<T1>> groupBy(final Iterable<T1> list, final FnCombine<T1, Boolean> f)
	{

		//function f determines if two elements belong in the same group;
		//use that function to create a 'unique' list where there is only
		//one element from each group remaining
		List<T1> uniques = uniqueBy(list, f);

		//map the list into a list of lists
		return map(uniques, new FnMap<T1, List<T1>>() {

			public List<T1> f(final T1 o1)
			{
				return filter(list, new FnMap<T1, Boolean>() {

					public Boolean f(T1 o2)
					{
						return f.f(o1, o2);
					}
				});
			}
		});

	}
	
	public static <T1> FList<List<T1>> groupBy_target(final Iterable<T1> list, final List<List<T1>> target, final FnCombine<T1, Boolean> f, final FnMap<T1, List<T1>> getSublist)
	{
	
		//generate a unique list
		List<T1> uniques = uniqueBy(list, f);

		
		
		//map the list into a list of lists
		return map_target(uniques, target, new FnMap<T1, List<T1>>() {

			public List<T1> f(final T1 o1)
			{				
				return filter_target(list, getSublist.f(null), new FnMap<T1, Boolean>() {

					public Boolean f(T1 o2)
					{
						return f.f(o1, o2);
					}
				});
			}
		});

	}
	
	
	


	//////////////////////////////////////////////////////////
	// CHUNK
	//////////////////////////////////////////////////////////
	
	public static <T1> FList<List<T1>> chunk(final Iterable<T1> list, final int size)
	{
		FList<List<T1>> newlist = Fn.<List<T1>> list();
		List<T1> sublist = null;
		
		int count = 0;
		for (T1 t : list)
		{
			
			if (count == 0) {
				sublist = new FList<T1>();
				newlist.add(sublist);
			}
			sublist.add(t);
			count++;
			
			if (count == size) count = 0;

			
		}
		
		return newlist;
	}
	
	
	
	
	
	//////////////////////////////////////////////////////////
	// CONCAT
	//////////////////////////////////////////////////////////
	public static <T1> FList<T1> concat(final List<List<T1>> lists)
	{
		return asFList(foldr(lists, Functions.<T1>listConcat()));
	}

	public static <T1> FList<T1> concatFList(final List<FList<T1>> lists)
	{
		return asFList(foldr(lists, Functions.<T1>flistConcat()));
	}
	
	
	public static <T1, T2> FList<T2> concatMap(final List<T1> list, FnMap<T1, List<T2>> f)
	{
		return concat(map(list, f));	
	}
	
	
	
	//////////////////////////////////////////////////////////
	// LOGICAL OPERATOR MAPPINGS
	//////////////////////////////////////////////////////////
	public static <T1> Boolean any(final Iterable<T1> list, FnMap<T1, Boolean>f)
	{
		return foldr(map(list, f), or());
	}

	public static Boolean any(final Iterable<Boolean> list)
	{
		return fold(list, or());
	}
	
	public static <T1> Boolean all(final Iterable<T1> list, FnMap<T1, Boolean>f)
	{
		return foldr(map(list, f), and());
	}
	
	public static Boolean all(final Iterable<Boolean> list)
	{
		return fold(list, and());
	}

	
	
	
	
	//////////////////////////////////////////////////////////
	// SHOW
	//////////////////////////////////////////////////////////
	public static <T1> FList<String> showList(List<T1> list)
	{
		return map(list, Functions.<T1>show());
	}
	public static <T1> FList<String> showList_target(List<T1> list, List<String> target)
	{
		return map_target(list, target, Functions.<T1>show());
	}
	
	public static <T1> FList<String> showListBy(List<T1> list, FnMap<T1, String> f)
	{
		return map(list, f);
	}
	public static <T1> FList<String> showListBy_target(List<T1> list, List<String> target, FnMap<T1, String> f)
	{
		return map_target(list, target, f);
	}
	
	
	
	
	//////////////////////////////////////////////////////////
	// TAKE
	//////////////////////////////////////////////////////////
	public static <T1> FList<T1> take(Iterable<T1> list, int count)
	{
		FList<T1> newlist = Fn.<T1> list();
		return take_target(list, newlist, count);
	}
	
	public static <T1> FList<T1> take_target(Iterable<T1> list, List<T1> target, final int count)
	{
		FList<T1> newlist = Fn.<T1> list();
		return takeWhile_target(list, newlist, new FnMap<T1, Boolean>() {

			int takeCount = 0;
			
			public Boolean f(T1 element) {
				return takeCount++ < count;
				
			}
		});
	}	
	
	public static <T1> FList<T1> takeWhile(Iterable<T1> list, FnMap<T1, Boolean> f)
	{
		FList<T1> newlist = Fn.<T1> list();
		return takeWhile_target(list, newlist, f);
	}
	
	public static <T1> FList<T1> takeWhile_target(Iterable<T1> list, List<T1> target, FnMap<T1, Boolean> f)
	{
		for (T1 t : list)
		{
			if (f.f(t))
				target.add(t);
			else
				break;
		}
		
		return asFList(target);
	}
	
	
	
	

	//////////////////////////////////////////////////////////
	// REVERSE
	//////////////////////////////////////////////////////////
	public static <T1> FList<T1> reverse(List<T1> list)
	{
		FList<T1> newlist = Fn.<T1> list();
		return reverse_target(list, newlist);
		
	}
	
	public static <T1> FList<T1> reverse_target(List<T1> list, List<T1> target)
	{
		for (T1 t : list) target.add(t);
		Collections.reverse(target);
		
		
		return asFList(target);
		
	}
	
	
	
	
	//////////////////////////////////////////////////////////
	// PERMUTATIONS
	//////////////////////////////////////////////////////////
	public static <T1> FList<List<T1>> permutations(final List<T1> list)
	{
		
		if (list.size() == 0) return new FList<List<T1>>(new FList<T1>());
		
		
		//map each element to a list of permutations with 'element' at the head
		return concat(map(list, new FnMap<T1, List<List<T1>>>() {

			public List<List<T1>> f(final T1 element) {
				
				
				List<T1> rest = map(list, Functions.<T1>id());
				rest.remove(element);
				
				
				return map(permutations(rest), new FnMap<List<T1>, List<T1>>(){

					public List<T1> f(List<T1> sublist) {
						List<T1> target = map(sublist, Functions.<T1>id());
						target.add(element);
						return target;
					}
				});
								
				
			}
		}));
		
		
	}
	
	
	private static <T1> FList<T1> asFList(List<T1> target){
		if (target instanceof FList) {
			return (FList<T1>)target;
		} else {
			return FList.wrap(target);
		}
	}
	
}
