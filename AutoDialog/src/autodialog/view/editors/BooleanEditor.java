package autodialog.view.editors;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import autodialog.controller.IAutoDialogController;
import autodialog.model.Parameter;
import autodialog.view.AutoPanel;
import autodialog.view.ParamListener;


public class BooleanEditor extends JCheckBox implements IEditor
{
	
	Parameter param;

	public BooleanEditor(Parameter param, IAutoDialogController controller, AutoPanel view)
	{
		this.param = param;
		
		setSelected(param.boolValue());
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setOpaque(false);
		
		addChangeListener(new ParamListener(param, controller, view));
	}
	
	@Override
	public boolean expandVertical()
	{
		return false;
	}

	@Override
	public JComponent getComponent()
	{
		return this;
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
		setSelected(param.boolValue());
	}

	@Override
	public Object getEditorValue()
	{
		return isSelected();
	}
	

	@Override
	public void validateFailed() {
		setFromParameter();
	}
	
}
