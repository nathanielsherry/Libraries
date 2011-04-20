package bolt.compiler;

import fava.signatures.FnFold;
import fava.signatures.FnMap;

public class BoltJavaFold<T1, T2> extends BoltJavaFunction implements FnFold<T1, T2>{


	private FnFold<T1, T2> innerFn;

	private String base, value;
	private Class<?> t1, t2;
	
	
	public BoltJavaFold(String base, String value, Class<?> t1, Class<?> t2) {
		
		this.t1 = t1;
		this.t2 = t2;
		
		this.base = base;
		this.value = value;

	}
	

	
	protected String getSourceCode()
	{
		return generateSourceCode(
				"FnFold",
				t1.getName() + ", " + t2.getName(), 
				t2.getName(), 
				t1.getName() + " " + value + ", " + t2.getName() + " " + base
			);
	}
	
	private void compile()
	{

		innerFn = (FnFold<T1, T2>)getFunctionObject();

	}
	
	
	@Override
	public T2 f(T1 v, T2 b) {
		if (innerFn == null) compile();
		return innerFn.f(v, b);
	}



	@Override
	protected void sourceCodeChanged() {
		innerFn = null;
	}

	
	
}