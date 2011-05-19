package plural.executor;

import eventful.Eventful;


/**
 * 
 * A Map object encapsulates a unit of work applied to a generic data point. A Task will process one unique
 * numerical work ticket at a time. This allows it to easily act as a multi-threaded for-loop or the inner
 * block of a map() function in a functional programming language. This can then be used to process large data
 * sets in various ways with minimal effort using an implementation of the {@link TicketingUITaskExecutor} or other
 * means.
 * 
 * @author Nathaniel Sherry, 2009
 * 
 */

public class Plural extends Eventful implements Cloneable
{

	protected String		name;

	protected ExecutorState	state;
	protected int			workUnits;
	protected int			workUnitsCompleted;
	
	public PluralSet<?>		pluralSet;
	
	private boolean			stalling = false; 
	
	

	public Plural() {
		init("", -1);
	}
	
	public Plural(String name) {
		init(name, -1);
	}
	
	private void init(String name, int size)
	{
		this.name = name;
		state = ExecutorState.UNSTARTED;
		workUnitsCompleted = 0;
		setWorkUnits(size);
	}
	

	/**
	 * Gets the name of this Task
	 * 
	 * @return name of this Task
	 */
	public String getName()
	{
		return name;
	}

	
	public void setStalling(boolean stalling)
	{
		this.stalling = stalling;
	}

	
	/**
	 * Gets the {@link ExecutorState} of this Task
	 * 
	 * @return the current State of this Task
	 */
	public synchronized ExecutorState getState()
	{
		return state;
	}


	/**
	 * Advances the {@link ExecutorState} of the current Task by 1
	 */
	public synchronized void advanceState()
	{

		switch (state) {
			case UNSTARTED:
				state = stalling? ExecutorState.STALLED : ExecutorState.WORKING;
				break;
			case WORKING:
			case STALLED:
				state = ExecutorState.COMPLETED;
				break;
			default:
				break;
		}

		updateListeners();

	}


	/**
	 * Mark this Task as having been {@link ExecutorState#SKIPPED}
	 */
	public synchronized void markTaskSkipped()
	{
		state = ExecutorState.SKIPPED;
		updateListeners();
	}


	/**
	 * Indicate that a work unit has been completed.
	 */
	public synchronized void workUnitCompleted()
	{
		workUnitCompleted(1);
	}


	/**
	 * Indicate that several work units have been completed
	 * 
	 * @param unitCount
	 *            number of work units completed
	 */
	public synchronized void workUnitCompleted(int unitCount)
	{
		this.workUnitsCompleted += unitCount;
		updateListeners();
	}


	/**
	 * Gets the progress of this Task
	 * 
	 * @return the current progress
	 */
	public synchronized double getProgress()
	{
		return ((double) workUnitsCompleted) / workUnits;
	}


	/**
	 * Gets the number of work units in this task. This will only be a valid value once
	 * {@link #setWorkUnits(int)} has been called, either explicitly, or through something like the
	 * {@link TicketingUITaskExecutor}.
	 * 
	 * @return number of work units
	 */
	public synchronized int getWorkUnits()
	{
		return workUnits;
	}
	
	



	/**
	 * Sets the number of work units in this task. This allows for tracking the progress of this Task
	 * 
	 * @param units
	 *            total number of work units
	 */
	public synchronized void setWorkUnits(int units)
	{
		if (state == ExecutorState.WORKING || state == ExecutorState.STALLED) return;
		if (units < workUnitsCompleted) return;
		if (units < 0) return;
		workUnits = units;
	}
	
}
