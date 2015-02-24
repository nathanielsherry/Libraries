package net.sciencestudio.autodialogfx.view;

import net.sciencestudio.autodialogfx.model.Model;
import net.sciencestudio.autodialogfx.model.group.Group;
import net.sciencestudio.autodialogfx.model.value.Value;
import net.sciencestudio.autodialogfx.view.editors.Editors;
import net.sciencestudio.autodialogfx.view.layouts.Layouts;

public class Views {

	public static View forModel(Model<?> model, Class<? extends View> viewClass) {
		
		if (model instanceof Value) {
			return Editors.forValue((Value)model, (Class)viewClass);
		}
		
		if (model instanceof Group) {
			return Layouts.forGroup((Group)model, (Class)viewClass);
		}
		
		return null;
		
	}
	
}
