package scratch;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;


public class ScratchMap<K, V extends Serializable> implements Map<K, V>{

	
	AbstractScratchList<V> valueList;
	BiMap<K, Integer> indexMap;
	
	public static <K, V extends Serializable> Map<K, V> create(String name)
	{
		try {
			return new ScratchMap<K, V>(name);
		} catch (IOException e) {
			return new LinkedHashMap<K, V>();
		}
	}
	
	
	public static <K, V extends Serializable> Map<K, V> create(String name, AbstractScratchList<V> backingList)
	{
		try {
			return new ScratchMap<K, V>(name, backingList);
		} catch (IOException e) {
			return new LinkedHashMap<K, V>();
		}
	}
	
	public ScratchMap(String name) throws IOException
	{
		this(name, null);
	}

	
	public ScratchMap(String name, AbstractScratchList<V> list) throws IOException {
		if (list == null) {
			valueList = new ScratchList<V>(name + " ➤ Scratch Map");
		} else {
			valueList = list;
			valueList.clear();
		}
		
		
		indexMap = HashBiMap.create(); //new LinkedHashMap<K, Integer>(10, 0.75f, accesOrder);
	}
	
	public void clear() {
		indexMap.clear();
		valueList.clear();
	}

	public boolean containsKey(Object key) {
		return indexMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return valueList.contains(value);
	}

	public V get(Object key) {
		Integer i = indexMap.get(key);
		if (i == null) return null;
		return valueList.get(i);
	}

	public boolean isEmpty() {
		return indexMap.isEmpty();
	}

	public V put(K key, V value) {
		if (indexMap.containsKey(key))
		{
			V old = get(key);
			
			valueList.set(indexMap.get(key), value);
			
			return old;
		} else 
		{
			indexMap.put(key, valueList.size());
			valueList.add(value);
			return null;
		}
	}

	public void putAll(Map<? extends K, ? extends V> t) {
		for (K k : t.keySet())
		{
			put(k, t.get(k));
		}
	}

	public V remove(Object key) {
		Integer i = indexMap.get(key);
		if (i == null) return null;
		
		//get the value (to return it later), and remove the key from
		//the mapping of keys to list indices
		V v = valueList.get(i);
		indexMap.remove(key);
		
		//compact the list by moving the last element into the empty space
		//and adjusting the index mapping accordingly
		V lastValueInList = valueList.get(valueList.size()-1);
		int lastIndex = valueList.size()-1;
		if (lastIndex != i) {
			valueList.set((int)i, lastValueInList);
			K lastKey = indexMap.inverse().get(lastIndex);
			indexMap.put(lastKey, i);
			valueList.remove(lastIndex);
		}
		
		return v;
	}

	public int size() {
		return indexMap.size();
	}

	
	
	
	
	public Set<Map.Entry<K, V>> entrySet() {
		
		Set<Map.Entry<K, V>> s = new HashSet<Entry<K,V>>();
		
		for (K k : indexMap.keySet())
		{
			s.add(new FileBackedMapEntry<K, V>(k, this));
		}
		
		return s;
		
	}
	
	public Set<K> keySet() {
		return indexMap.keySet();
	}
	
	public Collection<V> values() {
		return valueList;
	}

	public long filesize()
	{
		return valueList.filesize();
	}
	
}


class FileBackedMapEntry<K, V extends Serializable> implements Map.Entry<K, V> {

	private ScratchMap<K, V> map;
	private K key;
	
	public FileBackedMapEntry(K key, ScratchMap<K, V> map) {
		this.key = key;
		this.map = map;
	}
	
	public K getKey() {
		return key;
	}

	public V getValue() {
		return map.get(key);
	}

	public V setValue(V value) {
		return map.put(key, value);
	}
	
}