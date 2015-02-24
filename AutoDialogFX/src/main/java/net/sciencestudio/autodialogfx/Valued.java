package net.sciencestudio.autodialogfx;

public interface Valued<T> {

	boolean setValue(T value);
	T getValue();
	T getProposedValue();
	
}
