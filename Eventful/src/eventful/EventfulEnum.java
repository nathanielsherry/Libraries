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
	public void addListener(EventfulEnumListener<T> l)
	{
		listeners.add(l);
	}


	/**
	 * @see eventful.IEventfulEnum#removeListener(eventful.EventfulTypeListener)
	 */
	public void removeListener(final EventfulEnumListener<T> l)
	{
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()	{
				listeners.remove(l);
			}
		});
		
		
	}
	
	/**
	 * @see eventful.IEventfulEnum#removeAllListeners()
	 */
	public void removeAllListeners()
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()	{
				listeners.clear();
			}
		});
	}


	/**
	 * @see eventful.IEventfulEnum#updateListeners(T)
	 */
	public void updateListeners(final T message)
	{

		if (listeners.size() == 0) return;

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()	{
		
				for (EventfulEnumListener<T> l : listeners) {
					
					l.change(message);

				}
				
			}
		});

	}
	
}
