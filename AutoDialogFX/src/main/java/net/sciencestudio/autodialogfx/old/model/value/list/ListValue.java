package net.sciencestudio.autodialogfx.old.model.value.list;

import java.util.Collection;

import net.sciencestudio.autodialogfx.old.model.value.Value;

public interface ListValue<T> extends Value<T> {

	public Collection<T> getValues();
	
}
