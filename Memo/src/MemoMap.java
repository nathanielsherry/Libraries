import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

import fava.signatures.FnMap;

public abstract class MemoMap<T1, T2> implements FnMap<T1, T2>
{
	
	LinkedHashMap<T1, T2> results;

	public MemoMap() {
		
		results = new LinkedHashMap<T1, T2>();
		
	}
	
	protected abstract T2 momoized(T1 v);
	
	@Override
	public T2 f(T1 v) {
		
		if (v == null) throw new NoSuchElementException();
		
		if (!results.containsKey(v)) results.put(v, momoized(v));
		return results.get(v);
		
	}
	
}