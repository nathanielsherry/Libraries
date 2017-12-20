package net.sciencestudio.autodialogfx.old.view.editors;

import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import net.sciencestudio.autodialogfx.old.model.value.Value;

public class ProgressBarEditor extends AbstractEditor<Double>{

	private ProgressBar node;
	
	
	public ProgressBarEditor() {
		node = new ProgressBar(0d);
	}
	
	@Override
	public Double getEditorValue() {
		return node.getProgress();
	}

	@Override
	public Node getNode() {
		return node;
	}

	@Override
	protected void onValueChange() {
		node.setProgress(getModel().getValue());
	}

	@Override
	public void init(Value<Double> value) {
		node.setProgress(value.getValue());
	}

}
