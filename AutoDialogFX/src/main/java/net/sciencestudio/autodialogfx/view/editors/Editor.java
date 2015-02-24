package net.sciencestudio.autodialogfx.view.editors;

import net.sciencestudio.autodialogfx.model.Model;
import net.sciencestudio.autodialogfx.model.value.Value;
import net.sciencestudio.autodialogfx.view.View;

public interface Editor<T> extends View {

	T getEditorValue();
	//boolean accepts(Value<?> value);
	Editor<T> initialize(Value<T> value);

	public static <T> Editor<T> forValue(Value<T> model, Class<? extends Editor<T>> viewClass) {
		try {
			Editor<T> view = viewClass.newInstance();
			view.initialize(model);
			return view;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
