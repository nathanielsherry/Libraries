package scitypes;

import java.io.Serializable;

import fava.datatypes.Maybe;

public abstract class Cache<K, V extends Serializable> {

	public abstract void store(K key, V value);

	public abstract Maybe<V> load(K key);

	public abstract void clear();
	
	protected abstract boolean isFull();

}