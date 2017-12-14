package autodialog.view.swing.editors;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import autodialog.model.Parameter;
import eventful.Eventful;


public class FloatEditor extends AbstractSwingEditor<Float>
{

	private Parameter<Float> param;
	private JSpinner control;
	
	public FloatEditor() {
		control = new JSpinner();
	}
	
	@Override
	public void initialize(Parameter<Float> param)
	{
		this.param = param;
		
		setFromParameter();
		param.getValueHook().addListener(v -> this.setFromParameter());
		
		
		control.setModel(new SpinnerNumberModel((Float)param.getValue(), null, null, 0.1d));
		control.getEditor().setPreferredSize(new Dimension(70, control.getEditor().getPreferredSize().height));
		control.setValue((Float)param.getValue());
		
		
		control.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				getValueHook().updateListeners(getEditorValue());
			}
		});

	}
	
	@Override
	public boolean expandVertical()
	{
		return false;
	}

	@Override
	public boolean expandHorizontal()
	{
		return false;
	}

	@Override
	public LabelStyle getLabelStyle()
	{
		return LabelStyle.LABEL_ON_SIDE;
	}

	@Override
	public JComponent getComponent()
	{
		return control;
	}

	@Override
	public void setFromParameter()
	{
		control.setValue((Float)param.getValue());
	}

	@Override
	public Float getEditorValue()
	{
		return (Float)control.getValue();
	}

	@Override
	public void validateFailed() {
		setFromParameter();
	}

	@Override
	public Parameter<Float> getParameter() {
		return param;
	}
	

}
