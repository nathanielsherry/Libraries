package bolt;

import fava.signatures.FnGet;

public class BoltGet<T1> extends Bolt implements FnGet<T1>{

	private String get;
	
	public BoltGet(String language, String get, String script) {
		super(language, script);
		
		this.get = get;
	
	}
	
	
	public BoltGet(String get, String script) {
		this(LANGUAGE, get, script);
	}
	
	@Override
	public T1 f() {
		
		if (!hasSideEffects) clear();
		
		try {
			run();
			return (T1) get(get);
			
		} catch (Exception e) {
			throw new RuntimeException("Error executing script\n\n" + e.getMessage() + "\n-----\n" + getStdErr());
		}
		
	}

}
