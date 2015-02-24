package net.sciencestudio.autodialogfx.view;


import net.sciencestudio.autodialogfx.model.Model;
import net.sciencestudio.autodialogfx.model.dummy.Dummy;
import net.sciencestudio.autodialogfx.model.group.Group;
import net.sciencestudio.autodialogfx.model.value.Value;
import net.sciencestudio.autodialogfx.view.decor.Decor;
import net.sciencestudio.autodialogfx.view.editors.Editor;
import net.sciencestudio.autodialogfx.view.layouts.Layout;
import javafx.scene.Node;

public interface View {

	Model<?> getModel();
	
	Node getNode();
	String getTitle();
	ViewProperties getProperties();
	
	public static View forModel(Model<?> model, Class<? extends View> viewClass) {
		
		if (model instanceof Value) {
			return Editor.forValue((Value)model, (Class)viewClass);
		}
		
		if (model instanceof Group) {
			return Layout.forGroup((Group)model, (Class)viewClass);
		}
		
		if (model instanceof Dummy) {
			return Decor.forDummy((Dummy)model, (Class)viewClass);
		}
		
		return null;
		
	}
	
	
}
