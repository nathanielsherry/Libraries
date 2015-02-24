package net.sciencestudio.autodialogfx.view.editors;

import net.sciencestudio.autodialogfx.model.value.Value;

public class Editors {

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
