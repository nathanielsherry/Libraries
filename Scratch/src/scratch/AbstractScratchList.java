package scratch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import fava.functionable.FList;
import fava.functionable.Functionable;
import scitypes.Range;
import scitypes.RangeSet;


/**
 * AbstractScratchList is an implementation of the List interface which writes 
 * out values to a temporary file, rather than store elements in memory. 
 * This means that get operations are idempotent when there are no 
 * intervening set operations, even if the accessed elements are 
 * modified externally. AbstractScratchList does not define the manner in which
 * values are (de)serialized, leaving that detail up to specific 
 * implementations.
 * @author Nathaniel Sherry
 *
 * @param <T> AbstractScratchList will be a list of values of type T
 */

public abstract class AbstractScratchList<T> extends Functionable<T> implements List<T>{


	
	//stores the locations of all entries as offset/length pairs
	private List<Range> elementPositions;
	private RangeSet discardedRanges;
	

	private File file;
	private RandomAccessFile raf;
	
	
	/**
	 * Create a new AbstractScratchList
	 * @param name the name prefix to give the temporary file
	 * @throws IOException
	 */
	public AbstractScratchList(String name) throws IOException
	{
		
		
		file = File.createTempFile(name + " - Scratch List [temp - ", "]");
		file.deleteOnExit();
		
		init();
				
	}
	
	public AbstractScratchList(File file) throws IOException
	{
		this.file = file;
		
	}
	
	private void init() throws FileNotFoundException
	{
		elementPositions = new FList<Range>();
		discardedRanges = new RangeSet();
		raf = new RandomAccessFile(file, "rw");
	}
	
	protected AbstractScratchList()
	{
		
	}
	
	protected AbstractScratchList(File temp, RandomAccessFile raf, List<Range> positions, RangeSet discarded)
	{
		elementPositions = FList.wrap(positions);
		discardedRanges = discarded;
		this.file = temp;
		this.raf = raf;
	}
	
	protected final void makeSublist(AbstractScratchList<T> target, int startIndex, int endIndex)
	{
		target.elementPositions = elementPositions.subList(startIndex, endIndex);
		target.discardedRanges = discardedRanges;
		target.file = file;
		target.raf = raf;
	}
	
	
	protected abstract byte[] encodeObject(T element) throws IOException;
	
	
	protected abstract T decodeObject(byte[] byteArray) throws IOException;


	private void addEntry(int index, T element)
	{
		

		try {
			
			long currentLength = raf.length();
			long writePosition = currentLength;
			
			byte[] encoded = encodeObject(element);
			final int encodedLength = encoded.length;
			
			List<Range> bigRanges = discardedRanges.getRanges().stream().filter(r -> r.size() >= encodedLength).collect(Collectors.toList());			
			
						
			bigRanges.sort((o1, o2) -> {
				Integer s1 = o1.size();
				Integer s2 = o2.size();
				return s2.compareTo(s1);
			});
			
			
			if (bigRanges.size() != 0)
			{
				writePosition = bigRanges.get(0).getStart();
			}
			
			raf.seek(writePosition);
			raf.write(encoded);
			
			if (index >= elementPositions.size())
			{
				for (int i = elementPositions.size(); i <= index; i++)
				{
					elementPositions.add(null);
				}
			}
			Range elementPosition = new Range((int)writePosition, (int)writePosition+encoded.length-1);
			elementPositions.set(index, elementPosition);
			discardedRanges.removeRange(elementPosition);
			
		} catch (IOException e)
		{
			throw new UnsupportedOperationException("Cannot write to backend file");
		}
		
		
		
	}
	
	
	
	public synchronized boolean add(T e)
	{
		addEntry(elementPositions.size(), e);
		return true;
		
	}
	
	public synchronized void add(int index, T element)
	{		
		addEntry(index, element);
	}

	public boolean addAll(Collection<? extends T> c)
	{
		for (T t : c)
		{
			add(t);
		}
		return true;
	}

	public boolean addAll(int index, Collection<? extends T> c)
	{
		for (T t : c)
		{
			add(index, t);
			index++;
		}
		return true;
	}

	public void clear()
	{
		elementPositions.clear();
		discardedRanges.clear();
		try {
			raf.seek(0);
		} catch (IOException e)
		{
			throw new UnsupportedOperationException("Cannot write to backend file");
		}
	}

	public boolean contains(Object o)
	{
		for (T t : this)
		{
			if (t.equals(o)) return true;
		}
		return false;
	}

	public boolean containsAll(Collection<?> c)
	{
		for (Object o : c)
		{
			if (!contains(o)) return false;
		}
		return true;
	}

	public synchronized T get(int index)
	{
		if (index >= elementPositions.size()) return null;
		Range position = elementPositions.get(index);
		if (position == null) return null;
		
		long offset = position.getStart();
		int length = position.getStop() - position.getStart() + 1;
		
		byte[] data = new byte[length];
		try
		{
			raf.seek(offset);
			raf.read(data, 0, length);
						
			return decodeObject(data);
		}
		catch (IOException e)
		{
			return null;
		}
		
	}

	public int indexOf(Object o)
	{
		int index = 0;
		for (T t : this)
		{
			if (t == null && o == null) return index;
			if (t != null && t.equals(o)) return index;
			index++;
		}
		return -1;
	}

	public boolean isEmpty()
	{
		return elementPositions.isEmpty();
	}

	public Iterator<T> iterator()
	{
		return new Iterator<T>() {

			int index = 0;
			
			public boolean hasNext()
			{
				return index < elementPositions.size();
			}

			public T next()
			{
				return AbstractScratchList.this.get(index++);
			}

			public void remove()
			{
				AbstractScratchList.this.remove(index);
			}};
	}

	public int lastIndexOf(Object o)
	{
		T t;
		for (int i = size(); i >= 0; i--)
		{
			t = get(i);
			if (t.equals(o)) return i;
		}
		return -1;
	}

	public ListIterator<T> listIterator()
	{
		return listIterator(0);
	}

	public ListIterator<T> listIterator(final int startIndex)
	{
		return new ListIterator<T>() {

			int inext = startIndex;
			int lastReturned = startIndex;
			
			public void add(T t)
			{
				AbstractScratchList.this.add(lastReturned, t);
			}

			public boolean hasNext()
			{
				return inext < elementPositions.size();
			}

			public boolean hasPrevious()
			{
				return inext > 0;
			}

			public T next()
			{
				lastReturned = inext;
				return AbstractScratchList.this.get(inext++);
			}

			public int nextIndex()
			{
				return inext;
			}

			public T previous()
			{
				lastReturned = inext-1;
				return AbstractScratchList.this.get(--inext);
			}

			public int previousIndex()
			{
				return inext-1;
			}

			public void remove()
			{
				AbstractScratchList.this.remove(lastReturned);
				inext--;
			}

			public void set(T t)
			{
				AbstractScratchList.this.set(lastReturned, t);
			}};
	}

	public boolean remove(Object o)
	{
		T t;
		for (int i = 0; i < elementPositions.size(); i++)
		{
			t = get(i);
			if (t.equals(o))
			{
				remove(i);
				return true;
			}
		}
		
		return false;
	}

	public T remove(int index)
	{
		T t = get(index);
		discardedRanges.addRange(  elementPositions.remove(index)  );
		return t;
	}

	public boolean removeAll(Collection<?> c)
	{
		
		if (c == null) return false;
		
		T t;
		
		for (int i = 0; i < elementPositions.size(); i++)
		{
			//deserialize each element in our list only once
			t = get(i);
			
			if (c.contains(t))
			{
				//decrement the counter to make up for the fact that
				//we have removed the current element, and we don't
				//want to skip over an element
				remove(i--);
			}
			
		}
		
		return true;
		
		/*
		 * This is a bad way to do it -- it means that we will have
		 * to deserialize each element in the list m times where m
		 * is the size of the collection except for the last m items 
		 * in the worst case. 
		for (Object t : c)
		{
			remove(t);
		}
		return true;
		*/
	}

	public boolean retainAll(Collection<?> c)
	{
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

	public synchronized T set(int index, T element)
	{

		T old = null;
		
		try{
			old = get(index);
		} catch (Exception e)
		{
			//nothing
		}
		
		if (elementPositions.size() > index)
		{
			//record the old range which is not being used anymore
			Range oldRange = elementPositions.get(index);
			discardedRanges.addRange(oldRange);
		}
		
		addEntry(index, element);
		
		return old;
			
		
	}

	public int size()
	{
		return elementPositions.size();
	}

	public abstract List<T> subList(int fromIndex, int toIndex);

	public Object[] toArray()
	{
		Object[] t = new Object[size()];
		for (int i = 0; i < size(); i++)
		{
			t[i] = get(i);
		}
		return t;
		
	}

	@SuppressWarnings("unchecked")
	public <S> S[] toArray(S[] a)
	{
		S[] s;
		
		if (a.length >= size())
		{
			s = a;
		} else {
			s = (S[])(new Object[size()]);
		}

		for (int i = 0; i < size(); i++)
		{
			s[i] = (S) get(i);
		}
		
		
		return s;
	}
	
	@Override
	protected void finalize()
	{
		try
		{
			raf.close();			
		}
		catch (IOException e)
		{
			
		}
	}
	
	
	public long filesize()
	{
		return elementPositions.stream().map(range -> range.size()).reduce(0, (a, b) -> a + b);
		//return elementPositions.foldr(0, (r, i) -> i + r.size()).longValue();
	}
	
}
