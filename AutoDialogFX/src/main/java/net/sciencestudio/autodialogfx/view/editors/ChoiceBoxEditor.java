package net.sciencestudio.autodialogfx.view.editors;


import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import net.sciencestudio.autodialogfx.model.value.Value;
import net.sciencestudio.autodialogfx.model.value.list.ListValue;

public class ChoiceBoxEditor<T> extends AbstractEditor<T>{

	ChoiceBox<T> node = new ChoiceBox<>();
	
	public ChoiceBoxEditor() {}
	
	@Override
	public void init(Value<T> v) {
		ListValue<T> value = (ListValue<T>) v;
		node.getItems().addAll(value.getValues());
		node.getSelectionModel().select(value.getValue());
		
		node.getSelectionModel().selectedItemProperty().addListener(change -> {
			getModel().setValue(node.getSelectionModel().getSelectedItem());
		});
	}
	

	@Override
	public Node getNode() {
		return node;
	}

	@Override
	protected void onValueChange() {
		node.getSelectionModel().select(getModel().getValue());
	}

	@Override
	public T getEditorValue() {
		return node.getSelectionModel().getSelectedItem();
	}

	@Override
	protected Class<?> acceptedClass() {
		return Object.class;
	}
	
	protected Class<? extends Value> acceptedValueType() {
		return ListValue.class;
	}
	
	public static <S> Class<ChoiceBoxEditor<S>> forClass() {
		return (Class<ChoiceBoxEditor<S>>)(Object)ChoiceBoxEditor.class;
	}



}
