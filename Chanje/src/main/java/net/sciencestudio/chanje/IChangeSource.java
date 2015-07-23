package net.sciencestudio.chanje;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import javafx.application.Platform;

public abstract class IChangeSource implements ChangeSource {

	protected final List<Consumer<Object>> listeners;

	public IChangeSource() {
		listeners = new LinkedList<Consumer<Object>>();
	}

	@Override
	public synchronized void listen(final Consumer<Object> l) {
		listeners.add(l);
	}

	@Override
	public synchronized void unlisten(final Consumer<Object> l) {
		listeners.remove(l);
	}
	
	protected synchronized void broadcast(final Object message) {

		if (listeners.size() == 0)
			return;

		Platform.runLater(() -> {
			synchronized (IChangeSource.this) {
				for (Consumer<Object> l : new ArrayList<>(listeners)) {
					l.accept(message);
				}
			}
		});

	}
	
}
