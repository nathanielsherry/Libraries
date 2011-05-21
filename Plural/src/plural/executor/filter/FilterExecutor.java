package plural.executor.filter;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

public abstract class FilterExecutor<T1> extends AbstractExecutor
{

	protected FnMap<T1, Boolean>	filter;
	protected List<T1>				sourceData;
	
	protected List<LinkedList<T1>>	acceptedLists;
	protected List<T1> 				result;
	


	public FilterExecutor(List<T1> sourceData, FnMap<T1, Boolean> filter)
	{
		super();
		
		this.sourceData = sourceData;
		this.filter = filter;

		acceptedLists = new ArrayList<LinkedList<T1>>();
		result = new ArrayList<T1>();
		
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
	public abstract List<T1> executeBlocking();

}
