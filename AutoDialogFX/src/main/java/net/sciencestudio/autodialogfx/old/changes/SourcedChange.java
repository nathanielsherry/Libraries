package net.sciencestudio.autodialogfx.old.changes;


public class SourcedChange<T> {

	private T source;

	public SourcedChange(T model) {
		this.source = model;
	}

	public T getModel() {
		return source;
	}
	
	public boolean isFor(T other) {
		return source.equals(other);
	}

}
