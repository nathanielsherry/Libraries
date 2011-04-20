package bolt.compiler;

import fava.signatures.FnMap;

public class BoltJavaMap<T1, T2> extends BoltJavaFunction implements FnMap<T1, T2>{

	private FnMap<T1, T2> innerFn;

	private String value;
	private Class<?> t1, t2;
		

	public BoltJavaMap(String value, Class<?> t1, Class<?> t2) {
			
		this.t1 = t1;
		this.t2 = t2;
		
		this.value = value;

	}
	

	
	protected String getSourceCode()
	{
		return generateSourceCode(
				"FnMap",
				t1.getSimpleName() + ", " + t2.getSimpleName(), 
				t2.getSimpleName(), 
				t1.getSimpleName() + " " + value
			);
	}
	
	private void compile()
	{

		innerFn = (FnMap<T1, T2>)getFunctionObject();

	}
	
	
	
	
	@Override
	public T2 f(T1 v) {
		if (innerFn == null) compile();
		return innerFn.f(v);
	}



	@Override
	protected void sourceCodeChanged() {
		innerFn = null;
	}
	
}
