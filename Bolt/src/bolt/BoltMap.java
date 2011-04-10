package bolt;

import fava.signatures.FnMap;


public class BoltMap<T1, T2> extends Bolt implements FnMap<T1, T2>{

	private String inputName, outputName;
	private boolean allowSideEffects = false;

	public BoltMap(String language, String script, String inputName, String outputName) {
		super(language, script);
		
		this.inputName = inputName;
		this.outputName = outputName;
		
	}
	
	
	public BoltMap(String script, String inputName, String outputName) {
		this("jython", script, inputName, outputName);
	}

	public void allowSideEffects(boolean allow)
	{
		this.allowSideEffects = allow;
	}
	
	@Override
	public T2 f(T1 v) {
		
		if (!allowSideEffects) clear();
		set(inputName, v);
				
		try {
			
			run();
			
			return (T2)get(outputName);
			
		} catch (Exception e) {
			throw new RuntimeException("Error executing script\n\n" + e.getMessage() + "\n-----\n" + getStdErr());
		}
		
	}

}
