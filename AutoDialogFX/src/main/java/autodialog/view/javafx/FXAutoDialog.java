package autodialog.view.javafx;

import java.util.List;

import autodialog.model.Group;
import autodialog.model.Parameter;
import autodialog.view.javafx.editors.FXEditorFactory;
import autodialog.view.javafx.layouts.FXLayoutFactory;
import autodialog.view.javafx.layouts.SimpleFXLayout;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXAutoDialog {

	private BorderPane node;
	private Group group;
	
		
	public FXAutoDialog(Group group) {
		this.group = group;
		node = new BorderPane();
		node.setPadding(new Insets(6));

		node.setCenter(FXLayoutFactory.forGroup(group).getComponent());
		
	}
	

	
	public void initialize() {
		
		Stage dialog = new Stage();
		//dialog.initStyle(StageStyle.UNIFIED);
		dialog.setResizable(false);
		
		Scene scene = new Scene(node);
		dialog.setScene(scene);
		dialog.setTitle(group.getName());
		dialog.sizeToScene();
		dialog.show();
	}
	

	
}

