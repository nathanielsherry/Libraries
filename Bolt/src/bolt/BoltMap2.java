package bolt;

import fava.signatures.FnMap2;

public class BoltMap2<T1, T2, T3> extends Bolt implements FnMap2<T1, T2, T3>{

	private String input1, input2, output;
	private boolean allowSideEffects = false;

	public BoltMap2(String language, String script, String input1, String input2, String output) {
		super(language, script);
		
		this.input1 = input1;
		this.input2 = input2;
		this.output = output;
		
	}
	
	public BoltMap2(String script, String input1, String input2, String output) {
		this("jython", script, input1, input2, output);
	}
	
	public void allowSideEffects(boolean allow)
	{
		this.allowSideEffects = allow;
	}
	
	@Override
	public T3 f(T1 v1, T2 v2) {
		
		if (!allowSideEffects) clear();
		set(input1, v1);
		set(input2, v2);
				
		try {
			
			run();
			
			return (T3)get(output);
			
		} catch (Exception e) {
			throw new RuntimeException("Error executing script\n\n" + e.getMessage() + "\n-----\n" + getStdErr());
		}
		
	}

}