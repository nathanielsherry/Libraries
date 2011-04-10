package plural.workers;

/**
 * Dummy Task used as a place-filler when a job will not fit neatly into a task object, or for when there is no
 * advantage in doing so. This helps a UI to be more informative.
 * 
 * @author Nathaniel Sherry, 2009
 * 
 */

public class EmptyMap extends PluralMap<Boolean, Boolean>
{

	public EmptyMap(String name)
	{
		super(name);
	}



	@Override
	public synchronized void advanceState()
	{

		switch (state) {
			case UNSTARTED:
				state = State.STALLED;
				break;
			case STALLED:
				state = State.COMPLETED;
				break;
			default:
				break;
		}

		updateListeners();

	}



	public Boolean f(Boolean element) {
		return true;
	}


}
