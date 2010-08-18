package scitypes.filebacked;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import scitypes.Spectrum;

import fava.Fn;
import fava.Functions;
import fava.datatypes.Range;
import fava.datatypes.RangeSet;
import fava.lists.FList;
import fava.signatures.FnMap;

/**
 * TempFileList is an implementation of the List interface which writes out values to a temporary file, rather than store elements in memory. This means that get operations are idempotent when there are no intervening set operations, even if the accessed elements are modified externally 
 * @author Nathaniel Sherry, 2010
 *
 */

public class FileBackedList<T extends Serializable> implements List<T>
{


	public static void main(String args[])
	{
		Spectrum s1 = new Spectrum(10, 4);
		Spectrum s2 = new Spectrum(5, 3);
		Spectrum s3 = new Spectrum(20, 2);
		Spectrum s4 = new Spectrum(20, 7);
		Spectrum s5 = new Spectrum(9, 20);
		
		try {
			
			FileBackedList<Spectrum> list = new FileBackedList<Spectrum>("test");
			
			list.add(s1);
			list.add(s5);
			list.add(s2);
			
			list.set(0, s3);
			list.set(2, s4);
			
			list.add(s5);
			list.add(s4);
			
			System.out.println(list.get(0).show());
			System.out.println(list.get(2).show());
			System.out.println(list.get(3).show());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//stores the locations of all entries as offset/length pairs
	private List<Range> elementPositions;
	private RangeSet discardedRanges;
	

	private File temp;
	private RandomAccessFile raf;
	
	
	/**
	 * Createa new TempFileList
	 * @param name the name prefix to give the temporary file
	 * @param encode a function which can take an element T, and serialize it for output to the temporary file
	 * @param decode a function which can take a serialized representation of T and deserialize it into an element T
	 * @throws IOException
	 */
	public FileBackedList(String name) throws IOException
	{
		elementPositions = new ArrayList<Range>();
		discardedRanges = new RangeSet();
		temp = File.createTempFile("peakaboo - " + name , "list");
		raf = new RandomAccessFile(temp, "rw");
		
		temp.deleteOnExit();
				
				
	}
	
	protected FileBackedList(File temp, RandomAccessFile raf, List<Range> positions, RangeSet discarded)
	{
		elementPositions = positions;
		discardedRanges = discarded;
		this.temp = temp;
		this.raf = raf;
		
		
	}
	
	private byte[] encodeObject(T element) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		oos = new ObjectOutputStream(baos);
		oos.writeObject(element);
		return baos.toByteArray();
	}
	
	private T decodeObject(byte[] byteArray) throws IOException
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
		ObjectInputStream ois = new ObjectInputStream(bais);
		try {
			return (T)ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	private void addEntry(int index, T element)
	{
		

		try {
			
			long currentLength = raf.length();

			byte[] encoded = encodeObject(element);
			
			long writePosition = currentLength;
			
			final int encodedLength = encoded.length;
			FList<Range> bigRanges = discardedRanges.getRanges().filter(new FnMap<Range, Boolean>() {

				public Boolean f(Range r) {
					return r.size() >= encodedLength;
				}
			});
			
			Fn.sortBy(bigRanges, new Comparator<Range>() {

				public int compare(Range o1, Range o2) {
					Integer s1 = o1.size();
					Integer s2 = o2.size();
					
					return s2.compareTo(s1);
					
				}
			}, Functions.<Range>id());
			
			
			if (bigRanges.size() != 0)
			{
				writePosition = bigRanges.head().getStart();
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
		boolean all = true;
		for (Object o : c)
		{
			all &= contains(o);
			if (!all) return all;
		}
		return all;
	}

	public synchronized T get(int index)
	{
			
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
				return FileBackedList.this.get(index++);
			}

			public void remove()
			{
				FileBackedList.this.remove(index);
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
				FileBackedList.this.add(lastReturned, t);
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
				return FileBackedList.this.get(inext++);
			}

			public int nextIndex()
			{
				return inext;
			}

			public T previous()
			{
				lastReturned = inext-1;
				return FileBackedList.this.get(--inext);
			}

			public int previousIndex()
			{
				return inext-1;
			}

			public void remove()
			{
				FileBackedList.this.remove(lastReturned);
				inext--;
			}

			public void set(T t)
			{
				FileBackedList.this.set(lastReturned, t);
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
		elementPositions.remove(index);
		return t;
	}

	public boolean removeAll(Collection<?> c)
	{
		for (Object t : c)
		{
			remove(t);
		}
		return true;
	}

	public boolean retainAll(Collection<?> c)
	{
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

	public List<T> subList(int fromIndex, int toIndex)
	{	
		return new FileBackedList<T>(temp, raf, elementPositions.subList(fromIndex, toIndex), discardedRanges);
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
	
	
	

}
