package net.sciencestudio.autodialogfx.view.editors;

import java.util.Optional;

import net.sciencestudio.autodialogfx.changes.EnabledChange;
import net.sciencestudio.autodialogfx.changes.ValuedChange;
import net.sciencestudio.autodialogfx.model.Model;
import net.sciencestudio.autodialogfx.model.value.Value;
import net.sciencestudio.autodialogfx.view.ViewProperties;

public abstract class AbstractEditor<T> implements Editor<T> {

	private Value<T> value;
	private ViewProperties properties = new ViewProperties();
	
	AbstractEditor() {}

	@Override
	public final Editor<T> initialize(Value<T> model) {
		
		this.value = (Value<T>) model;
		
		value.listen(ValuedChange.class, change -> { 
			if (!change.isFor(value)) { return; }
			if (getModel().getValue().equals(getEditorValue())) { return; }
			onValueChange();  
		});
		
		value.listen(EnabledChange.class, change -> { 
			if (!change.isFor(value)) { return; }
			if (getNode().isDisable() != value.isEnabled()) { return; }
			onEnabledChange();
		});
		
		init(value);
		
		return this;
	}

	@Override
	public Value<T> getModel() {
		return value;
	}
	
	
	protected abstract void onValueChange();
	
	protected void onEnabledChange() {
		getNode().setDisable(!value.isEnabled());
	}
	
	@Override
	public ViewProperties getProperties() {
		return properties;
	}
	
	@Override
	public String getTitle() {
		return value.getTitle();
	}

	public abstract void init(Value<T> value);
	
	public boolean accepts(Value<?> value) {
		if (!acceptedClass().isAssignableFrom(value.getValueClass())) { return false; }
		if (!acceptedValueType().isAssignableFrom(value.getClass())) { return false; }
		return true;
	}
	
	protected abstract Class<?> acceptedClass();
	
	protected Class<? extends Value> acceptedValueType() {
		return Value.class;
	}
	
	
	
}
