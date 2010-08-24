package scitypes.filebacked;

import java.io.IOException;
import java.io.Serializable;

import scitypes.Cache;

public class FileBackedCache<K, V extends Serializable> extends Cache<K, V> {

	
	public FileBackedCache(int size, String name) throws IOException {
		super();
		this.size = size;
		this.cache = new FileBackedMap<K, V>(name + " ➤ File Backed Cache", true);
	}
	
	public FileBackedCache(int size, String name, AbstractFileBackedList<V> backingList) throws IOException {
		super();
		this.size = size;
		this.cache = new FileBackedMap<K, V>(name + " ➤ File Backed Cache", true, backingList);
	}
	
	public static <K, V extends Serializable> Cache<K, V> create(int size, String name)
	{
		try {
			return new FileBackedCache<K, V>(size, name);
		} catch (IOException e) {
			return new Cache<K, V>(size);
		}
	}
	
	
	public static <K, V extends Serializable> Cache<K, V> create(int size, String name, AbstractFileBackedList<V> backingList)
	{
		try {
			return new FileBackedCache<K, V>(size, name, backingList);
		} catch (IOException e) {
			return new Cache<K, V>(size);
		}
	}
	
	
	public static void main(String args[])
	{
		
		Cache<String, String> cache = FileBackedCache.<String, String>create(3, "test");
		
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
