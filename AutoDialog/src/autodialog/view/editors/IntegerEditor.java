package autodialog.view.editors;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import autodialog.model.Parameter;
import eventful.Eventful;


public class IntegerEditor extends Eventful implements IEditor<Integer>
{

	private Parameter<Integer> param;
	private JSpinner control;
	
	public IntegerEditor() {
		control = new JSpinner();
	}
	
	@Override
	public void initialize(Parameter<Integer> param)
	{	
		this.param = param;
		
		control.getEditor().setPreferredSize(new Dimension(70, control.getEditor().getPreferredSize().height));
		control.setValue(param.getValue());

		
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
		control.setValue(param.getValue());
	}

	@Override
	public Integer getEditorValue()
	{
		return (Integer)control.getValue();
	}
	

	@Override
	public void validateFailed() {
		setFromParameter();
	}
	
}
