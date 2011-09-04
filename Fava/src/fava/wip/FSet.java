package fava.wip;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import fava.signatures.FnMap;


public class FSet<S> extends FCollection<S> implements Set<S> 
{

	private Set<S> backing;
	
	
	
	protected <T> Collection<T> getNewCollection()
	{
		return new LinkedHashSet<T>();
	}
	
	protected <T> FSet<T> wrapNewCollection(Collection<T> col)
	{
		if (! (col instanceof Set)) throw new ClassCastException();
		return FSet.wrap((Set<T>)col);
	}
	
	/**
	 * Attempts to create a new target list by creating a new instance of source's class (eg ArrayList)
	 * @param <T> Type of source
	 * @param <T2> Type of new list to create.
	 * @param backing list to use as template
	 * @return a new list of type T2 of the same implementation as source
	 */
	@SuppressWarnings("unchecked")
	protected static <T, T2> FSet<T2> newTarget(Set<T> backing)
	{
		try {
			return (FSet<T2>)(backing.getClass().newInstance());
		} catch (Exception e) {
			return new FSet<T2>();
		}
	}

	protected <T2> FSet<T2> newTarget()
	{
		return FSet.<S, T2>newTarget(backing);
	}
	
	
	
	
	
	////////////////////////////////////////////////
	// Constructors & Internal Methods
	////////////////////////////////////////////////
	
	public FSet() {
		backing = new LinkedHashSet<S>();
	}
	
	private FSet(Set<S> set)
	{
		backing = set;
	}
	
	public static <S> FSet<S> wrap(Set<S> set)
	{
		if (set instanceof FSet) return (FSet<S>)set;
		return new FSet<S>(set);
	}
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////
	// Set methods
	////////////////////////////////////////////////
	
	@Override
	public boolean add(S s) {
		return backing.add(s);
	}

	@Override
	public boolean addAll(Collection<? extends S> ss) {
		return backing.addAll(ss);
	}

	@Override
	public void clear() {
		backing.clear();
	}

	@Override
	public boolean contains(Object o) {
		return backing.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> os) {
		return backing.containsAll(os);
	}

	@Override
	public boolean isEmpty() {
		return backing.isEmpty();
	}

	@Override
	public Iterator<S> iterator() {
		return backing.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return backing.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> os) {
		return backing.removeAll(os);
	}

	@Override
	public boolean retainAll(Collection<?> os) {
		return backing.retainAll(os);
	}

	@Override
	public int size() {
		return backing.size();
	}

	@Override
	public Object[] toArray() {
		return backing.toArray();
	}

	@Override
	public <T> T[] toArray(T[] ts) {
		return backing.toArray(ts);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////
	// Overriding Functionable methods
	////////////////////////////////////////////////

	public <T2> FSet<T2> map(FnMap<S, T2> f)
	{
		
		Collection<T2> target = getNewCollection();		
		map(this, f, target);
		return wrapNewCollection(target);
		
	}
	

	public FSet<S> filter(FnMap<S, Boolean> f)
	{
		Collection<S> target = getNewCollection();		
		filter(this, f, target);
		return wrapNewCollection(target);
	}
	
	
	public FSet<S> take(int number)
	{
		Collection<S> target = getNewCollection();
		take(this, number, target);
		return wrapNewCollection(target);
	}
	
	
	public FSet<S> takeWhile(FnMap<S, Boolean> f)
	{
		Collection<S> target = getNewCollection();
		takeWhile(this, f, target);
		return wrapNewCollection(target);
	}
	
	
	
}