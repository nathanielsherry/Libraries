package plural.workers.executor.eachindex.implementations;


import plural.workers.PluralEachIndex;
import plural.workers.PluralSet;

public class SimpleUIEachIndexExecutor extends SimpleEachIndexExecutor {

	PluralSet<?>	pluralSet;
	
	public SimpleUIEachIndexExecutor(int size, PluralEachIndex pluralEachIndex, PluralSet<?> pluralSet) {
		super(size, pluralEachIndex);
		this.pluralSet = pluralSet;
	}
	
	@Override
	protected void workForExecutor()
	{
		
		for (int i = 0; i < super.getDataSize(); i++) {
			
			super.pluralEachIndex.f(i);
			pluralEachIndex.workUnitCompleted();
			
			if (pluralSet.isAbortRequested()) return;
		}
	}
	
	@Override
	public void executeBlocking()
	{

		super.pluralEachIndex.advanceState();

		workForExecutor();
		
		if (pluralSet != null && pluralSet.isAbortRequested()) {
			pluralSet.aborted(); 
		}

		super.pluralEachIndex.advanceState();
		
	}
	
}
