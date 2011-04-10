package eventful;

/**
 * 
 * Basic listener for a simple Model/View/Controller system. Receives an update when a change occurs.
 * 
 * @author Nathaniel Sherry, 2009
 *
 */

public interface EventfulTypeListener<T> {

	public void change(T message);
	
}