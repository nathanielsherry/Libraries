package net.sciencestudio.autodialogfx.view.layouts;

import net.sciencestudio.autodialogfx.model.group.Group;
import net.sciencestudio.autodialogfx.view.View;

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
