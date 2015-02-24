package net.sciencestudio.autodialogfx.view.editors;

import java.math.BigDecimal;
import java.text.NumberFormat;

import javafx.scene.Node;
import jfxtras.labs.scene.control.BigDecimalField;
import net.sciencestudio.autodialogfx.model.value.Value;
import net.sciencestudio.autodialogfx.model.value.bounded.BoundedValue;
import net.sciencestudio.autodialogfx.model.value.bounded.IBoundedValue;

public class SpinnerIntegerEditor extends AbstractEditor<Integer> {

	BigDecimalField node;
	
	public SpinnerIntegerEditor(){}
	
	@Override
	public void init(Value<Integer> value) {
		
		double interval = 1;
		
		if (value instanceof BoundedValue) {
			BoundedValue<Integer> boundedValue = (BoundedValue<Integer>) value;
			interval = boundedValue.getInterval();
		}
		
		node = new BigDecimalField(new BigDecimal(value.getValue()));
		node.setFormat(NumberFormat.getNumberInstance());
		node.setStepwidth(new BigDecimal(interval));
				
		node.setMaxWidth(100);
		
		node.numberProperty().addListener(change -> {
			getModel().setValue(node.getNumber().intValue());
		});
		
	}

	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public Integer getEditorValue() {
		return node.getNumber().intValue();
	}

	@Override
	protected void onValueChange() {
		node.setNumber(new BigDecimal(getModel().getValue()));
	}

	@Override
	protected Class<?> acceptedClass() {
		return Integer.class;
	}	
	
}
