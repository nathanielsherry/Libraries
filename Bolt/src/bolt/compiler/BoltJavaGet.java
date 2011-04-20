package bolt.compiler;

import fava.signatures.FnCombine;
import fava.signatures.FnGet;

public class BoltJavaGet<T1> extends BoltJavaFunction implements FnGet<T1>{

	private FnGet<T1> innerFn;

	private Class<?> t1;
		

	public BoltJavaGet(Class<?> t1) {
			
		this.t1 = t1;


	}
	

	
	protected String getSourceCode()
	{
		return generateSourceCode(
				"FnGet",
				t1.getSimpleName(), 
				t1.getSimpleName(), 
				""
			);
	}
	
	private void compile()
	{

		innerFn = (FnGet<T1>)getFunctionObject();

	}
	
	
	
	
	@Override
	public T1 f() {
		if (innerFn == null) compile();
		return innerFn.f();
	}



	@Override
	protected void sourceCodeChanged() {
		innerFn = null;
	}
	
}
