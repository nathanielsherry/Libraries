package autodialog.model;

import autodialog.model.style.Style;
import eventful.EventfulType;

public interface Value<T> {

	Style<T> getStyle();
	String getName();
	
	boolean setValue(T value);
	T getValue();
	
	boolean isEnabled();
	void setEnabled(boolean enabled);

	EventfulType<T> getValueHook();
	EventfulType<Boolean> getEnabledHook();
	
}
