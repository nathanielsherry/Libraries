package bolt.compiler;

import fava.signatures.FnMap2;


public class BoltJavaMap2<T1, T2, T3> extends BoltJavaFunction implements FnMap2<T1, T2, T3>{

	private FnMap2<T1, T2, T3> innerFn;

	private String value1;
	private String value2;
	private Class<?> t1, t2, t3;
		

	public BoltJavaMap2(String value1, String value2, Class<?> t1, Class<?> t2, Class<?> t3) {
			
		this.t1 = t1;
		this.t2 = t2;
		this.t3 = t3;
		
		this.value1 = value1;
		this.value2 = value2;

	}
	

	
	protected String getSourceCode()
	{
		return generateSourceCode(
				"FnMap2",
				t1.getName() + ", " + t2.getName() + ", " + t3.getName(), 
				t3.getName(), 
				t1.getName() + " " + value1 + ", " + t2.getName() + " " + value2
			);
	}
	
	private void compile()
	{

		innerFn = (FnMap2<T1, T2, T3>)getFunctionObject();

	}
	
	
	
	
	@Override
	public T3 f(T1 v1, T2 v2) {
		if (innerFn == null) compile();
		return innerFn.f(v1, v2);
	}



	@Override
	protected void sourceCodeChanged() {
		innerFn = null;
	}
	
}