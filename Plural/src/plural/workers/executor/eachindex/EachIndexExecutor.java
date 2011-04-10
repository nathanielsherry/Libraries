package plural.workers.executor.eachindex;


import plural.workers.PluralEachIndex;
import plural.workers.executor.AbstractExecutor;

public abstract class EachIndexExecutor extends AbstractExecutor<Integer> {

	protected PluralEachIndex		pluralEachIndex;
	
	public EachIndexExecutor(int size, PluralEachIndex pluralEachIndex)
	{

		this.pluralEachIndex = pluralEachIndex;
		pluralEachIndex.setWorkUnits(size);
		
	}
	
	@Override
	public int getDataSize()
	{
		return pluralEachIndex.getWorkUnits();
	}


	/**
	 * Executes the EachIndexExecutor, waiting until the processing is complete.
	 */
	public abstract void executeBlocking();
	
}
