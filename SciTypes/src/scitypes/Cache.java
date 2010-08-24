package scitypes;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import fava.datatypes.Maybe;



public class Cache<K, V extends Serializable> {

	protected int size;
	protected Map<K, V> cache;
	
	
	public Cache(int size) {
		
		this();
		this.size = size;
		this.cache = new LinkedHashMap<K, V>(size, 1.0f, true);
		
	}
	
	protected Cache()
	{
	}
	
	public void store(K key, V value)
	{
		removeLRUifFull();
		cache.put(key, value);
	}
	
	public V load(K key)
	{
		return cache.get(key);
	}
	
	public void clear()
	{
		cache.clear();
	}
	
	
	private Maybe<V> removeLRUifFull()
	{
		//if cache is full, remove least recently used element
		if (cache.size() == size)
		{
			Iterator<K> keyIterator = cache.keySet().iterator();
			K k = keyIterator.next();
			V v = load(k);
			cache.remove(k);
			return new Maybe<V>(v);
		}
		
		return new Maybe<V>();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String args[])
	{
		
		Cache<String, String> cache = new Cache<String, String>(3);
		
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
