package scratch;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public final class ScratchList<T extends Serializable> implements IScratchList<T> {

	protected List<byte[]> data;
	protected ScratchEncoder<T> encoder;
	
	
	
	public ScratchEncoder<T> getEncoder() {
		return encoder;
	}
	public void setEncoder(ScratchEncoder<T> encoder) {
		this.encoder = encoder;
	}
	
	protected byte[] encode(T data) throws ScratchException {
		return encoder.encode(data);
	}
	protected T decode(byte[] data) throws ScratchException {
		return encoder.decode(data);
	}
	
	public ScratchList() {
		data = new ArrayList<>();
	}
	
	//use your own backing -- also sublist constructor
	public ScratchList(List<byte[]> data) {
		this.data = data;
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return data.contains(encode((T) o));
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			Iterator<byte[]> delegate = data.iterator();
			
			@Override
			public boolean hasNext() {
				return delegate.hasNext();
			}

			@Override
			public T next() {
				return decode(delegate.next());
			}
		};
	}

	@Override
	public Object[] toArray() {
		Object[] arr = data.toArray();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = get(i);
		}
		return arr;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		T[] arr = data.toArray(a);
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (T) get(i);
		}
		return arr;
	}

	@Override
	public boolean add(T e) {
		return data.add(encode(e));
	}

	@Override
	public boolean remove(Object o) {
		return data.remove(encode((T) o));
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) { return false; }
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean flag = false;
		for (T o : c) {
			flag |= add(o);
		}
		return flag;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		for (T o : c) {
			add(index, o);
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean flag = false;
		for (Object o : c) {
			flag |= remove(o);
		}
		return flag;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if (c == null) return false;
		
		ListIterator<T> li = listIterator();
		
		boolean modified = false;
		while(li.hasNext())
		{
			if ( ! c.contains(li.next()) )
			{
				li.remove();
				modified = true;
			}
		}
		
		return modified;
	}

	@Override
	public void clear() {
		data.clear();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ScratchList) {
			return data.equals(((ScratchList) o).data);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return data.hashCode();
	}

	@Override
	public T get(int index) {
		return decode(data.get(index));
	}

	@Override
	public T set(int index, T element) {
		data.set(index, encode(element));
		return element;
	}

	@Override
	public void add(int index, T element) {
		data.add(index, encode(element));
	}

	@Override
	public T remove(int index) {
		return decode(data.remove(index));
	}

	@Override
	public int indexOf(Object o) {
		return data.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return data.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return new ListIterator<T>() {

			ListIterator<byte[]> delegate = data.listIterator(index);
			
			@Override
			public boolean hasNext() {
				return delegate.hasNext();
			}

			@Override
			public T next() {
				return decode(delegate.next());
			}

			@Override
			public boolean hasPrevious() {
				return delegate.hasPrevious();
			}

			@Override
			public T previous() {
				return decode(delegate.previous());
			}

			@Override
			public int nextIndex() {
				return delegate.nextIndex();
			}

			@Override
			public int previousIndex() {
				return delegate.previousIndex();
			}

			@Override
			public void remove() {
				delegate.remove();
			}

			@Override
			public void set(T e) {
				delegate.set(encode(e));
			}

			@Override
			public void add(T e) {
				delegate.add(encode(e));
			}
		};
	}
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return new ScratchList<>(data.subList(fromIndex, toIndex));
	}
	
}
