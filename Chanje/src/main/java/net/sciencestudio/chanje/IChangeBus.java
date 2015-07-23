package net.sciencestudio.chanje;

public class IChangeBus extends IChangeSource implements ChangeBus {


	@Override
	public synchronized void broadcast(final Object message) {
		super.broadcast(message);
	}

}
