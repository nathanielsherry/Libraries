package autodialog.view.javafx.editors;

import autodialog.model.Parameter;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class TextAreaEditor extends AbstractEditor<String> {

	TextArea node;
	
	public TextAreaEditor(){}
	
	@Override
	public void init(Parameter<String> value) {
		
		node = new TextArea(value.getValue());
		node.setWrapText(true);
		
		node.setPrefWidth(300);
		node.setPrefHeight(100);
		
		node.textProperty().addListener(change -> {
			getEditorValueHook().updateListeners(getEditorValue());
			if (!parameter.setValue(getEditorValue())) {
				validateFailed();
			}
		});
	}

	@Override
	public Node getComponent() {
		return node;
	}

	@Override
	public String getEditorValue() {
		return node.getText();
	}

	@Override
	public void setEditorValue(String value) {
		node.setText(value);
	}

	@Override
	public boolean expandVertical() {
		return true;
	}

	@Override
	public boolean expandHorizontal() {
		return true;
	}

	@Override
	public LabelStyle getLabelStyle() {
		return LabelStyle.LABEL_ON_TOP;
	}

	public void validateFailed() {
		setFromParameter();
	}


}
