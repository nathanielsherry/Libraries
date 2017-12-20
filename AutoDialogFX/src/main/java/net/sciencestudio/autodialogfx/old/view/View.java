package net.sciencestudio.autodialogfx.old.view;


import javafx.scene.Node;
import net.sciencestudio.autodialogfx.old.model.Model;
import net.sciencestudio.autodialogfx.old.model.dummy.Dummy;
import net.sciencestudio.autodialogfx.old.model.group.Group;
import net.sciencestudio.autodialogfx.old.model.value.Value;
import net.sciencestudio.autodialogfx.old.view.decor.Decor;
import net.sciencestudio.autodialogfx.old.view.editors.Editor;
import net.sciencestudio.autodialogfx.old.view.layouts.Layout;

public interface View {

	Model<?> getModel();
	
	Node getNode();
	String getTitle();
	ViewProperties getProperties();
	
	public static View forModel(Model<?> model) {
		
		if (model instanceof Value) {
			return Editor.forValue((Value<?>)model);
		}
		
		if (model instanceof Group) {
			return Layout.forGroup((Group)model);
		}
		
		if (model instanceof Dummy) {
			return Decor.forDummy((Dummy)model);
		}
		
		return null;
		
	}
	
	
}
