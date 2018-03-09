package eventful;

import java.util.LinkedList;
import java.util.List;

public class EventfulEnum<T extends Enum<T>> implements IEventfulEnum<T>
{

	protected final List<EventfulEnumListener<T>>	listeners;
	
	
	public EventfulEnum() {
		listeners = new LinkedList<EventfulEnumListener<T>>();
	}
	
	public synchronized void addListener(EventfulEnumListener<T> l)
	{
		listeners.add(l);
	}


	public synchronized void removeListener(final EventfulEnumListener<T> l)
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
			
				synchronized(EventfulEnum.this) {	
					for (EventfulEnumListener<T> l : listeners) {		
						l.change(message);
					}
			}}
		});

	}
	
}
