package plural.workers.executor.maps.implementations;


import java.util.List;

import plural.workers.PluralMap;
import plural.workers.executor.TicketManager;
import plural.workers.executor.maps.MapExecutor;

/**
 * 
 * The PluralMapExecutor is a multi-threaded {@link MapExecutor} which assigns work to the given
 * {@link PluralMap} based on the current thread number.
 * 
 * @author Nathaniel Sherry, 2009-2010
 * 
 */

public class PluralMapExecutor<T1, T2> extends MapExecutor<T1, T2>
{

	protected int			threadCount;
	
	protected TicketManager	ticketManager;


	public PluralMapExecutor(List<T1> sourceData, PluralMap<T1, T2> t)
	{
		super(sourceData, t);
		
		init(super.sourceData, super.targetList, t);
	}


	public PluralMapExecutor(List<T1> sourceData, List<T2> targetList, PluralMap<T1, T2> t)
	{
		super(sourceData, targetList, null);
		
		init(super.sourceData, super.targetList, t);
	}


	private void init(List<T1> sourceData, List<T2> targetList, PluralMap<T1, T2> t)
	{

		threadCount = calcNumThreads();

		ticketManager = new TicketManager(super.getDataSize(), getDesiredBlockSize());

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
	public void setMap(PluralMap<T1, T2> map)
	{

		if (super.pluralMap != null && super.pluralMap.getState() != PluralMap.State.UNSTARTED) return;
		super.pluralMap = map;
	}

	
	/**
	 * Returns the desired size of a block of work to be done. Subclasses looking to change the behaviour of this
	 * class can overload this method
	 * @return
	 */
	protected int getDesiredBlockSize()
	{
		return (int)Math.ceil(super.getDataSize() / (double)threadCount);
	}


	/**
	 * Executes the {@link Task}, blocking until complete. This method will return without executing the Task if the Task is null.
	 */
	@Override
	public List<T2> executeBlocking()
	{
		if (super.pluralMap == null) return null;
		super.execute(threadCount);
		
		return super.targetList;

	}


	

	@Override
	protected void workForExecutor()
	{
		int blockIndex;
		blockIndex = ticketManager.getTicketBlockIndex();
		
		int start = ticketManager.getBlockStart(blockIndex);
		int size = ticketManager.getBlockSize(blockIndex);
		
		T2 t2;
		for (int i = start; i < start + size; i++)
		{
			t2 = pluralMap.f(sourceData.get(i));
			targetList.set(i, t2);
		}
	}


}
