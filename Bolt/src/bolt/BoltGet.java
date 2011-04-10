package bolt;

import fava.signatures.FnGet;

public class BoltGet<T1> extends Bolt implements FnGet<T1>{

	private String get;
	private boolean allowSideEffects = false;
	
	public BoltGet(String engineName, String script, String get) {
		super(engineName, script);
		
		this.get = get;
	
	}
	
	
	public BoltGet(String script, String get) {
		this("jython", script, get);
	}

	public void allowSideEffects(boolean allow)
	{
		this.allowSideEffects = allow;
	}
	
	@Override
	public T1 f() {
		
		if (!allowSideEffects) clear();
		
		try {
			run();
			return (T1) get(get);
			
		} catch (Exception e) {
			throw new RuntimeException("Error executing script\n\n" + e.getMessage() + "\n-----\n" + getStdErr());
		}
		
	}

}
