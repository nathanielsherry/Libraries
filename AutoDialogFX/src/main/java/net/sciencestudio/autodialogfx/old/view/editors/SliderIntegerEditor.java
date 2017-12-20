package net.sciencestudio.autodialogfx.old.view.editors;

import javafx.scene.Node;
import javafx.scene.control.Slider;
import net.sciencestudio.autodialogfx.old.model.value.Value;
import net.sciencestudio.autodialogfx.old.model.value.bounded.BoundedValue;

public class SliderIntegerEditor extends AbstractEditor<Integer> {

	Slider node;
	
	public SliderIntegerEditor() {}
	
	
	@Override
	public void init(Value<Integer> v) {
		BoundedValue<Integer> value = (BoundedValue<Integer>) v;
		node = new Slider(value.getMin(), value.getMax(), value.getValue());
		node.setBlockIncrement(value.getInterval());
		node.valueProperty().addListener(change -> {
			value.setValue((int)node.getValue());
		});
	}
	

	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public Integer getEditorValue() {
		return (int)node.getValue();
	}

	@Override
	protected void onValueChange() {
		node.setValue(getModel().getValue());
	}

}
