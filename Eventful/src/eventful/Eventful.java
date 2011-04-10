package eventful;


import java.util.LinkedList;
import java.util.List;


/**
 * 
 * A controller for a simple Model/View/Controller system. Contains the mechanism for notifying
 * views of a change to the model via the controller.
 * 
 * @author Nathaniel Sherry, 2009
 * 
 */


public class Eventful implements IEventful
{

	protected final List<EventfulListener>	listeners;


	public Eventful()
	{
		listeners = new LinkedList<EventfulListener>();
	}


	/**
	 * @see eventful.IEventful#addListener(eventful.EventfulListener)
	 */
	public void addListener(EventfulListener l)
	{
		listeners.add(l);
	}


	/**
	 * @see eventful.IEventful#removeListener(eventful.EventfulListener)
	 */
	public void removeListener(final EventfulListener l)
	{
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()	{
				listeners.remove(l);
			}
		});
		
		
	}
	
	/**
	 * @see eventful.IEventful#removeAllListeners()
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
	 * @see eventful.IEventful#updateListeners()
	 */
	public void updateListeners()
	{

		if (listeners.size() == 0) return;

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()	{
		
				for (EventfulListener l : listeners) {
					l.change();
				}
				
			}
		});

	}


}
