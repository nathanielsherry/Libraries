package plural.executor.eachindex;


import fava.signatures.FnEach;
import plural.executor.AbstractExecutor;

public abstract class EachIndexExecutor extends AbstractExecutor {

	protected FnEach<Integer>		eachIndex;
	
	
	public EachIndexExecutor(int size, FnEach<Integer> eachIndex)
	{

		super();
		
		this.eachIndex = eachIndex;
		
		super.setWorkUnits(size);
		
	}
	
	@Override
	public int getDataSize()
	{
		return super.getWorkUnits();
	}


	/**
	 * Executes the EachIndexExecutor, waiting until the processing is complete.
	 */
	public abstract void executeBlocking();
	
}
