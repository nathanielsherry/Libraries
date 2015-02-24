package net.sciencestudio.autodialogfx.view.decor;

import net.sciencestudio.autodialogfx.model.dummy.Dummy;
import net.sciencestudio.autodialogfx.view.View;

public interface Decor extends View {

	Decor initialize(Dummy dummy);
	
	static View forDummy(Dummy model, Class<? extends Decor> viewClass) {
		try {
			Decor view = viewClass.newInstance();
			view.initialize(model);
			return view;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
