package net.sciencestudio.autodialogfx.old;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sciencestudio.autodialogfx.old.model.Model;
import net.sciencestudio.autodialogfx.old.model.group.Group;
import net.sciencestudio.autodialogfx.old.model.group.IGroup;
import net.sciencestudio.autodialogfx.old.model.value.Value;
import net.sciencestudio.autodialogfx.old.view.AbstractView;
import net.sciencestudio.autodialogfx.old.view.View;
import net.sciencestudio.autodialogfx.old.view.layouts.VerticalLayout;

public class AutoDialog extends AbstractView implements View {

	private BorderPane node;
	private Group group;
	
	

	public AutoDialog(Value<?> value) {
		this(new IGroup(VerticalLayout.class, value.getTitle()).add(value), new Insets(6));
	}
	
	public AutoDialog(Group group) {
		this(group, new Insets(6));
	}
		
	public AutoDialog(Group group, Insets insets) {
		this.group = group;
		node = new BorderPane();
		node.setPadding(insets);
		node.setCenter(View.forModel(group).getNode());
		
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
	public Model<?> getModel() {
		return group;
	}

	
	public Stage present(Stage stage) {
		
		Scene scene = new Scene(node);
		stage.setScene(scene);
		stage.setTitle(getTitle());
		stage.sizeToScene();

		return stage;
	}
	

	
}

