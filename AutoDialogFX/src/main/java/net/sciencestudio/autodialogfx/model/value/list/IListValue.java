package net.sciencestudio.autodialogfx.model.value.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sciencestudio.autodialogfx.model.value.IValue;
import net.sciencestudio.autodialogfx.view.View;
import net.sciencestudio.autodialogfx.view.editors.Editor;

public class IListValue<T> extends IValue<T> implements ListValue<T> {

	private List<T> values;
	
	public IListValue(String title, Class<T> cls, Class<? extends Editor<T>> view, T value, Collection<T> values) {
		super(title, cls, view, value);
		this.values = new ArrayList<>(values);
		addValidator(v -> values.contains(v));
	}

	public Collection<T> getValues() {
		return values;
	}


}
