package scitypes.filebacked;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileBackedMap<K, V extends Serializable> implements Map<K, V>{

	
	private FileBackedList<V> valuesList;
	private Map<K, Integer> indexMap;
	
	public FileBackedMap() throws IOException {
		valuesList = new FileBackedList<V>("FileBackedMap Values");
		indexMap = new HashMap<K, Integer>();
	}
	
	public void clear() {
		valuesList.clear();
		indexMap.clear();
	}

	public boolean containsKey(Object k) {
		return indexMap.containsKey(k);
	}

	public boolean containsValue(Object v) {
		return valuesList.contains(v);
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		
		
		// TODO Auto-generated method stub
		return null;
	}

	public V get(Object key) {
		return valuesList.get(indexMap.get(key));
	}

	public boolean isEmpty() {
		return indexMap.isEmpty();
	}

	public Set<K> keySet() {
		return indexMap.keySet();
	}

	public V put(K k, V v) {
		
		if (indexMap.containsKey(k))
		{
			V old = get(k);
			
			valuesList.set(indexMap.get(k), v);
			
			return old;
		} else 
		{
			indexMap.put(k, valuesList.size());
			valuesList.add(v);
			return null;
		}
		
	}

	public void putAll(Map<? extends K, ? extends V> map) {
		for (Entry<? extends K, ? extends V> e : map.entrySet())
		{
			put(e.getKey(), e.getValue());
		}
	}

	public V remove(Object key) {
		V old = get(key);
		valuesList.remove(indexMap.get(key));
		indexMap.remove(key);
		return old;
	}

	public int size() {
		return indexMap.size();
	}

	public Collection<V> values() {
		return valuesList;
	}

	
	
}


class FileBackedMapEntry<K, V extends Serializable> implements Map.Entry<K, V>
{

	private Map<K, Integer> indexMap;
	private FileBackedList<V> valueList;
	
	public FileBackedMapEntry(Map<K, Integer> indexMap, FileBackedList<V> valueList)
	{
		this.indexMap = indexMap;
		this.valueList = valueList;
	}
	
	public K getKey()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public V getValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public V setValue(V value)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
