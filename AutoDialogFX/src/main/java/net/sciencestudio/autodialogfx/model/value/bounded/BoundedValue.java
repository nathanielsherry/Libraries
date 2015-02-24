package net.sciencestudio.autodialogfx.model.value.bounded;

import net.sciencestudio.autodialogfx.model.value.Value;

public interface BoundedValue<T extends Number> extends Value<T> {

	T getMin();
	T getMax();
	T getInterval();
	
}
