package bolt.scripting;

import bolt.scripting.languages.Language;
import fava.signatures.FnMap2;

public class BoltMap2<T1, T2, T3> extends BoltScripter implements FnMap2<T1, T2, T3>{

	private String input1, input2, output;

	
	public BoltMap2(String language, boolean compilable, String input1, String input2, String output, String script) {
		
		this(customLanguage(language, compilable), input1, input2, output, script);
		
	}
	
	
	public BoltMap2(Language language, String input1, String input2, String output, String script) {
		super(language, script);
		
		this.input1 = input1;
		this.input2 = input2;
		this.output = output;
		
	}
	

	
	@Override
	public T3 f(T1 v1, T2 v2) {
		
		if (hasSideEffects || !multithreaded) {
			synchronized(this)
			{
				return do_f(v1, v2);
			}
		} else {
			return do_f(v1, v2);
		}
		
	}
	

	private T3 do_f(T1 v1, T2 v2) {
		
		if (!hasSideEffects) clear();
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