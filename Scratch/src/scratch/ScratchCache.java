package scratch;

import java.io.IOException;
import java.io.Serializable;

import scitypes.Cache;

public class ScratchCache<K, V extends Serializable> extends Cache<K, V> {

	
	public ScratchCache(int size, String name) throws IOException {
		super();
		this.size = size;
		this.cache = new ScratchMap<K, V>(name + " ➤ Scratch Cache", true);
	}
	
	public ScratchCache(int size, String name, AbstractScratchList<V> backingList) throws IOException {
		super();
		this.size = size;
		this.cache = new ScratchMap<K, V>(name + " ➤ Scratch Cache", true, backingList);
	}
	
	public static <K, V extends Serializable> Cache<K, V> create(int size, String name)
	{
		try {
			return new ScratchCache<K, V>(size, name);
		} catch (IOException e) {
			return new Cache<K, V>(size);
		}
	}
	
	
	public static <K, V extends Serializable> Cache<K, V> create(int size, String name, AbstractScratchList<V> backingList)
	{
		try {
			return new ScratchCache<K, V>(size, name, backingList);
		} catch (IOException e) {
			return new Cache<K, V>(size);
		}
	}
	
	
	public static void main(String args[])
	{
		
		Cache<String, String> cache = ScratchCache.<String, String>create(3, "test");
		
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
