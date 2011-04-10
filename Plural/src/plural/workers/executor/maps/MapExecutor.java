package plural.workers.executor.maps;


import java.util.ArrayList;
import java.util.List;

import plural.workers.PluralMap;
import plural.workers.PluralSet;
import plural.workers.executor.AbstractExecutor;
import plural.workers.executor.maps.implementations.PluralMapExecutor;
import plural.workers.executor.maps.implementations.PluralUIMapExecutor;
import plural.workers.executor.maps.implementations.SimpleMapExecutor;

/**
 * 
 * A TaskExecutor defines a manner of executing a {@link Task} with a given number of data
 * points. Subclasses can perform the work in a single-threaded manner such as {@link SimpleMapExecutor}, or
 * in a multi-threaded manner such as {@link PluralMapExecutor}. {@link PluralUIMapExecutor} accepts a
 * {@link PluralSet} and will update the given Task with the progress of the processing -- useful for updating
 * a UI.
 * 
 * @author Nathaniel Sherry, 2009
 * 
 */

public abstract class MapExecutor<T1, T2> extends AbstractExecutor<T1>
{

	protected PluralMap<T1, T2>		pluralMap;
	protected List<T1>				sourceData;
	protected List<T2>				targetList;
	

	public MapExecutor(List<T1> sourceData, PluralMap<T1, T2> map)
	{
		this(sourceData, null, map);
	}
	
	public MapExecutor(List<T1> sourceData, List<T2> target, PluralMap<T1, T2> map)
	{
		this.sourceData = sourceData;
		this.pluralMap = map;
		this.targetList = target;

		//if the target list is not given, create and populate with nulls
		if (targetList == null)
		{
			targetList = new ArrayList<T2>(sourceData.size());
			for (int i = 0; i < sourceData.size(); i++){ targetList.add(null); }
		}
		
		for (int i = target.size(); i < sourceData.size(); i++)
		{
			target.add(null);
		}
		
		pluralMap.setWorkUnits(sourceData.size());
		
	}
	

	@Override
	public int getDataSize()
	{
		return sourceData.size();
	}


	/**
	 * Executes the MapExecutor, waiting until the processing is complete.
	 */
	public abstract List<T2> executeBlocking();

}
