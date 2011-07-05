package plural.executor.fold.implementations;


import java.util.List;

import fava.Fn;
import fava.functionable.Range;
import fava.signatures.FnFold;
import fava.signatures.FnMap;

import plural.executor.ExecutorState;
import plural.executor.TicketManager;
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

public class PluralFoldExecutor<T1> extends FoldExecutor<T1>
{

	protected int			threadCount;
	
	protected TicketManager	ticketManager;


	public PluralFoldExecutor(List<T1> sourceData, FnFold<T1, T1> t)
	{
		super(sourceData, t);
		
		init(super.sourceData, t, -1);
	}

	
	public PluralFoldExecutor(List<T1> sourceData, FnFold<T1, T1> t, int threads)
	{
		super(sourceData, t);
		
		init(super.sourceData, t, threads);
	}
	

	private void init(List<T1> sourceData, FnFold<T1, T1> t, int threads)
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
	public void setFold(FnFold<T1, T1> fold)
	{

		if (super.fold != null && super.getState() != ExecutorState.UNSTARTED) return;
		super.fold = fold;
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
	public T1 executeBlocking()
	{
		if (super.fold == null) return null;
		
		super.advanceState();
		
		
		super.execute(threadCount);
		
		
		if (super.executorSet != null && super.executorSet.isAbortRequested()) {
			super.executorSet.aborted(); 
		}
		
		super.advanceState();
		
		if (super.executorSet != null && super.executorSet.isAborted()) return null;
		return super.result;

	}


	

	@Override
	protected void workForExecutor()
	{

		while(true){
			
			int blockNum = ticketManager.getTicketBlockIndex();
			if (blockNum == -1) break;
			
			int start, size, end;
			start = ticketManager.getBlockStart(blockNum);
			size = ticketManager.getBlockSize(blockNum);
			end = start + size;
			
			
			
			T1 runningTotal = null;
			boolean first = true;
			for (int i = start; i < end; i++)
			{
				if (first) {
					runningTotal = sourceData.get(i);
					first = false;
				} else {
					runningTotal = fold.f(sourceData.get(i), runningTotal);
				}

			}
			
			synchronized (this) {
				result = (  (result == null) ? runningTotal : fold.f(result, runningTotal)  );	
			}
			
			if (super.executorSet != null) {
				super.workUnitCompleted(size);
				if (super.executorSet.isAbortRequested()) return;
			}
			
		}
	}


}
