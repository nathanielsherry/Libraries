package plural.executor.filter.implementations;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fava.Fn;
import fava.functionable.Range;
import fava.signatures.FnCondition;
import fava.signatures.FnFold;
import fava.signatures.FnMap;

import plural.executor.ExecutorState;
import plural.executor.TicketManager;
import plural.executor.filter.FilterExecutor;
import plural.executor.fold.FoldExecutor;
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

		threadCount = threads == -1 ? calcNumThreads() : threads;

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
	public void setFilter(FnCondition<T1> fitler)
	{

		if (super.filter != null && super.getState() != ExecutorState.UNSTARTED) return;
		super.filter = filter;
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
		int size = Fn.fold(acceptedLists, 0, new FnFold<LinkedList<T1>, Integer>() {

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

		while(true){
			
			LinkedList<T1> accepted = new LinkedList<T1>();
						
			Range block = ticketManager.getBlockAsRange();
			if (block == null) return;
			
			for (int i = block.getStart(); i <= block.getStop(); i++)
			{

				if(  filter.f(sourceData.get(i))  ){
					accepted.add(sourceData.get(i));
				}


			}
			
			synchronized (super.acceptedLists) {
				super.acceptedLists.add(accepted);	
			}
			
			if (super.executorSet != null) {
				super.workUnitCompleted(block.size());
				if (super.executorSet.isAbortRequested()) return;
			}
			
		}
	}


}
