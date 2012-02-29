package bolt.compiler;

import fava.signatures.FnCondition;

public class BoltJavaCondition<T1> extends BoltJavaFunction implements FnCondition<T1>
{


	private FnCondition<T1> innerFn;

	private String value;
	private Class<?> t1;
		

	public BoltJavaCondition(String value, Class<? extends T1> t1) {
			
		this.t1 = t1;
		
		this.value = value;

	}
	

	
	protected String getSourceCode()
	{
		return generateSourceCode(
				"FnCondition",
				t1.getSimpleName(), 
				"Boolean", 
				t1.getSimpleName() + " " + value
			);
	}
	
	@SuppressWarnings("unchecked")
	private void compile()
	{
		innerFn = (FnCondition<T1>)getFunctionObject();
	}
	
	
	
	
	@Override
	public Boolean f(T1 v) {
		if (innerFn == null) compile();
		return innerFn.f(v);
	}



	@Override
	protected void sourceCodeChanged() {
		innerFn = null;
	}

}
