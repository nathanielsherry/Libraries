package net.sciencestudio.autodialogfx.view;


import net.sciencestudio.autodialogfx.model.Model;
import javafx.scene.Node;

public interface View {

	Model<?> getModel();
	
	Node getNode();
	String getTitle();
	ViewProperties getProperties();
	
}
