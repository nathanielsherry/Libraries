package net.sciencestudio.autodialogfx.old.view.decor;

import net.sciencestudio.autodialogfx.old.model.dummy.Dummy;
import net.sciencestudio.autodialogfx.old.view.View;

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
