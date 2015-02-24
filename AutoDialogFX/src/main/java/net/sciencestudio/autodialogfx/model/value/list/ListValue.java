package net.sciencestudio.autodialogfx.model.value.list;

import java.util.Collection;

import net.sciencestudio.autodialogfx.model.value.Value;

public interface ListValue<T> extends Value<T> {

	public Collection<T> getValues();
	
}
