package plural.executor.maps.implementations;

import java.util.List;

import fava.signatures.FnMap;

import plural.executor.PluralSet;


public class SimpleUIMapExecutor<T1, T2> extends SimpleMapExecutor<T1, T2>
{
	
	public SimpleUIMapExecutor(List<T1> sourceData, FnMap<T1, T2> t)
	{
		super(sourceData, t);
		
		super.plural.setWorkUnits(super.getDataSize());
	}
	
	public SimpleUIMapExecutor(List<T1> sourceData, List<T2> targetList, FnMap<T1, T2> t)
	{
		super(sourceData, targetList, t);
		
		super.plural.setWorkUnits(super.getDataSize());
	}
	
	
	@Override
	protected void workForExecutor()
	{
		
		for (int i = 0; i < super.getDataSize(); i++) {
			
			super.targetList.set(  i, super.map.f(super.sourceData.get(i))  );
			super.plural.workUnitCompleted();
			
			if (plural.pluralSet.isAbortRequested()) return;
		}
	}
	
	@Override
	public List<T2> executeBlocking()
	{

		super.plural.advanceState();

		workForExecutor();
		
		if (plural.pluralSet != null && plural.pluralSet.isAbortRequested()) {
			plural.pluralSet.aborted(); 
		}

		super.plural.advanceState();
		
		if (plural.pluralSet != null && plural.pluralSet.isAborted()) return null;
		return super.targetList;
	}

}
