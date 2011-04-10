package eventful.swing;

import javax.swing.JFrame;

import eventful.EventfulType;
import eventful.EventfulTypeListener;
import eventful.IEventfulType;

public class EventfulTypeFrame<T> extends JFrame implements IEventfulType<T>{

	private EventfulType<T> listenee;
	
	public EventfulTypeFrame() {
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
