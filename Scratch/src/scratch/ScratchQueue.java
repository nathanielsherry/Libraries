package scratch;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;


public class ScratchQueue<T extends Serializable> implements Queue<T> {

	private ScratchList<T> valueList;
	private Queue<Integer> indexQueue;
	private Queue<Integer> emptyIndices;
	private int maxIndex;
	
	
	public ScratchQueue(String name) throws IOException {
		
		valueList = new ScratchList<T>(name);
		
		indexQueue = new LinkedList<Integer>();
		emptyIndices = new LinkedList<Integer>();
		
		maxIndex = -1;
		
	}
	
	
	
	
	
	
	
	
	public boolean add(T arg0) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(Collection<? extends T> arg0) {
		throw new UnsupportedOperationException();
	}

	public void clear() {
		valueList.clear();
		indexQueue.clear();
		emptyIndices.clear();
		maxIndex = -1;
	}

	public boolean contains(Object arg0) {
		return valueList.contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		
		return valueList.containsAll(arg0);
		
	}

	public boolean isEmpty() {
		return valueList.isEmpty();
	}

	public Iterator<T> iterator() {
		
		return new Iterator<T>() {

			Iterator<Integer> indexIterator = indexQueue.iterator();
			int index = -1;
			
			public boolean hasNext() {
				return indexIterator.hasNext();
			}

			public T next() {
				index = indexIterator.next();
				return valueList.get(index);
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
		
	}

	public boolean remove(Object arg0) {
		
		//we need to do this ourselves so that we can
		//track which index is being eliminated. Also,
		//if the queue has fewer elements in it now than
		//it has in the past, this will be quicker, since
		//we won't be checking null entries.
		int index = -1;
		for (Integer i : indexQueue) {
			if (  arg0.equals(valueList.get(i))  ) {
				valueList.remove(i);
				emptyIndices.offer(i);
				index = i;
				break;
			}
		}
		
		if (index != -1) {
			indexQueue.remove(index);
			return true;
		}
		
		return false;
	}

	public boolean removeAll(Collection<?> c) {
		
		if (c == null) return false;
		
		T t;
		
		LinkedList<Integer> toRemove = new LinkedList<Integer>();
		
		//figure out which elements we want to remove.
		for (Integer i : indexQueue)
		{
			//deserialize each element in our list only once
			t = valueList.get(i);
			
			if (c.contains(t))
			{
				//decrement the counter to make up for the fact that
				//we have removed the current element, and we don't
				//want to skip over an element
				toRemove.addFirst(i);
			}
			
		}
		
		for (Integer i : toRemove)
		{
			valueList.remove(i);
			indexQueue.remove(i);
			emptyIndices.offer(i);
		}
		

		return true;
		
	}

	public boolean retainAll(Collection<?> c) {
		
		if (c == null) return false;
		
		T t;
		
		LinkedList<Integer> toRemove = new LinkedList<Integer>();
		
		//figure out which elements we want to remove.
		for (Integer i : indexQueue)
		{
			//deserialize each element in our list only once
			t = valueList.get(i);
			
			if (!c.contains(t))
			{
				//decrement the counter to make up for the fact that
				//we have removed the current element, and we don't
				//want to skip over an element
				toRemove.addFirst(i);
			}
			
		}
		
		for (Integer i : toRemove)
		{
			valueList.remove(i);
			indexQueue.remove(i);
			emptyIndices.offer(i);
		}
		

		return toRemove.size() > 0;
	}

	public int size() {
		return valueList.size();
	}

	public Object[] toArray() {
		return valueList.toArray();
	}

	public <S> S[] toArray(S[] arg0) {
		return valueList.toArray(arg0);
	}

	public T element() {
		
		if (indexQueue.size() == 0) throw new NoSuchElementException();
		
		return valueList.get(indexQueue.element());
		
	}

	public boolean offer(T t) {
		
		int index = ++maxIndex;
		if (emptyIndices.size() > 0)
		{
			index = emptyIndices.poll();
		}
		
		valueList.add(index, t);
		indexQueue.add(index);
		
		return true;
		
	}

	public T peek() {
		try{
			return element();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public T poll() {
		
		//this will be null if we have no elements
		T result = peek();
		
		//get the index of the first element -- will be null if we have no elements
		Integer index = indexQueue.poll();
		if (index != null) emptyIndices.offer(index);
		
		return result;
		
	}

	public T remove() {
		
		//this will throw an exception if we are empty
		T result = element();
		
		//if we are not empty, we will make it to this point
		//here we update our index queue and empty index list
		emptyIndices.offer(indexQueue.remove());
		
		return result;

		
	}

}
