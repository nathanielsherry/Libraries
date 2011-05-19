package plural.executor.eachindex.implementations;


import fava.signatures.FnEach;
import plural.executor.PluralSet;

public class SimpleUIEachIndexExecutor extends SimpleEachIndexExecutor {

	
	public SimpleUIEachIndexExecutor(int size, FnEach<Integer> pluralEachIndex) {
		super(size, pluralEachIndex);
	}
	
	@Override
	protected void workForExecutor()
	{
		
		for (int i = 0; i < super.getDataSize(); i++) {
			
			super.eachIndex.f(i);
			super.plural.workUnitCompleted();
			
			if (plural.pluralSet.isAbortRequested()) return;
		}
	}
	
	@Override
	public void executeBlocking()
	{

		super.plural.advanceState();

		workForExecutor();
		
		if (plural.pluralSet != null && plural.pluralSet.isAbortRequested()) {
			plural.pluralSet.aborted(); 
		}

		super.plural.advanceState();
		
	}
	
}
