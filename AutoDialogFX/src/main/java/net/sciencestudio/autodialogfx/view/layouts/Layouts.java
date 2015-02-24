package net.sciencestudio.autodialogfx.view.layouts;

import net.sciencestudio.autodialogfx.model.group.Group;

public class Layouts {

	public static Layout forGroup(Group group, Class<? extends Layout> layoutClass) {

		try {
			Layout layout = layoutClass.newInstance();
			layout.initialize(group);
			return layout;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
