package bolt.compiler;

import fava.signatures.TriFunction;

public class BoltJavaMap3<T1, T2, T3, T4> extends BoltJavaFunction implements TriFunction<T1, T2, T3, T4>{

	private TriFunction<T1, T2, T3, T4> innerFn;

	private String value1;
	private String value2;
	private String value3;
	private Class<?> t1, t2, t3, t4;
		

	public BoltJavaMap3(String value1, String value2, String value3, Class<?> t1, Class<?> t2, Class<?> t3, Class<?> t4) {
			
		this.t1 = t1;
		this.t2 = t2;
		this.t3 = t3;
		this.t4 = t4;
		
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;

	}
	

	
	protected String getSourceCode()
	{
		return generateSourceCode(
				"FnMap3",
				t1.getSimpleName() + ", " + t2.getSimpleName() + ", " + t3.getSimpleName() + ", " + t4.getSimpleName(), 
				t4.getSimpleName(), 
				t1.getSimpleName() + " " + value1 + ", " + t2.getSimpleName() + " " + value2 + ", " + t3.getSimpleName() + " " + value3
			);
	}
	
	@SuppressWarnings("unchecked")
	private void compile()
	{
		innerFn = (TriFunction<T1, T2, T3, T4>)getFunctionObject();
	}
	
	
	
	
	@Override
	public T4 apply(T1 v1, T2 v2, T3 v3) {
		if (innerFn == null) compile();
		return innerFn.apply(v1, v2, v3);
	}



	@Override
	protected void sourceCodeChanged() {
		innerFn = null;
	}
	
}