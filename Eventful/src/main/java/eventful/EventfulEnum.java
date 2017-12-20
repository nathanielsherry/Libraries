package eventful;

import java.util.LinkedList;
import java.util.List;

public class EventfulEnum<T extends Enum<T>> implements IEventfulEnum<T>
{

	protected final List<EventfulEnumListener<T>>	listeners;
	
	
	public EventfulEnum() {
		
		listeners = new LinkedList<EventfulEnumListener<T>>();
		
	}
	
	/**
	 * @see eventful.IEventfulEnum#addListener(eventful.EventfulEnumListener)
	 */
	public synchronized void addListener(EventfulEnumListener<T> l)
	{
		listeners.add(l);
	}


	/**
	 * @see eventful.IEventfulEnum#removeListener(eventful.EventfulTypeListener)
	 */
	public void removeListener(final EventfulEnumListener<T> l)
	{
		
		EventfulConfig.runThread.accept(new Runnable() {
			public void run()	{ 
			
				synchronized(EventfulEnum.this){
					listeners.remove(l);
			}}
		});
		
		
	}
	
	/**
	 * @see eventful.IEventfulEnum#removeAllListeners()
	 */
	public void removeAllListeners()
	{
		EventfulConfig.runThread.accept(new Runnable() {
			public void run() { 
			
				synchronized(EventfulEnum.this){
					listeners.clear();
			}}
		});
	}


	/**
	 * @see eventful.IEventfulEnum#updateListeners(T)
	 */
	public void updateListeners(final T message)
	{

		if (listeners.size() == 0) return;

		EventfulConfig.runThread.accept(new Runnable() {
			public void run()	{ 
			
				synchronized(EventfulEnum.this) {	
					for (EventfulEnumListener<T> l : listeners) {		
						l.change(message);
					}
			}}
		});

	}
	
}
