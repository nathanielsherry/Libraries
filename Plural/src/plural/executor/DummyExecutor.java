package plural.executor;

public class DummyExecutor extends AbstractExecutor{

	public DummyExecutor() {
		super();
	}
	
	public DummyExecutor(boolean stall) {
		super();
		super.plural.setStalling(stall);
	}
	
	public DummyExecutor(int workunits) {
		super();
		super.plural.setWorkUnits(workunits);
	}
	
	@Override
	protected void workForExecutor() {		
	}

	@Override
	public int getDataSize() {
		return 0;
	}
	
	public void advanceState()
	{
		super.plural.advanceState();
	}
	
	public void setWorkUnits(int count)
	{
		super.plural.setWorkUnits(count);
	}
	
	public void workUnitCompleted(int count)
	{
		super.plural.workUnitCompleted(count);
	}
	
	public void workUnitCompleted()
	{
		super.plural.workUnitCompleted();
	}

}
