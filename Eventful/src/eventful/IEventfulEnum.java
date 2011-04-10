package eventful;



public interface IEventfulEnum<T extends Enum<T>>
{

	public abstract void addListener(EventfulEnumListener<T> l);


	public abstract void removeListener(final EventfulEnumListener<T> l);


	public abstract void removeAllListeners();


	public abstract void updateListeners(final T message);

}