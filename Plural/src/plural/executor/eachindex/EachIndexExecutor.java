package plural.executor.eachindex;


import fava.signatures.FnEach;
import plural.executor.AbstractExecutor;
import plural.executor.Plural;

public abstract class EachIndexExecutor extends AbstractExecutor {

	protected FnEach<Integer>		eachIndex;
	
	
	public EachIndexExecutor(int size, FnEach<Integer> pluralEachIndex)
	{

		super();
		
		this.eachIndex = pluralEachIndex;
		
		plural = new Plural();
		plural.setWorkUnits(size);
		
	}
	
	@Override
	public int getDataSize()
	{
		return plural.getWorkUnits();
	}


	/**
	 * Executes the EachIndexExecutor, waiting until the processing is complete.
	 */
	public abstract void executeBlocking();
	
}
