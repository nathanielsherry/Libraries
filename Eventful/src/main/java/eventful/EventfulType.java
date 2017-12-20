package eventful;

import java.util.LinkedList;
import java.util.List;

public class EventfulType<T> implements IEventfulType<T>
{

	protected final List<EventfulTypeListener<T>>	listeners;
	
	
	public EventfulType() {
		
		listeners = new LinkedList<EventfulTypeListener<T>>();
		
	}
	/**
	 * @see eventful.IEventfulType#addListener(eventful.EventfulTypeListener)
	 */
	public synchronized void addListener(final EventfulTypeListener<T> l)
	{
		
		EventfulConfig.runThread.accept(new Runnable() {
			public void run()	{
				synchronized(EventfulType.this){
					listeners.add(l);
				}
			}
		});
	}


	/**
	 * @see eventful.IEventfulType#removeListener(eventful.EventfulTypeListener)
	 */
	public void removeListener(final EventfulTypeListener<T> l)
	{
		
		EventfulConfig.runThread.accept(new Runnable() {
			public void run()	{
				synchronized(EventfulType.this){
					listeners.remove(l);
				}
			}
		});
		
		
	}
	
	/**
	 * @see eventful.IEventfulType#removeAllListeners()
	 */
	public void removeAllListeners()
	{
		EventfulConfig.runThread.accept(new Runnable() {
			public void run()	{
				synchronized(EventfulType.this){
					listeners.clear();
				}
			}
		});
	}


	/**
	 * @see eventful.IEventfulType#updateListeners(T)
	 */
	public void updateListeners(final T message)
	{

		if (listeners.size() == 0) return;

		EventfulConfig.runThread.accept(new Runnable() {
			public void run()	{
				synchronized(EventfulType.this){
					for (EventfulTypeListener<T> l : listeners) {
						l.change(message);
					}
				}
				
			}
		});

	}
	
}
