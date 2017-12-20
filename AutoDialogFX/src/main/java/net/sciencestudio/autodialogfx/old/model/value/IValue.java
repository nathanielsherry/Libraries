package net.sciencestudio.autodialogfx.old.model.value;

import java.io.Serializable;

import net.sciencestudio.autodialogfx.old.changes.ValuedChange;
import net.sciencestudio.autodialogfx.old.view.editors.Editor;

/**
 * 
 * This class defines a parameter for a filter.
 * 
 * @author Nathaniel Sherry, 2009-2012
 */

public class IValue<T> extends AbstractValue<T> implements Serializable {


	public IValue(String title, T value, Class<? extends Editor<T>> view) {
		super(title, value, view);
	}


	
	@Override
	public boolean setValue(T value) {
		
		if (this.value.equals(value)) { return false; }
		
		
		setProposedValue(value);
		if (validate(value)) {
			this.value = value;
		}
		setProposedValue(this.value);
		
		//broadcast change either way, since view may want to know that value wasn't changed
		broadcast(new ValuedChange<>(this));
		return true;
	}
	

	@Override
	public String toString() {
		String str = "Parameter " + getTitle();
		if (value != null)
			str += ": " + value.toString();
		return str;
	}





}
