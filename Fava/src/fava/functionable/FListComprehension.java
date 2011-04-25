package fava.functionable;

public interface FListComprehension<T> {
	
	public Iterable<T> in();
	public T let(T x);
	public Boolean where(T x);
	
}
