package plural.executor.maps.implementations;


import java.util.List;

import fava.signatures.FnMap;

import plural.executor.PluralSet;

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

	/**
	 * Creates a new TicketingTaskExecutor.
	 * @param ticketCount the number of work tickets needed
	 * @param map the {@link Task} to be executed.
	 * @param mapSet the {@link PluralSet} to monitor for an abort status
	 */
	public PluralUIMapExecutor(List<T1> sourceData, FnMap<T1, T2> map)
	{
		super(sourceData, map);
	}
	
	public PluralUIMapExecutor(List<T1> sourceData, List<T2> targetList, FnMap<T1, T2> map)
	{
		super(sourceData, targetList, map);
	}
		

	@Override
	public List<T2> executeBlocking()
	{

		super.plural.advanceState();

		super.execute(super.threadCount);
		
		if (plural.pluralSet != null && plural.pluralSet.isAbortRequested()) {
			plural.pluralSet.aborted(); 
		}

		super.plural.advanceState();
		
		if (plural.pluralSet != null && plural.pluralSet.isAborted()) return null;
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
				super.targetList.set(i, super.map.f(super.sourceData.get(i)));
			}
			
			if (plural.pluralSet != null) {
				super.plural.workUnitCompleted(blockEnd - blockStart);
				if (plural.pluralSet.isAbortRequested()) return;
			}

		}


	}
	
	
	@Override
	public int getDesiredBlockSize()
	{
		return Math.max(super.getDataSize() / (calcNumThreads() * 50), 1);
	}

}
