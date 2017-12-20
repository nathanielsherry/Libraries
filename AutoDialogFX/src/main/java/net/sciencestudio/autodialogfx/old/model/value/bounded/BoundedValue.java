package net.sciencestudio.autodialogfx.old.model.value.bounded;

import net.sciencestudio.autodialogfx.old.model.value.Value;

public interface BoundedValue<T extends Number> extends Value<T> {

	T getMin();
	T getMax();
	T getInterval();
	
}
