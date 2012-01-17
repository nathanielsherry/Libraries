package fava.functionable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import fava.signatures.FnMap;

public class FCollection<T> extends Functionable<T> implements Collection<T>{

	
	private Collection<T> backing;
	
	public FCollection() {
		backing = new ArrayList<T>();
	}
	
	private FCollection(Collection<T> col)
	{
		backing = col;
	}
	
	
	public static <T> FCollection<T> wrap(Collection<T> col)
	{
		if (col instanceof FCollection) return (FCollection<T>)col;
		return new FCollection<T>(col);
	}
	
	
	
	protected <T2> Collection<T2> getNewCollection()
	{
		return new ArrayList<T2>();
	}
	
	protected <T2> FCollection<T2> wrapNewCollection(Collection<T2> col)
	{
		if (! (col instanceof Collection)) throw new ClassCastException();
		return FCollection.wrap((Collection<T2>)col);
	}
	
	
	
	
	
	
	
	
	@Override
	public boolean add(T t) {
		return backing.add(t);
	}

	@Override
	public boolean addAll(Collection<? extends T> ts) {
		return backing.addAll(ts);
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
	public Iterator<T> iterator() {
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
	public <S> S[] toArray(S[] ts) {
		return backing.<S>toArray(ts);
	}
	
	
	
	
	////////////////////////////////////////////////
	// Overriding Functionable methods
	////////////////////////////////////////////////

	public <T2> FCollection<T2> map(FnMap<T, T2> f)
	{
		
		Collection<T2> target = getNewCollection();		
		map(this, f, target);
		return wrapNewCollection(target);
		
	}
	

	public FCollection<T> filter(FnMap<T, Boolean> f)
	{
		Collection<T> target = getNewCollection();		
		filter(this, f, target);
		return wrapNewCollection(target);
	}
	
	


}
