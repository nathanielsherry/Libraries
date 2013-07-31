package autodialog.view.editors;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import autodialog.controller.IAutoDialogController;
import autodialog.model.Parameter;
import autodialog.view.AutoPanel;
import autodialog.view.ParamListener;


public class EnumEditor extends JComboBox<Object> implements IEditor
{

	private Parameter param;
	
	public EnumEditor(Parameter param, IAutoDialogController controller, AutoPanel view)
	{
		super((Enum<?>[]) param.possibleValues);
	
		this.param = param;
		
		setSelectedItem(param.getValue());
		setAlignmentX(Component.LEFT_ALIGNMENT);
		
		addActionListener(new ParamListener(param, controller, view));
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
		return this;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setFromParameter()
	{
		setSelectedItem(param.<Enum>enumValue());
	}

	@Override
	public Object getEditorValue()
	{
		return getSelectedItem();
	}
	

	@Override
	public void validateFailed() {
		setFromParameter();
	}
	
}
