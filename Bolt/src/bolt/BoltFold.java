package bolt;

import fava.signatures.FnFold;


public class BoltFold<T1, T2> extends Bolt implements FnFold<T1, T2>{

	private String base, value, result;
	private boolean allowSideEffects = false;

	public BoltFold(String language, String script, String base, String value, String result) {
		super(language, script);
		
		this.base = base;
		this.value = value;
		this.result = result;
		
	}
	
	public BoltFold(String script, String base, String value, String result) {
		this("jython", script, base, value, result);
	}

	
	public void allowSideEffects(boolean allow)
	{
		this.allowSideEffects = allow;
	}
	
	@Override
	public T2 f(T1 v, T2 b) {
		
		if (!allowSideEffects) clear();
		set(base, b);
		set(value, v);
				
		try {
			
			run();
			return (T2)get(result);
			
		} catch (Exception e) {
			throw new RuntimeException("Error executing script\n\n" + e.getMessage() + "\n-----\n" + getStdErr());
		}
				
	}

}
