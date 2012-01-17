package bolt.scripting;

import bolt.scripting.languages.Language;
import fava.signatures.FnGet;

public class BoltGet<T1> extends BoltScripter implements FnGet<T1>{

	private String get;
	
	public BoltGet(String language, boolean compilable, String get, String script) {
		
		this(customLanguage(language, compilable), get, script);
		
	}
	
	public BoltGet(Language language, String get, String script) {
		
		super(language, script);
		
		this.get = get;
	
	}
	

	
	@Override
	public T1 f() {
		
		if (hasSideEffects || !multithreaded) {
			synchronized(this)
			{
				return do_f();
			}
		} else {
			return do_f();
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	private T1 do_f() {
		
		if (!hasSideEffects) clear();
		
		try {
			run();
			return (T1) get(get);
			
		} catch (Exception e) {
			throw new BoltScriptExecutionException("Error executing script\n\n" + e.getMessage() + "\n-----\n" + getStdErr(), e);
		}
		
	}

}
