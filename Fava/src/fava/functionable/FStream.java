package fava.functionable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;


import fava.Fn;
import fava.Functions;
import fava.datatypes.Maybe;
import fava.signatures.FnCombine;
import fava.signatures.FnFold;
import fava.signatures.FnMap;

/**
 * Experimental mechanism for defining complex processing of data without the data being
 * available. Data can then be fed into the 
 * @author nathaniel
 *
 * @param <T1>
 */

public class FStream<T1> extends Functionable<T1> 
{

	private LinkedBlockingQueue<Maybe<T1>> queue;
	private static int defaultQueueSize = 50;
	private int queueSize;
	
	/**
	 * Creates a new FStream with the default buffer size
	 */
	public FStream() {
		this(defaultQueueSize);
	}
	
	/**
	 * Creates a new FStream with the specified buffer size
	 * @param size the size of the buffer for this Stream
	 */
	public FStream(int size) {
		queueSize = size;
		queue = new LinkedBlockingQueue<Maybe<T1>>(size);
	}
	
	/**
	 * Read data from an Iterable source into this stream. This call returns 
	 * immediately, and may continue to reads elements from this Iterable afterwards. 
	 * If the stream's buffer is full, no more elements will be added until there is room.
	 * 
	 * @param source the source to read values from
	 */
	public void fromSource(final Iterable<T1> source)
	{
		asThread(new Runnable() {
			
			public void run() {
				
				for (T1 t : source)
				{
					try {
						queue.put(new Maybe<T1>(t));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				close();
				
			}
		});
	}
	
	/**
	 * Reads from the source given, adding elements to the stream, or dropping them
	 * if the stream cannot accommodate more elements at this time. This method returns 
	 * immediately, and may continue reading from the Iterable afterwards.
	 * @param source
	 */
	public void fromLiveSource(final Iterable<T1> source)
	{
		asThread(new Runnable() {
			
			public void run() {
				
				for (T1 t : source)
				{
					try {
						queue.add(new Maybe<T1>(t));
					} catch (IllegalStateException e) {
						//nothing
					}
				}
				
				close();
				
			}
		});
	}
	
	
	/**
	 * Writes the contents of this stream out to an FList. Unlike toSink(), 
	 * toSinkStreaming() returns the resulting list immediately, continuing 
	 * to add elements to the list afterwards. Note that the default backing 
	 * for an FList is not threadsafe, and any parallel access should be synchronized
	 * externally.
	 * 
	 * @return A list which the contents of the stream will be written to
	 */
	public FList<T1> toSinkStreaming()
	{
		final FList<T1> target = new FList<T1>();
		
		asThread(new Runnable() {
			
			public void run() {
				
				for (T1 t : FStream.this)
				{
					synchronized (target) {
						target.add(t);	
					}
					
				}
				
			}
		});
		
		
		return target;
	}
	
	
	private void asThread(Runnable r)
	{
		new Thread(r).start();
	}
	
	
	/**
	 * Place an element into the stream
	 * @param value
	 */
	public void put(T1 value)
	{
		try {
			queue.put(new Maybe<T1>(value));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Place a value into the stream if there is room, discarding it otherwise.
	 * @param value
	 */
	public boolean putLive(T1 value)
	{
		return queue.add(new Maybe<T1>(value));
	}
	
	private void close()
	{
		try {
			queue.put(new Maybe<T1>());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Iterator<T1> iterator() {
		
		return new Iterator<T1>(){


			private Maybe<Maybe<T1>> item = new Maybe<Maybe<T1>>();
			
			public boolean hasNext() {
				
				if (! item.is() )
				{
					try {
						item.set(queue.take());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				return item.get().is();
				
				
			}

			public T1 next() {
				
				if (! item.is() )
				{
					try {
						return queue.take().get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					throw new NullPointerException();
				} else {
					T1 val = item.get().get();
					item.set();
					return val;
				}


					
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}};
		
	}
	
	
	
	
	
	
	/* Functionable Overrides */

	@Override
	public <T2> FStream<T2> map(final FnMap<T1, T2> f)
	{
		final FStream<T2> target = new FStream<T2>(queueSize);
		
		
		asThread(new Runnable() {
			
			public void run() {
				
				for (T1 t : FStream.this)
				{
					target.put(f.f(t));
				}
				target.close();
				
			}
		});
		
		return target;
		
	}
	
	@Override
	public FStream<T1> filter(final FnMap<T1, Boolean> f)
	{
		
		final FStream<T1> target = new FStream<T1>(queueSize);
		
		
		asThread(new Runnable() {
			
			public void run() {
				
				for (T1 t : FStream.this)
				{
					if (f.f(t)) target.put(t);
				}
				target.close();
				
			}
		});
		
		return target;
		
	}
	
	@Override
	public T1 fold(FnFold<T1, T1> f)
	{
		return Fn.fold(this, f);
	}
	
	@Override
	public <T2> T2 fold(T2 base, FnFold<T1, T2> f)
	{
		return Fn.fold(this, base, f);
	}
	
	@Override
	public FStream<T1> take(final int number)
	{
		final FStream<T1> target = new FStream<T1>(queueSize);
		
		
		asThread(new Runnable() {
			
			public void run() {
				
				int count = 0;
				for (T1 t : FStream.this)
				{
					target.put(t);
					count++;
					if (count >= number) break;
				}
				target.close();
				
			}
		});
		
		return target;
	}
	
	@Override
	public FStream<T1> takeWhile(final FnMap<T1, Boolean>f)
	{
		final FStream<T1> target = new FStream<T1>(queueSize);
		
		asThread(new Runnable() {
			
			public void run() {
				
				for (T1 t : FStream.this)
				{
					if (! f.f(t)) break; 
					target.put(t);
				}
				target.close();
				
			}
		});
		
		return target;
	}
	
	@Override
	public String show()
	{
		return "[>FStream>]";
	}
	
	@Override
	public String show(String separator)
	{
		return "[>FStream>]";
	}	
	
	@Override
	public FStream<Functionable<T1>> chunk(final int size)
	{
		
		final FStream<Functionable<T1>> target = new FStream<Functionable<T1>>(queueSize);
		
		
		asThread(new Runnable() {
			
			public void run() {
				
				FList<T1> chunk = null;
				
				int count = 0;
				for (T1 t : FStream.this)
				{
					
					if (count == 0)
					{
						chunk = new FList<T1>();						
					}
					
					chunk.add(t);
					count++;
					
					if (count >= size) 
					{
						count = 0;
						target.put(chunk);
						chunk = null;
					}
				}
				
				if (chunk != null) target.put(chunk);
				
				target.close();
				
			}
		});
		
		return target;
		
		
	}
	
	
	@Override
	public FStream<Functionable<T1>> group()
	{
		return groupBy(Functions.<T1>equiv());
	}
	
	public FStream<Functionable<T1>> group(int size)
	{
		return groupBy(Functions.<T1>equiv(), size);
	}
	
	@Override
	public FStream<Functionable<T1>> groupBy(final FnCombine<T1, Boolean> f)
	{
		return groupBy(f, 10);
	}
	
	public FStream<Functionable<T1>> groupBy(final FnCombine<T1, Boolean> f, final int size)
	{
		final FStream<Functionable<T1>> target = new FStream<Functionable<T1>>(queueSize);
		
		new Thread(new Runnable() {
			
			Map<T1, FList<T1>> equivmap = new HashMap<T1, FList<T1>>();
			FStream<T1> source = FStream.this;
			

			private FnMap<T1, Boolean> compareFunction(final T1 value)
			{
				return new FnMap<T1, Boolean>(){

					public Boolean f(T1 element) {
						return f.f(value, element);
					}};
			}
			
			private T1 getKeyFromValue(final T1 value)
			{
				FList<T1> keys = Fn.filter(equivmap.keySet(), compareFunction(value));
				
				if (keys.size() == 0) return null;
				return keys.head();
			}
			
			private boolean keyInMap(final T1 value)
			{
				return Fn.includeBy(equivmap.keySet(), compareFunction(value));
			}
			
			private FList<T1> putValueInMap(final T1 value)
			{
				T1 key = getKeyFromValue(value);
				
				FList<T1> list = equivmap.get(key);
				list.add(value);
				return list;
			}
			
			public void run() {
				
				
				for (T1 t : source)
				{
					
					if (keyInMap(t))
					{
						//the map already contains this kind of element, so there is a list on the go
						FList<T1> list = putValueInMap(t);
						if (list.size() == size)
						{
							equivmap.remove(getKeyFromValue(t));
							target.put(list);
						}
						
					} else {
						//the map does not contain an equiv element, so there is not a list on the go
						equivmap.put(t, new FList<T1>(t));
					}
					
				}
				
				//gather up the remaining lists, and submit them
				for (FList<T1> l : equivmap.values())
				{
					target.put(l);
				}
				
				
			}
			
		}).start();
		
		return target;
		
	}
	
	
	
	
	

}
