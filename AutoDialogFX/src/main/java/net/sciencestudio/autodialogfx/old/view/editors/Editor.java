package net.sciencestudio.autodialogfx.old.view.editors;

import net.sciencestudio.autodialogfx.old.model.value.Value;
import net.sciencestudio.autodialogfx.old.view.View;

public interface Editor<T> extends View {

	T getEditorValue();
	//boolean accepts(Value<?> value);
	Editor<T> initialize(Value<T> value);

	public static <T> Editor<T> forValue(Value<T> model) {
		try {
			Editor<T> view = (Editor<T>) model.getView().newInstance();
			view.initialize(model);
			return view;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
