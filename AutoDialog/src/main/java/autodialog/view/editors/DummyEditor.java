package autodialog.view.editors;

import javax.swing.JComponent;
import javax.swing.JPanel;

import autodialog.model.Parameter;
import eventful.Eventful;

public class DummyEditor extends Eventful implements IEditor<Object> {

	private Parameter<Object> param;
	private JComponent component;
	

	public DummyEditor() {
		this(new JPanel());
	}
	
	public DummyEditor(JComponent component) {
		this.component = component;
	}
	
	@Override
	public void initialize(Parameter<Object> param)
	{
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
		return component;
	}

}
