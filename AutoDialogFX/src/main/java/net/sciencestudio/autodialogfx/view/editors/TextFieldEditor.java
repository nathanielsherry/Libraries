package net.sciencestudio.autodialogfx.view.editors;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import net.sciencestudio.autodialogfx.model.value.Value;
import net.sciencestudio.chanje.ChangeBus;

public class TextFieldEditor extends AbstractEditor<String> {

	TextField node;
	
	public TextFieldEditor(){}
	
	@Override
	public void init(Value<String> value) {
		
		node = new TextField(value.getValue());
		node.textProperty().addListener(change -> {
			value.setValue(node.getText());
		});
	}

	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public String getEditorValue() {
		return node.getText();
	}

	@Override
	protected void onValueChange() {
		node.setText(getModel().getValue());
	}


}
