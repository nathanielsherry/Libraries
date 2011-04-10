package eventful;



public interface IEventful
{

	public abstract void addListener(EventfulListener l);


	public abstract void removeListener(final EventfulListener l);


	public abstract void removeAllListeners();


	public abstract void updateListeners();

}