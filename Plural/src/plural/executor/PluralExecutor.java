package plural.executor;

import eventful.IEventful;
import plural.executor.maps.implementations.PluralMapExecutor;

public interface PluralExecutor extends IEventful{

	/**
	 * Gets the name of this Executor
	 * 
	 * @return name of this Executor
	 */
	public abstract String getName();
	
	/**
	 * Sets the name of this Executor
	 * @param name the new name of this Executor
	 */
	public void setName(String name);

	
	public abstract void setStalling(boolean stalling);

	/**
	 * Gets the {@link ExecutorState} of this Executor
	 * 
	 * @return the current State of this Executor
	 */
	public abstract ExecutorState getState();


	/**
	 * Advances the {@link ExecutorState} of the current Executor by 1
	 */
	public abstract void advanceState();

	/**
	 * Mark this Executor as having been {@link ExecutorState#SKIPPED}
	 */
	public abstract void markTaskSkipped();

	/**
	 * Indicate that a work unit has been completed.
	 */
	public abstract void workUnitCompleted();

	/**
	 * Indicate that several work units have been completed
	 * 
	 * @param unitCount
	 *            number of work units completed
	 */
	public abstract void workUnitCompleted(int unitCount);

	/**
	 * Gets the progress of this Executor
	 * 
	 * @return the current progress
	 */
	public abstract double getProgress();

	/**
	 * Gets the number of work units in this Executor. This will only be a valid value once
	 * {@link #setWorkUnits(int)} has been called, either explicitly, or through something like the
	 * {@link PluralMapExecutor}.
	 * 
	 * @return number of work units
	 */
	public abstract int getWorkUnits();

	/**
	 * Sets the number of work units in this Executor. This allows for tracking the progress of this Executor
	 * 
	 * @param units
	 *            total number of work units
	 */
	public abstract void setWorkUnits(int units);

	/**
	 * The size of the data set that the provided Executor will be operating on.
	 * 
	 * @return number of required iterations.
	 */
	public abstract int getDataSize();

	
	public ExecutorSet<?> getExecutorSet();

	public void setExecutorSet(ExecutorSet<?> executorSet);
	
}