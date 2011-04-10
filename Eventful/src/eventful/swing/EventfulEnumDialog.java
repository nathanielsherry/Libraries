package eventful.swing;

import javax.swing.JDialog;

import eventful.EventfulEnum;
import eventful.EventfulEnumListener;
import eventful.IEventfulEnum;

public class EventfulEnumDialog<T extends Enum<T>> extends JDialog implements IEventfulEnum<T>{

	private EventfulEnum<T> listenee;
	
	public EventfulEnumDialog() {
		listenee = new EventfulEnum<T>();
	}
	
	public void addListener(EventfulEnumListener<T> l) {
		listenee.addListener(l);
	}

	public void removeAllListeners() {
		listenee.removeAllListeners();
	}

	public void removeListener(EventfulEnumListener<T> l) {
		listenee.removeListener(l);
	}

	public void updateListeners(T message) {
		listenee.updateListeners(message);
	}

}
