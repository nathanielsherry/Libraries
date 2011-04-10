package eventful;

public abstract class QueuedEventfulEnumListener<T extends Enum<T>> extends QueuedEventfulTypeListener<T> {

	public QueuedEventfulEnumListener(int msDelay) {
		super(msDelay);
	}

}
