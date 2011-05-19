package plural.executor.maps.implementations;


import java.util.List;

import fava.signatures.FnMap;

import plural.executor.Plural;
import plural.executor.ExecutorState;
import plural.executor.TicketManager;
import plural.executor.maps.MapExecutor;

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


	public PluralMapExecutor(List<T1> sourceData, FnMap<T1, T2> t)
	{
		super(sourceData, t);
		
		init(super.sourceData, super.targetList, t);
	}


	public PluralMapExecutor(List<T1> sourceData, List<T2> targetList, FnMap<T1, T2> t)
	{
		super(sourceData, targetList, null);
		
		init(super.sourceData, super.targetList, t);
	}


	private void init(List<T1> sourceData, List<T2> targetList, FnMap<T1, T2> t)
	{

		threadCount = calcNumThreads();

		ticketManager = new TicketManager(super.getDataSize(), getDesiredBlockSize());

	}


	/**
	 * Sets the {@link PluralMap} for this {@link SplittingMapExecutor}. Setting the PluralMap after creation of the
	 * {@link MapExecutor} allows the associated {@link PluralMap} to query the {@link SplittingMapExecutor} for
	 * information about the work block for each thread. This method will return without setting the PluralMap if
	 * the current PluralMap's state is not {@link PluralMap.ExecutorState#UNSTARTED}
	 * 
	 * @param map
	 *            the {@link PluralMap} to execute.
	 */
	public void setMap(FnMap<T1, T2> map)
	{

		if (super.map != null && super.plural.getState() != ExecutorState.UNSTARTED) return;
		super.map = map;
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
		if (super.map == null) return null;
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
			t2 = map.f(sourceData.get(i));
			targetList.set(i, t2);
		}
	}


}
