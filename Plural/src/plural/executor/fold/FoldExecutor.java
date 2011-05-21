package plural.executor.fold;


import java.util.ArrayList;
import java.util.List;

import fava.signatures.FnFold;
import fava.signatures.FnMap;

import plural.executor.AbstractExecutor;
import plural.executor.ExecutorSet;

/**
 * 
 * A TaskExecutor defines a manner of executing a {@link Task} with a given number of data
 * points. Subclasses can perform the work in a single-threaded manner such as {@link SimpleMapExecutor}, or
 * in a multi-threaded manner such as {@link PluralMapExecutor}. {@link PluralUIMapExecutor} accepts a
 * {@link ExecutorSet} and will update the given Task with the progress of the processing -- useful for updating
 * a UI.
 * 
 * @author Nathaniel Sherry, 2009
 * 
 */

public abstract class FoldExecutor<T1> extends AbstractExecutor
{

	protected FnFold<T1, T1>		fold;
	protected List<T1>				sourceData;
	protected T1					result;
	
	
	public FoldExecutor(List<T1> sourceData, FnFold<T1, T1> fold)
	{
		super();
		
		this.sourceData = sourceData;
		this.fold = fold;
				
		super.setWorkUnits(sourceData.size());
		
	}
	

	@Override
	public int getDataSize()
	{
		return sourceData.size();
	}


	/**
	 * Executes the MapExecutor, waiting until the processing is complete.
	 */
	public abstract T1 executeBlocking();

}
