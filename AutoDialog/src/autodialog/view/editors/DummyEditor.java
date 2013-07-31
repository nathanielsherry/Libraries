package autodialog.view.editors;

import javax.swing.JComponent;
import javax.swing.JPanel;

import autodialog.controller.IAutoDialogController;
import autodialog.model.Parameter;
import autodialog.view.AutoPanel;

public class DummyEditor extends JPanel implements IEditor {

	private Parameter param;
	
	public DummyEditor(Parameter param, IAutoDialogController controller, AutoPanel view) {
		this.param = param;
	}
	
	@Override
	public boolean expandVertical() {
		return false;
	}

	@Override
	public boolean expandHorizontal() {
		return false;
	}

	@Override
	public LabelStyle getLabelStyle() {
		return LabelStyle.LABEL_HIDDEN;
	}

	@Override
	public void setFromParameter() {}

	@Override
	public void validateFailed() {}

	@Override
	public Object getEditorValue() {
		return param.getValue();
	}

	@Override
	public JComponent getComponent() {
		return this;
	}

}
