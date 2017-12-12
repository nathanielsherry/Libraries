package autodialog.view.editors;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import autodialog.model.Parameter;
import eventful.Eventful;


public class BooleanEditor extends Eventful implements IEditor<Boolean>
{
	
	private Parameter<Boolean> param;
	private JCheckBox control;

	public BooleanEditor() {
		control = new JCheckBox();
	}
	
	@Override
	public void initialize(Parameter<Boolean> param)
	{
		this.param = param;
		
		setFromParameter();
		control.setAlignmentX(Component.LEFT_ALIGNMENT);
		control.setOpaque(false);
		
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
	public JComponent getComponent()
	{
		return control;
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
	public void setFromParameter()
	{
		control.setSelected(param.getValue());
	}

	@Override
	public Boolean getEditorValue()
	{
		return control.isSelected();
	}
	

	@Override
	public void validateFailed() {
		setFromParameter();
	}
	
}
