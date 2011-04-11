package bolt;

import fava.signatures.FnFold;


public class BoltFold<T1, T2> extends Bolt implements FnFold<T1, T2>{

	private String base, value, result;

	public BoltFold(String language, String base, String value, String result, String script) {
		super(language, script);
		
		this.base = base;
		this.value = value;
		this.result = result;
		
	}
	
	public BoltFold(String base, String value, String result, String script) {
		this(LANGUAGE, base, value, result, script);
	}

	
	@Override
	public T2 f(T1 v, T2 b) {
		
		if (!hasSideEffects) clear();
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
