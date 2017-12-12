package eventful.swing;

import javax.swing.JDialog;

import eventful.EventfulType;
import eventful.EventfulTypeListener;
import eventful.IEventfulType;

public class EventfulTypeDialog<T> extends JDialog implements IEventfulType<T>{

	private EventfulType<T> listenee;
	
	public EventfulTypeDialog() {
		listenee = new EventfulType<T>();
	}
	
	public void addListener(EventfulTypeListener<T> l) {
		listenee.addListener(l);
	}

	public void removeAllListeners() {
		listenee.removeAllListeners();
	}

	public void removeListener(EventfulTypeListener<T> l) {
		listenee.removeListener(l);
	}

	public void updateListeners(T message) {
		listenee.updateListeners(message);
	}

}
