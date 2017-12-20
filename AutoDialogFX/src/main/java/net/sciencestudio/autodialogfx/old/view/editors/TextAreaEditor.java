package net.sciencestudio.autodialogfx.old.view.editors;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import net.sciencestudio.autodialogfx.old.model.value.Value;
import net.sciencestudio.autodialogfx.old.view.ViewProperties.LabelStyle;

public class TextAreaEditor extends AbstractEditor<String> {

	TextArea node;
	
	public TextAreaEditor(){}
	
	@Override
	public void init(Value<String> value) {
		
		node = new TextArea(value.getValue());
		node.setWrapText(true);
		
		node.setPrefWidth(300);
		node.setPrefHeight(100);
		
		node.textProperty().addListener(change -> {
			value.setValue(node.getText());
		});
		
		getProperties().labelStyle = LabelStyle.LABEL_ON_TOP;
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
