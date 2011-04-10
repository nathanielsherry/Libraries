package plural.workers.executor.eachindex.implementations;


import plural.workers.PluralEachIndex;
import plural.workers.PluralMap;
import plural.workers.executor.eachindex.EachIndexExecutor;
import plural.workers.executor.maps.MapExecutor;

/**
 * 
 * The PluralMapExecutor is a multi-threaded {@link MapExecutor} which assigns work to the given
 * {@link PluralMap} based on the current thread number.
 * 
 * @author Nathaniel Sherry, 2009-2010
 * 
 */

public class SimpleEachIndexExecutor extends EachIndexExecutor
{

	protected int			threadCount;
	
	public SimpleEachIndexExecutor(int size, PluralEachIndex pluralEachIndex)
	{
		super(size, pluralEachIndex);
	}


	/**
	 * Sets the {@link PluralMap} for this {@link SplittingMapExecutor}. Setting the PluralMap after creation of the
	 * {@link MapExecutor} allows the associated {@link PluralMap} to query the {@link SplittingMapExecutor} for
	 * information about the work block for each thread. This method will return without setting the PluralMap if
	 * the current PluralMap's state is not {@link PluralMap.State#UNSTARTED}
	 * 
	 * @param map
	 *            the {@link PluralMap} to execute.
	 */
	public void setEachIndex(PluralEachIndex eachIndex)
	{
		if (super.pluralEachIndex != null && super.pluralEachIndex.getState() != PluralMap.State.UNSTARTED) return;
		super.pluralEachIndex = eachIndex;
	}

	


	/**
	 * Executes the {@link Task}, blocking until complete. This method will return without executing the Task if the Task is null.
	 */
	@Override
	public void executeBlocking()
	{
		workForExecutor();
	}


	

	@Override
	protected void workForExecutor()
	{
		for (int i = 0; i < super.getDataSize(); i++)
		{
			pluralEachIndex.f(i);
		}
	}


}
