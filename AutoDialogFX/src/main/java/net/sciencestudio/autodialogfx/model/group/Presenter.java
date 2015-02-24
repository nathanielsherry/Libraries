package net.sciencestudio.autodialogfx.model.group;

import java.util.Optional;

import net.sciencestudio.autodialogfx.model.Model;
import net.sciencestudio.autodialogfx.view.View;
import net.sciencestudio.autodialogfx.view.ViewProperties;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class Presenter implements View {

	private BorderPane node;
	private Group group;
	
	
	
	public Presenter(Group group) {
		this(group, new Insets(6));
	}
		
	public Presenter(Group group, Insets insets) {
		this.group = group;
		node = new BorderPane();
		node.setPadding(insets);
		
		node.setCenter(View.forModel(group, group.getView()).getNode());
		
	}
	
	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public String getTitle() {
		return group.getTitle();
	}

	@Override
	public ViewProperties getProperties() {
		return null;
	}

	@Override
	public Model<?> getModel() {
		return group;
	}

}
