package net.sciencestudio.autodialogfx.model.value.bounded;

import net.sciencestudio.autodialogfx.model.value.IValue;
import net.sciencestudio.autodialogfx.view.editors.Editor;

public class IBoundedValue<T extends Number> extends IValue<T> implements BoundedValue<T> {

	private T min, max, interval;

	
	public IBoundedValue(String title, T value, Class<? extends Editor<T>> view, T min, T max, T interval) {
		super(title, value, view);
		this.min = min;
		this.max = max;
		this.interval = interval;
		addValidator(v -> v.doubleValue() >= min.doubleValue() && v.doubleValue() <= max.doubleValue());
	}

	
	public T getMin() {
		return min;
	}

	public T getMax() {
		return max;
	}
	
	public T getInterval() {
		return interval;
	}


}
