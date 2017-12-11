package net.sciencestudio.autodialogfx.view.editors;

import net.sciencestudio.autodialogfx.changes.EnabledChange;
import net.sciencestudio.autodialogfx.changes.ValuedChange;
import net.sciencestudio.autodialogfx.model.value.Value;
import net.sciencestudio.autodialogfx.view.AbstractView;

public abstract class AbstractEditor<T> extends AbstractView implements Editor<T> {

	private Value<T> value;
	
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
	public String getTitle() {
		return value.getTitle();
	}

	public abstract void init(Value<T> value);
		
	
}
