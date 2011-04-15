package bolt.scripting;

import fava.signatures.FnMap3;

public class BoltMap3<T1, T2, T3, T4> extends BoltScripter implements FnMap3<T1, T2, T3, T4>{

	private String input1, input2, input3, output;
	

	public BoltMap3(String language, String input1, String input2, String input3, String output, String script) {
		super(language, script);
		
		this.input1 = input1;
		this.input2 = input2;
		this.input3 = input3;
		this.output = output;
		
	}
	
	public BoltMap3(String input1, String input2, String input3, String output, String script) {
		this(LANGUAGE, input1, input2, input3, output, script);
	}
	

	@Override
	public T4 f(T1 v1, T2 v2, T3 v3) {
		
		if (!hasSideEffects) clear();
		set(input1, v1);
		set(input2, v2);
		set(input3, v3);
				
		try {
			
			run();
			
			return (T4)get(output);
			
		} catch (Exception e) {
			throw new RuntimeException("Error executing script\n\n" + e.getMessage() + "\n-----\n" + getStdErr());
		}
		
	}

}