package net.sciencestudio.autodialogfx.view.editors;

import java.math.BigDecimal;
import java.text.NumberFormat;

import javafx.scene.Node;
import jfxtras.labs.scene.control.BigDecimalField;
import net.sciencestudio.autodialogfx.model.value.Value;
import net.sciencestudio.autodialogfx.model.value.bounded.BoundedValue;
import net.sciencestudio.autodialogfx.model.value.bounded.IBoundedValue;
import net.sciencestudio.chanje.ChangeBus;

public class SpinnerDoubleEditor extends AbstractEditor<Double> {

	BigDecimalField node;
	
	public SpinnerDoubleEditor(){}
	
	@Override
	public void init(Value<Double> value) {
		
		double interval = 1;
		
		if (value instanceof BoundedValue) {
			BoundedValue<Double> boundedValue = (BoundedValue<Double>) value;
			interval = boundedValue.getInterval();
		}
		
		node = new BigDecimalField(new BigDecimal(value.getValue()));
		node.setFormat(NumberFormat.getNumberInstance());
		node.setStepwidth(new BigDecimal(interval));
				
		node.setMaxWidth(100);
		
		node.numberProperty().addListener(change -> {
			getModel().setValue(node.getNumber().doubleValue());
		});
		
	}

	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public Double getEditorValue() {
		return node.getNumber().doubleValue();
	}

	@Override
	protected void onValueChange() {
		node.setNumber(new BigDecimal(getModel().getValue()));
	}
	
}
