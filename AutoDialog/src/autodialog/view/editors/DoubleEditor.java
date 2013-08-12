package autodialog.view.editors;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import autodialog.model.Parameter;
import eventful.Eventful;


public class DoubleEditor extends Eventful implements IEditor<Double>
{

	private Parameter<Double> param;
	private JSpinner control;
	
	public DoubleEditor() {
		control = new JSpinner();
	}
	
	@Override
	public void initialize(Parameter<Double> param)
	{
		this.param = param;
		
		
		control.setModel(new SpinnerNumberModel((Double)param.getValue(), null, null, 0.1d));
		control.getEditor().setPreferredSize(new Dimension(70, control.getEditor().getPreferredSize().height));
		control.setValue((Double)param.getValue());
		
		
		control.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				updateListeners();
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
		control.setValue((Double)param.getValue());
	}

	@Override
	public Double getEditorValue()
	{
		return (Double)control.getValue();
	}

	@Override
	public void validateFailed() {
		setFromParameter();
	}
	
}
