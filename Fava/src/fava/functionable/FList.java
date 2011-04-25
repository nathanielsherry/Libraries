package fava.functionable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import fava.Fn;
import fava.Functions;
import fava.datatypes.Pair;
import fava.signatures.FnCombine;
import fava.signatures.FnFold;
import fava.signatures.FnEach;
import fava.signatures.FnMap;
import fava.signatures.FnMap2;

/**
 * FList is a class which implements the List interface and acts as a pass-through
 * to another list object. This allows FList to adopt any List implementation (eg Array, Linked).
 * FList can imitate a sparse list's behaviour (although not runtime for sparse data) Eg. FList<String> l = new FList<String>(); l.add(4, "hello");
 * is a legitimate sequence of instructions, and will insert null values in the intervening elements.
 * @author Nathaniel Sherry
 *
 * @param <T>
 */
public class FList<T> extends Functionable<T> implements List<T> {

	
	protected List<T> backing;
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////
	// Constructors & Internal Methods
	////////////////////////////////////////////////
	
	public FList()
	{
		backing = new ArrayList<T>();
	}
	
	public FList(final FListComprehension<T> comprehension)
	{
		this(
			Fn.filter(comprehension.in(), new FnMap<T, Boolean>() {
	
				@Override
				public Boolean f(T v) {
					return comprehension.where(v);
				}
			}).map(new FnMap<T, T>(){
	
				@Override
				public T f(T v) {
					return comprehension.let(v);
				}
			})
		);
	}
		
	public FList(Iterable<T> source)
	{
		this(source.iterator());
	}
	
	public FList(Iterator<T> source)
	{
		this();
		
		while(source.hasNext())
		{
			add(source.next());
		}

	}

	public FList(T element)
	{
		this();
		add(element);
	}
	
	public FList(T... elements)
	{
		this();
		for (T t : elements)
		{
			add(t);
		}
	}
	
	
	public static <T> FList<T> wrap(List<T> list)
	{
		FList<T> newlist = new FList<T>();
		//make sure we don't nest FLists inside other FLists
		if (list instanceof FList<?>){
			FList<T> other = (FList<T>)list;
			newlist.backing = other.backing;
		} else {
			newlist.backing = list;
		}
		
		return newlist;
	}
	
	
	protected <C extends List<T>> FList(Class<C> c)
	{
		try {
			backing = c.newInstance();
		} catch (Exception e) {
			backing = new ArrayList<T>();
		}
	}
	
	/**
	 * Attempts to create a new target list by creating a new instance of source's class (eg ArrayList)
	 * @param <T> Type of source
	 * @param <T2> Type of new list to create.
	 * @param backing list to use as template
	 * @return a new list of type T2 of the same implementation as source
	 */
	protected static <T, T2> FList<T2> newTarget(List<T> backing)
	{
		try {
			return (FList<T2>)(backing.getClass().newInstance());
		} catch (Exception e) {
			return new FList<T2>();
		}
	}

	protected <T2> FList<T2> newTarget()
	{
		return FList.<T, T2>newTarget(backing);
	}
	
	
	
	
	public boolean equals(FList<T> other)
	{
		return (this == other || backing.equals(other.backing));
	}
	
	
	
	
	
	
	////////////////////////////////////////////////
	// List Interface Methods
	////////////////////////////////////////////////
	
	public boolean add(T arg0) {
		return backing.add(arg0);
	}

	public void add(int index, T value) {
		for (int i = backing.size(); i < index; i++) backing.add(null);
		backing.add(index, value);
	}

	public boolean addAll(Collection<? extends T> arg0) {
		return backing.addAll(arg0);
	}

	public boolean addAll(int arg0, Collection<? extends T> arg1) {
		return backing.addAll(arg0, arg1);
	}

	public void clear() {
		backing.clear();
	}

	public boolean contains(Object arg0) {
		return backing.contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		return backing.containsAll(arg0);
	}

	public T get(int arg0) {
		return backing.get(arg0);
	}

	public int indexOf(Object arg0) {
		return backing.indexOf(arg0);
	}

	public boolean isEmpty() {
		return backing.isEmpty();
	}

	public Iterator<T> iterator() {
		return backing.iterator();
	}

	public int lastIndexOf(Object arg0) {
		return backing.lastIndexOf(arg0);
	}

	public ListIterator<T> listIterator() {
		return backing.listIterator();
	}

	public ListIterator<T> listIterator(int arg0) {
		return backing.listIterator(arg0);
	}

	public boolean remove(Object arg0) {
		return backing.remove(arg0);
	}

	public T remove(int arg0) {
		return backing.remove(arg0);
	}

	public boolean removeAll(Collection<?> arg0) {
		return backing.removeAll(arg0);
	}

	public boolean retainAll(Collection<?> arg0) {
		return backing.retainAll(arg0);
	}

	public T set(int index, T value) {
		for (int i = backing.size(); i <= index; i++) backing.add(null);
		return backing.set(index, value);
	}

	public int size() {
		return backing.size();
	}

	public FList<T> subList(int arg0, int arg1) {
		return FList.<T>wrap(backing.subList(arg0, arg1));
	}

	public Object[] toArray() {
		return backing.toArray();
	}

	public <S> S[] toArray(S[] arg0) {
		return backing.toArray(arg0);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////
	// Functional Methods
	////////////////////////////////////////////////
	@Override
	public <T2> FList<T2> map(FnMap<T, T2> f)
	{
		FList<T2> target = newTarget();
		return Fn.map_target(backing, target, f);
	}
	
	public FList<T> map_i(FnMap<T, T> f)
	{
		return Fn.map_target(backing, backing, f);
	}

	
	
	
	public T foldr(FnFold<T, T> f)
	{
		return Fn.foldr(backing, f);
	}
	
	public <T2> T2 foldr(T2 base, FnFold<T, T2> f)
	{
		return Fn.foldr(backing, base, f);
	}
	
	public T foldl(FnFold<T, T> f)
	{
		return Fn.foldl(backing, f);
	}
	
	public <T2> T2 foldl(T2 base, FnFold<T, T2> f)
	{
		return Fn.foldr(backing, base, f);
	}
	
	
	
	
	@Override
	public FList<T> filter(FnMap<T, Boolean> f)
	{
		FList<T> target = newTarget();
		return Fn.filter_target(backing, target, f);
	}
	
	public FList<T> filter_i(FnMap<T, Boolean> f)
	{
		return Fn.filter_target(backing, backing, f);
	}
	
	
	
	
	public <T2, T3> FList<T3> zipWith(Iterable<T2> other, FnMap2<T, T2, T3> f)
	{
		FList<T3> target = newTarget();
		return Fn.zipWith_target(backing, other, target, f);
	}
	
	public FList<T> zipWith_i(Iterable<T> other, FnCombine<T, T> f)
	{
		return Fn.zipWith_target(backing, other, backing, f);
	}
	
	public FList<Boolean> zipEquivWith(Iterable<T> other, FnCombine<T, Boolean> f)
	{
		FList<Boolean> target = newTarget();
		return Fn.zipWith_target(backing, other, target, f);
	}
	
	public FList<Boolean> zipEquiv(Iterable<T> other)
	{
		FList<Boolean> target = newTarget();
		return Fn.zipWith_target(backing, other, target, Functions.<T>equiv());
	}
	
	public <T2> FList<Pair<T, T2>> zip(Iterable<T2> other)
	{
		FList<Pair<T, T2>> target = newTarget();
		return Fn.zipPair_target(backing, other, target);
	}
	
	
	
	@Override
	public void each(FnEach<T> f)
	{
		Fn.each(backing, f);
	}
	
	
	
	
	public boolean include(T element)
	{
		return Fn.include(backing, element);
	}
	
	public boolean includeBy(T element, FnMap<T, Boolean> f)
	{
		return Fn.includeBy(backing, f);
	}
	
	
	
	
	
	public FList<T> unique()
	{
		FList<T> target = newTarget();
		return Fn.unique_target(backing, target);
	}
	
	public FList<T> uniqueBy(FnCombine<T, Boolean> f)
	{
		FList<T> target = newTarget();
		return Fn.uniqueBy_target(backing, target, f);
	}
	
	
	

	
	@Override
	public FList<Functionable<T>> group()
	{
		FList<List<T>> result = newTarget();
		result = Fn.group_target(backing, result, new FnMap<T, List<T>>() {

			public List<T> f(T element) {
				return newTarget();
			}
		});
		
		return FList.mapToFunctionable(result);
	}
	
	@Override
	public FList<Functionable<T>> groupBy(FnCombine<T, Boolean> f)
	{
		FList<List<T>> result = newTarget();
		result = Fn.groupBy_target(backing, result, f, new FnMap<T, List<T>>() {

			public List<T> f(T element) {
				return newTarget();
			}
		});
		
		return FList.mapToFunctionable(result);
	}

	
	
	
	public boolean any(FnMap<T, Boolean> f)
	{
		return Fn.any(backing, f);
	}
	
	public boolean all(FnMap<T, Boolean> f)
	{
		return Fn.all(backing, f);
	}
	
	
	
	
	
	public FList<String> showList()
	{
		FList<String> target = newTarget();
		return Fn.showList_target(backing, target);
	}
	
	public FList<String> showListBy(FnMap<T, String> f)
	{
		FList<String> target = newTarget();
		return Fn.showListBy_target(backing, target, f);
	}
	
	
	@Override
	public String show(String separator)
	{
		final StringBuilder b = new StringBuilder("");
		this.each(new FnEach<T>(){

			public void f(T element) {
				if (element != null)
					if (element instanceof Functionable<?>)
					{
						b.append( ((Functionable<?>)element).show() );
					} else {
						b.append(element.toString());
					}
				else
					b.append("null");
					
				b.append(",");
			}});
		
		return "[" + (b.length() == 0 ? b.toString() : b.substring(0, b.length()-1)) + "]";
	}
	
	@Override
	public String show()
	{
		return show(",");
	}


	public String showBy(final FnMap<T, String> f)
	{
		final StringBuilder b = new StringBuilder();
		this.each(new FnEach<T>(){

			public void f(T element) {
				b.append(f.f(element));
			}});
		
		return b.toString();
	}
	
	
	
	public T head()
	{
		if (backing.size() == 0) return null;
		return backing.get(0);
	}
	public FList<T> tail()
	{
		return FList.<T>wrap(backing.subList(1, backing.size()));
	}
	
	
	@Override
	public FList<T> take(int count)
	{
		FList<T> target = newTarget();
		return Fn.take_target(backing, target, count);
	}
	
	@Override
	public FList<T> takeWhile(FnMap<T, Boolean> f)
	{
		FList<T> target = newTarget();
		return Fn.takeWhile_target(backing, target, f);
	}
	
	
	public FList<T> reverse()
	{
		FList<T> target = newTarget();
		return Fn.reverse_target(backing, target);
	}
	
	
	@Override
	public FList<Functionable<T>> chunk(int size)
	{
		return FList.mapToFunctionable(Fn.chunk(backing, size));
	}
	
	
	public void rotate()
	{
		rotate(1);
	}
	
	public void rotate(int count)
	{
		for (int i = 0; i <= count; i++) {
			T value = remove(0);
			add(value);	
		}
	
	}
	
	
	
	public void sort()
	{
		try{
			Collections.sort((List<Comparable>)backing);
		} catch (Exception e) {
			
		}
	}
	
	public void sort(Comparator<T> comparator) {
		
		Fn.sortBy(backing, comparator, Functions.<T>id());
	}
	
	
	public <S> void sort(Comparator<S> comparator, FnMap<T, S> mapping) {
		
		Fn.sortBy(backing, comparator, mapping);
	}
	
	
	public <S extends Comparable<? super S>> void sort(final FnMap<T, S> mapping) {
				

		Collections.sort(backing, new Comparator<T>() {

			public int compare(T v1, T v2) {
				return mapping.f(v1).compareTo(mapping.f(v2));
			}
		});
		
	}
	
	
	
	protected static <T> FList<Functionable<T>> mapToFunctionable(FList<List<T>> list)
	{
		return list.map(new FnMap<List<T>, Functionable<T>>() {

			public Functionable<T> f(List<T> element) {
				return FList.<T>wrap(element);
			}
		});
	}
		
}