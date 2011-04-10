package eventful.swing;

import javax.swing.JDialog;

import eventful.Eventful;
import eventful.EventfulListener;
import eventful.IEventful;

public class EventfulDialog extends JDialog implements IEventful {

	private Eventful listenee;
	
	public EventfulDialog() {
		listenee = new Eventful();
	}
	
	public void addListener(EventfulListener l) {
		listenee.addListener(l);
	}

	public void removeAllListeners() {
		listenee.removeAllListeners();
	}

	public void removeListener(EventfulListener l) {
		listenee.removeListener(l);
	}

	public void updateListeners() {
		listenee.updateListeners();
	}

}
