package scratch.wip;

import java.io.IOException;
import java.io.Serializable;

import scitypes.Cache;
import scitypes.wip.HashCache;
import scratch.AbstractScratchList;
import scratch.ScratchMap;

public class ScratchCache<K, V extends Serializable> extends HashCache<K, V> {

	long filesize;
	long currentsize;
	
	public ScratchCache(long filesize, String name) throws IOException {
		super();
		this.filesize = filesize;
		this.currentsize = 0;
		this.cache = new ScratchMap<K, V>(name + " ➤ Scratch Cache", true);
	}
	
	public ScratchCache(int filesize, String name, AbstractScratchList<V> backingList) throws IOException {
		super();
		this.filesize = filesize;
		this.currentsize = 0;
		this.cache = new ScratchMap<K, V>(name + " ➤ Scratch Cache", true, backingList);
	}
	
	
	protected boolean isFull()
	{
		return ((ScratchMap<K, V>)cache).filesize() > filesize;
	}
	
	
	public static <K, V extends Serializable> Cache<K, V> create(int size, String name)
	{
		try {
			return new ScratchCache<K, V>(size, name);
		} catch (IOException e) {
			return new HashCache<K, V>(size);
		}
	}
	
	
	public static <K, V extends Serializable> Cache<K, V> create(int size, String name, AbstractScratchList<V> backingList)
	{
		try {
			return new ScratchCache<K, V>(size, name, backingList);
		} catch (IOException e) {
			return new HashCache<K, V>(size);
		}
	}
	
	
	public static void main(String args[])
	{
		
		Cache<String, String> cache = ScratchCache.<String, String>create(24, "test");
		
		
		for (int i = 0; i < 10000; i++) {
						
			cache.store("1", "a");
			cache.store("2", "s");
			cache.store("3", "d");
			cache.store("4", "f");
		}
		
		
		
		if (cache.load("4").is()) System.out.println("4" + cache.load("4").get());
		if (cache.load("3").is()) System.out.println("3" + cache.load("3").get());
		if (cache.load("2").is()) System.out.println("2" + cache.load("2").get());
		if (cache.load("1").is()) System.out.println("1" + cache.load("1").get());
		
		cache.clear();
		
		
		if (cache.load("4").is()) System.out.println(cache.load("4").get());
	}
	
	
}
