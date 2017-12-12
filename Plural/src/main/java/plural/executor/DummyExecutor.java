package plural.executor;

public class DummyExecutor extends AbstractExecutor{

	public DummyExecutor() {
		super();
	}
	
	public DummyExecutor(boolean stall) {
		super();
		super.setStalling(stall);
	}
	
	public DummyExecutor(int workunits) {
		super();
		super.setWorkUnits(workunits);
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
		super.advanceState();
	}
	
	public void setWorkUnits(int count)
	{
		super.setWorkUnits(count);
	}
	
	public void workUnitCompleted(int count)
	{
		super.workUnitCompleted(count);
	}
	
	public void workUnitCompleted()
	{
		super.workUnitCompleted();
	}

}
