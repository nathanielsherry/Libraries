package net.sciencestudio.autodialogfx.old.model;

public interface Valued<T> {

	boolean setValue(T value);
	T getValue();
	T getProposedValue();
	
}
