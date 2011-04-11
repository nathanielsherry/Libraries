package bolt;

import fava.signatures.FnMap;


public class BoltMap<T1, T2> extends Bolt implements FnMap<T1, T2>{

	private String inputName, outputName;

	public BoltMap(String language, String inputName, String outputName, String script) {
		super(language, script);
		
		this.inputName = inputName;
		this.outputName = outputName;
		
	}
	
	
	public BoltMap(String inputName, String outputName, String script) {
		this(LANGUAGE, inputName, outputName, script);
	}


	@Override
	public T2 f(T1 v) {
		
		if (!hasSideEffects) clear();
		set(inputName, v);
				
		try {
			
			run();
			
			return (T2)get(outputName);
			
		} catch (Exception e) {
			throw new RuntimeException("Error executing script\n\n" + e.getMessage() + "\n-----\n" + getStdErr());
		}
		
	}

}
