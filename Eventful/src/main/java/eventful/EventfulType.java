package eventful;

import java.util.LinkedList;
import java.util.List;

public class EventfulType<T> implements IEventfulType<T>
{

	protected final List<EventfulTypeListener<T>>	listeners;
	
	
	public EventfulType() {
		
		listeners = new LinkedList<EventfulTypeListener<T>>();
		
	}

	public synchronized void addListener(final EventfulTypeListener<T> l)
	{
		listeners.add(l);
	}



	public synchronized void removeListener(final EventfulTypeListener<T> l)
	{
		listeners.remove(l);
	}
	

	public synchronized void removeAllListeners()
	{
		listeners.clear();
	}



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
