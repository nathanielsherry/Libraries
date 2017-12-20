package net.sciencestudio.autodialogfx.old.view.layouts;

import net.sciencestudio.autodialogfx.old.model.group.Group;
import net.sciencestudio.autodialogfx.old.view.View;

public interface Layout extends View {

	void initialize(Group group);
	
	public static Layout forGroup(Group group) {

		try {
			Layout layout = (Layout) group.getView().newInstance();
			layout.initialize(group);
			return layout;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	

	
}
