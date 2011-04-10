package eventful;



public interface IEventfulType<T>
{

	public abstract void addListener(EventfulTypeListener<T> l);


	public abstract void removeListener(final EventfulTypeListener<T> l);


	public abstract void removeAllListeners();


	public abstract void updateListeners(final T message);

}