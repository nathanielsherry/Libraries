package net.sciencestudio.autodialogfx.view.editors;

import java.io.File;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import net.sciencestudio.autodialogfx.model.value.Value;

public class FilenameEditor extends AbstractEditor<File> {

	HBox node = new HBox(3);
	TextField filename;
	
	public FilenameEditor() {}
	
	@Override
	public void init(Value<File> value) {
		
		Button browse = new Button("\u2026");
		filename = new TextField(value.getValue().getAbsolutePath());
		
		node.getChildren().add(filename);
		node.getChildren().add(browse);
		
		HBox.setHgrow(filename, Priority.ALWAYS);
		
		FileChooser chooser = new FileChooser();
        browse.setOnAction(event -> {

            File file;
            chooser.setInitialDirectory(value.getValue().getParentFile());
            //if (save) {
            //    file = chooser.showSaveDialog(null);
            //} else {
                file = chooser.showOpenDialog(null);
            //}

            if (file != null) {
            	value.setValue(file);
            }
        });
        
        
		
	}

	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public File getEditorValue() {
		return new File(filename.getText());
	}

	@Override
	protected void onValueChange() {
		filename.setText(getModel().getValue().getAbsolutePath());
	}

}
