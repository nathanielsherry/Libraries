package scratch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import scitypes.LongRange;
import scitypes.LongRangeSet;
import scratch.encoders.serializers.SerializingEncoder;



public class ScratchDiskList<T> implements IScratchList<T> {


	
	//stores the locations of all entries as offset/length pairs
	private List<LongRange> elementPositions;
	private LongRangeSet discardedRanges;
	

	private File file;
	private RandomAccessFile raf;
	
	private ScratchEncoder<T> encoder;
	
	
	
	public ScratchDiskList() throws IOException
	{
		this(new SerializingEncoder());
	}
	
	/**
	 * Create a new AbstractScratchList
	 * @param name the name prefix to give the temporary file
	 * @throws IOException
	 */
	public ScratchDiskList(ScratchEncoder<T> encoder) throws IOException
	{
		this.encoder = encoder;
		file = File.createTempFile("Scratch List [temp - ", "]");
		file.deleteOnExit();
		init();	
	}
	

	private void init() throws FileNotFoundException
	{
		elementPositions = new ArrayList<LongRange>();
		discardedRanges = new LongRangeSet();
		raf = new RandomAccessFile(file, "rw");
	}
	

	
	protected ScratchDiskList(File temp, RandomAccessFile raf, List<LongRange> positions, LongRangeSet discarded)
	{
		elementPositions = new ArrayList<>(positions);
		discardedRanges = discarded;
		this.file = temp;
		this.raf = raf;
	}
	
	
	
	public ScratchEncoder<T> getEncoder() {
		return encoder;
	}


	public void setEncoder(ScratchEncoder<T> encoder) {
		this.encoder = encoder;
	}


	protected final void makeSublist(ScratchDiskList<T> target, int startIndex, int endIndex)
	{
		target.elementPositions = elementPositions.subList(startIndex, endIndex);
		target.discardedRanges = discardedRanges;
		target.file = file;
		target.raf = raf;
	}
	
	
	protected byte[] encodeObject(T element) throws IOException {
		return this.encoder.encode(element);
	}
	
	
	protected T decodeObject(byte[] byteArray) throws IOException {
		return this.encoder.decode(byteArray);
	}


	private void addEntry(int index, T element)
	{
		

		try {
			
			long currentLength = raf.length();
			long writePosition = currentLength;
			
			byte[] encoded = encodeObject(element);
			final int encodedLength = encoded.length;
			
			List<LongRange> bigRanges = discardedRanges.getRanges().stream().filter(r -> r.size() >= encodedLength).collect(Collectors.toList());			
			
						
			bigRanges.sort((o1, o2) -> {
				Long s1 = o1.size();
				Long s2 = o2.size();
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
			LongRange elementPosition = new LongRange((long)writePosition, (long)writePosition+encoded.length-1);
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
		LongRange position = elementPositions.get(index);
		if (position == null) return null;
		
		long offset = position.getStart();
		//The positions may be >MAXINT, but the length really shouldn't be
		int length = (int)(position.getStop() - position.getStart() + 1);
		
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
				return ScratchDiskList.this.get(index++);
			}

			public void remove()
			{
				ScratchDiskList.this.remove(index);
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
				ScratchDiskList.this.add(lastReturned, t);
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
				return ScratchDiskList.this.get(inext++);
			}

			public int nextIndex()
			{
				return inext;
			}

			public T previous()
			{
				lastReturned = inext-1;
				return ScratchDiskList.this.get(--inext);
			}

			public int previousIndex()
			{
				return inext-1;
			}

			public void remove()
			{
				ScratchDiskList.this.remove(lastReturned);
				inext--;
			}

			public void set(T t)
			{
				ScratchDiskList.this.set(lastReturned, t);
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
			LongRange oldRange = elementPositions.get(index);
			discardedRanges.addRange(oldRange);
		}
		
		addEntry(index, element);
		
		return old;
			
		
	}

	public int size()
	{
		return elementPositions.size();
	}

	
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		throw new ScratchException(new RuntimeException("Cannot Create SubList of File Backed List"));
	}
	


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
		return elementPositions.stream().map(range -> range.size()).reduce(0l, (a, b) -> a + b);
		//return elementPositions.foldr(0, (r, i) -> i + r.size()).longValue();
	}
	
}
