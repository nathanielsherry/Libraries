package plural.workers.executor.maps.implementations;

import java.util.List;

import plural.workers.PluralMap;
import plural.workers.PluralSet;


public class SimpleUIMapExecutor<T1, T2> extends SimpleMapExecutor<T1, T2>
{

	private PluralSet<?> mapSet;
	
	public SimpleUIMapExecutor(List<T1> sourceData, PluralMap<T1, T2> t, PluralSet<?> taskList)
	{
		super(sourceData, t);
		this.mapSet = taskList;
		
		t.setWorkUnits(super.getDataSize());
	}
	
	public SimpleUIMapExecutor(List<T1> sourceData, List<T2> targetList, PluralMap<T1, T2> t, PluralSet<?> taskList)
	{
		super(sourceData, targetList, t);
		this.mapSet = taskList;
		
		t.setWorkUnits(super.getDataSize());
	}
	
	@Override
	protected void workForExecutor()
	{
		
		for (int i = 0; i < super.getDataSize(); i++) {
			
			super.targetList.set(  i, super.pluralMap.f(super.sourceData.get(i))  );
			pluralMap.workUnitCompleted();
			
			if (mapSet.isAbortRequested()) return;
		}
	}
	
	@Override
	public List<T2> executeBlocking()
	{

		super.pluralMap.advanceState();

		workForExecutor();
		
		if (mapSet != null && mapSet.isAbortRequested()) {
			mapSet.aborted(); 
		}

		super.pluralMap.advanceState();
		
		if (mapSet != null && mapSet.isAborted()) return null;
		return super.targetList;
	}

}
