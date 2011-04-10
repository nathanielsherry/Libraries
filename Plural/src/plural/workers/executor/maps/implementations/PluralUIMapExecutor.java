package plural.workers.executor.maps.implementations;


import java.util.List;

import plural.workers.PluralMap;
import plural.workers.PluralSet;

/**
 * 
 * TicketingUITaskExecutor is a multi-threaded {@link MapExecutor} which assigns work to the given {@link Task}
 * based on a ticket (number) representing one element of the problem set as defined by a supplied problem
 * size. TicketingTaskExecutor accepts a {@link PluralSet}, and when one is provided, it will update the given
 * {@link Task} with the number of work units completed, so that a UI can allow users to monitor the progress
 * of this TicketedTaskExecutor.
 * 
 * @author Nathaniel Sherry, 2009
 * 
 */

public class PluralUIMapExecutor<T1, T2> extends PluralMapExecutor<T1, T2>
{

	private PluralSet<?>	mapSet;

	/**
	 * Creates a new TicketingTaskExecutor.
	 * @param ticketCount the number of work tickets needed
	 * @param pluralMap the {@link Task} to be executed.
	 * @param mapSet the {@link PluralSet} to monitor for an abort status
	 */
	public PluralUIMapExecutor(List<T1> sourceData, PluralMap<T1, T2> map, PluralSet<?> mapSet)
	{
		super(sourceData, map);
		this.mapSet = mapSet;
	}
	
	public PluralUIMapExecutor(List<T1> sourceData, List<T2> targetList, PluralMap<T1, T2> map, PluralSet<?> mapSet)
	{
		super(sourceData, targetList, map);
		this.mapSet = mapSet;
	}


	@Override
	public List<T2> executeBlocking()
	{

		super.pluralMap.advanceState();

		super.execute(super.threadCount);
		
		if (mapSet != null && mapSet.isAbortRequested()) {
			mapSet.aborted(); 
		}

		super.pluralMap.advanceState();
		
		if (mapSet != null && mapSet.isAborted()) return null;
		return super.targetList;
		
	
	}


	@Override
	protected void workForExecutor()
	{

		int blockNum;
		
		while (true) {

			int blockStart, blockEnd;
		
			//get the block number to work on
			blockNum = super.ticketManager.getTicketBlockIndex();
			
			//if there are no more block numbers left, exit
			if (blockNum == -1) break;
			
			//calculate start, stop indexes
			blockStart = super.ticketManager.getBlockStart(blockNum);
			blockEnd = blockStart + super.ticketManager.getBlockSize(blockNum);
			
			//do work
			for (int i = blockStart; i < blockEnd; i++) {
				targetList.set(i, super.pluralMap.f(super.sourceData.get(i)));
			}
			
			if (mapSet != null) {
				pluralMap.workUnitCompleted(blockEnd - blockStart);
				if (mapSet.isAbortRequested()) return;
			}

		}


	}
	
	
	@Override
	public int getDesiredBlockSize()
	{
		return Math.max(super.getDataSize() / (calcNumThreads() * 50), 1);
	}

}
