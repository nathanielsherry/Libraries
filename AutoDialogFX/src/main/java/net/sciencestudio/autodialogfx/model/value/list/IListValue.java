package net.sciencestudio.autodialogfx.model.value.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sciencestudio.autodialogfx.model.value.IValue;
import net.sciencestudio.autodialogfx.view.editors.Editor;

public class IListValue<T> extends IValue<T> implements ListValue<T> {

	private List<T> values;
	
	public IListValue(String title, T value, Class<? extends Editor> view, Collection<T> values) {
		super(title, value, (Class<? extends Editor<T>>) view);
		this.values = new ArrayList<>(values);
		addValidator(v -> values.contains(v));
	}
	
	public Collection<T> getValues() {
		return values;
	}


}
