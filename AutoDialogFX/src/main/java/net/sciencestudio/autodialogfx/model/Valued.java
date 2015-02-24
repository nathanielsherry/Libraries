package net.sciencestudio.autodialogfx.model;

public interface Valued<T> {

	boolean setValue(T value);
	T getValue();
	T getProposedValue();
	
}
