package plural.workers.executor.eachindex.implementations;


import plural.workers.PluralEachIndex;
import plural.workers.PluralMap;
import plural.workers.executor.TicketManager;
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

public class PluralEachIndexExecutor extends EachIndexExecutor
{

	protected int			threadCount;
	protected TicketManager	ticketManager;


	public PluralEachIndexExecutor(int size, PluralEachIndex pluralEachIndex)
	{
		super(size, pluralEachIndex);
		
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
	public void setEachIndex(PluralEachIndex eachIndex)
	{
		if (super.pluralEachIndex != null && super.pluralEachIndex.getState() != PluralMap.State.UNSTARTED) return;
		super.pluralEachIndex = eachIndex;
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
	public void executeBlocking()
	{
		if (super.pluralEachIndex == null) return;
		super.execute(threadCount);

	}


	

	@Override
	protected void workForExecutor()
	{
		int blockIndex;
		blockIndex = ticketManager.getTicketBlockIndex();
		if (blockIndex == -1) return;
		
		int start = ticketManager.getBlockStart(blockIndex);
		int size = ticketManager.getBlockSize(blockIndex);

		for (int i = start; i < start + size; i++)
		{
			pluralEachIndex.f(i);
		}
	}


}
