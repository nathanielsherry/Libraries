package plural.executor.filter.implementations;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fava.functionable.FList;
import fava.signatures.FnCondition;
import fava.signatures.FnFold;

import plural.executor.TicketManager;
import plural.executor.filter.FilterExecutor;
import plural.executor.map.MapExecutor;

/**
 * 
 * The PluralMapExecutor is a multi-threaded {@link MapExecutor} which assigns work to the given
 * {@link PluralMap} based on the current thread number.
 * 
 * @author Nathaniel Sherry, 2009-2010
 * 
 */

public class PluralFilterExecutor<T1> extends FilterExecutor<T1>
{

	protected int			threadCount;
	
	protected TicketManager	ticketManager;


	public PluralFilterExecutor(List<T1> sourceData, FnCondition<T1> t)
	{
		super(sourceData, t);
		
		init(super.sourceData, t, -1);
	}

	
	public PluralFilterExecutor(List<T1> sourceData, FnCondition<T1> t, int threads)
	{
		super(sourceData, t);
		
		init(super.sourceData, t, threads);
	}
	

	private void init(List<T1> sourceData, FnCondition<T1> t, int threads)
	{

		threadCount = threads <= 0 ? calcNumThreads() : threads;

		

	}


	
	/**
	 * Returns the desired size of a block of work to be done. Subclasses looking to change the behaviour of this
	 * class can overload this method
	 * @return
	 */
	protected int getDesiredBlockSize()
	{
		return (int)Math.ceil(super.getDataSize() / ((double)threadCount));
	}


	/**
	 * Executes the {@link Task}, blocking until complete. This method will return without executing the Task if the Task is null.
	 */
	@Override
	public List<T1> executeBlocking()
	{
		if (super.filter == null) return null;
		
		super.advanceState();
		
		
		super.execute(threadCount);
		
		
		if (super.executorSet != null && super.executorSet.isAbortRequested()) {
			super.executorSet.aborted(); 
		}
		
		
		
		//super.result = Fn.fold(results, fold);
		int size = FList.wrap(acceptedLists).fold(0, new FnFold<LinkedList<T1>, Integer>() {

			@Override
			public Integer f(LinkedList<T1> list, Integer sum) {
				return sum+list.size();
			}
		});
		
		result = new ArrayList<T1>(size);
		for (int i = 0; i < acceptedLists.size(); i++)
		{
			result.addAll(acceptedLists.get(i));
		}
		

		super.advanceState();
		
		if (super.executorSet != null && super.executorSet.isAborted()) return null;
		return result;

	}


	

	@Override
	protected void workForExecutor()
	{

		synchronized(this){
			if (ticketManager == null) ticketManager = new TicketManager(super.getDataSize(), getDesiredBlockSize());
		}
		
		while(true){
			
			LinkedList<T1> accepted = new LinkedList<T1>();
						
			
			int blockNum = ticketManager.getTicketBlockIndex();
			if (blockNum == -1) break;
			
			int start, size, end;
			start = ticketManager.getBlockStart(blockNum);
			size = ticketManager.getBlockSize(blockNum);
			end = start + size;
			
			for (int i = start; i < end; i++)
			{

				if(  filter.f(sourceData.get(i))  ){
					accepted.add(sourceData.get(i));
				}


			}
			
			synchronized (super.acceptedLists) {
				super.acceptedLists.add(accepted);	
			}
			
			if (super.executorSet != null) {
				super.workUnitCompleted(size);
				if (super.executorSet.isAbortRequested()) return;
			}
			
		}
	}


}
