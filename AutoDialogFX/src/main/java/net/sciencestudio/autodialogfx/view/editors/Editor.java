package net.sciencestudio.autodialogfx.view.editors;

import net.sciencestudio.autodialogfx.model.Model;
import net.sciencestudio.autodialogfx.model.value.Value;
import net.sciencestudio.autodialogfx.view.View;

public interface Editor<T> extends View {

	T getEditorValue();
	boolean accepts(Value<?> value);
	Editor<T> initialize(Value<T> value);
	
}
