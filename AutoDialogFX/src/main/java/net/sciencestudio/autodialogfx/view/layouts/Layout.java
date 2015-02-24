package net.sciencestudio.autodialogfx.view.layouts;

import net.sciencestudio.autodialogfx.model.Model;
import net.sciencestudio.autodialogfx.model.group.Group;
import net.sciencestudio.autodialogfx.model.group.IGroup;
import net.sciencestudio.autodialogfx.view.View;

public interface Layout extends View {

	void initialize(Group group);
	
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
