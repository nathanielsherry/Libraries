package bolt;

import fava.signatures.FnCombine;


public class BoltCombine<T1, T2> extends Bolt implements FnCombine<T1, T2> {

	private String input1, input2, output;

	
	public BoltCombine(String language, String input1, String input2, String output, String script) {
		super(language, script);
		
		this.input1 = input1;
		this.input2 = input2;
		this.output = output;
		
	}
	
	public BoltCombine(String input1, String input2, String output, String script) {
		this(LANGUAGE, script, input1, input2, output);
	}

	
	@Override
	public T2 f(T1 v1, T1 v2) {

		if (!allowSideEffects) clear();
		set(input1, v1);
		set(input2, v2);
				
		try {
			run();
			return (T2)get(output);
			
		} catch (Exception e) {
			throw new RuntimeException("Error executing script\n\n" + e.getMessage() + "\n-----\n" + getStdErr());
		}
				
	}

}
