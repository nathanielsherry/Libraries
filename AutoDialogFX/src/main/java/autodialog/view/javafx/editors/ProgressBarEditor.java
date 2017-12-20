package autodialog.view.javafx.editors;

import autodialog.model.Parameter;
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
	public Node getComponent() {
		return node;
	}

	@Override
	public void setEditorValue(Double value) {
		node.setProgress(value);
	}

	@Override
	public void init(Parameter<Double> value) {
		node.setProgress(value.getValue());
	}

	@Override
	public boolean expandVertical() {
		return false;
	}

	@Override
	public boolean expandHorizontal() {
		return true;
	}

	@Override
	public LabelStyle getLabelStyle() {
		return LabelStyle.LABEL_ON_TOP;
	}



}
