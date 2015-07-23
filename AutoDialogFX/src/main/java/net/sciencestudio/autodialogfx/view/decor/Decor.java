package net.sciencestudio.autodialogfx.view.decor;

import net.sciencestudio.autodialogfx.model.dummy.Dummy;
import net.sciencestudio.autodialogfx.view.View;

public interface Decor extends View {

	Decor initialize(Dummy dummy);
	
	static View forDummy(Dummy model) {
		try {
			Decor view = (Decor) model.getView().newInstance();
			view.initialize(model);
			return view;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
