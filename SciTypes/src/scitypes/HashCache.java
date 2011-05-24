package scitypes;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import fava.datatypes.Maybe;



public class HashCache<K, V extends Serializable> extends Cache<K, V> {

	protected int size;
	protected Map<K, V> cache;
	protected LinkedHashSet<K> keyOrdering;
	
	
	public HashCache(int size) {
		
		this();
		this.size = size;
		this.cache = new LinkedHashMap<K, V>(size, 1.0f, true);

		
	}
	
	protected HashCache()
	{
		this.keyOrdering = new LinkedHashSet<K>();
	}
	

	@Override
	public void store(K key, V value)
	{
		cache.put(key, value);
		keyOrdering.remove(key);
		keyOrdering.add(key);
		removeLRUifFull();
	}
	

	@Override
	public Maybe<V> load(K key)
	{
		
		if (cache.containsKey(key))	{
			keyOrdering.remove(key);
			keyOrdering.add(key);
			return new Maybe<V>(cache.get(key));
		}
		return new Maybe<V>();
	}
	

	@Override
	public void clear()
	{
		cache.clear();
	}
	
	
	protected boolean isFull()
	{
		return cache.size() == size;
	}
	
	private void removeLRUifFull()
	{
		//if cache is full, remove least recently used element
		while (isFull() && cache.size() > 0)
		{
			removeLRU();
		}
		
		return;
	}
	
	private void removeLRU()
	{
		//Iterator<K> keyIterator = cache.keySet().iterator();	
		Iterator<K> keyIterator = keyOrdering.iterator();
		if (!keyIterator.hasNext()) return;
		K k = keyIterator.next();
		
		if (keyOrdering.size() == 1)
		{
			cache.clear();
			keyOrdering.clear();
		} else {
			cache.remove(k);
			keyOrdering.remove(k);
		}
		
		return;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String args[])
	{
		
		Cache<String, String> cache = new HashCache<String, String>(3);
		
		cache.store("1", "a");
		cache.store("2", "s");
		cache.store("3", "d");
		cache.store("4", "f");
		
		System.out.println(cache.load("4"));
		System.out.println(cache.load("1"));
		
		cache.clear();
		
		System.out.println(cache.load("4"));
		
	}
	
	
}
