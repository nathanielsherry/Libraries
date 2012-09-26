package plural.executor.fold.implementations;


import java.util.List;

import fava.signatures.FnFold;

import plural.executor.TicketManager;
import plural.executor.fold.FoldExecutor;
import plural.executor.map.MapExecutor;

/**
 * 
 * The PluralMapExecutor is a multi-threaded {@link MapExecutor} which assigns work to the given
 * {@link PluralMap} based on the current thread number.
 * Due to the nature of the parallelism used, the FnFold<T1, T1> fold operator must be associative 
 * and commutative.  
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
	

	
	public PluralFoldExecutor(List<T1> sourceData, T1 base, FnFold<T1, T1> t)
	{
		super(sourceData, base, t);
		init(super.sourceData, t, -1);
	}

	
	public PluralFoldExecutor(List<T1> sourceData, T1 base, FnFold<T1, T1> t, int threads)
	{
		super(sourceData, base, t);
		init(super.sourceData, t, threads);
	}
	
	
	
	private void init(List<T1> sourceData, FnFold<T1, T1> t, int threads)
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

		synchronized(this){
			if (ticketManager == null) ticketManager = new TicketManager(super.getDataSize(), getDesiredBlockSize());
		}
		
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
				result = (  (result == null) ? runningTotal : fold.f(runningTotal, result)  );	
			}
			
			if (super.executorSet != null) {
				super.workUnitCompleted(size);
				if (super.executorSet.isAbortRequested()) return;
			}
			
		}
	}


}
