package net.sciencestudio.autodialogfx.old.view.editors;

import javafx.scene.Node;
import javafx.scene.control.Slider;
import net.sciencestudio.autodialogfx.old.model.value.Value;
import net.sciencestudio.autodialogfx.old.model.value.bounded.BoundedValue;

public class SliderDoubleEditor extends AbstractEditor<Double> {

	Slider node;
	
	public SliderDoubleEditor() {}
	

	@Override
	public void init(Value<Double> v) {
		BoundedValue<Double> value = (BoundedValue<Double>) v;
		node = new Slider(value.getMin(), value.getMax(), value.getValue());
		node.setBlockIncrement(value.getInterval());
		node.valueProperty().addListener(change -> {
			value.setValue(node.getValue());
		});
	}

	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public Double getEditorValue() {
		return node.getValue();
	}

	@Override
	protected void onValueChange() {
		node.setValue(getModel().getValue());
	}

}
