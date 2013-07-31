package autodialog.view.editors;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JSpinner;

import autodialog.controller.IAutoDialogController;
import autodialog.model.Parameter;
import autodialog.view.AutoPanel;
import autodialog.view.ParamListener;


public class IntegerEditor extends JSpinner implements IEditor
{

	private Parameter param;
	
	public IntegerEditor(Parameter param, IAutoDialogController controller, AutoPanel view)
	{
		
		this.param = param;
		
		getEditor().setPreferredSize(new Dimension(70, getEditor().getPreferredSize().height));
		setValue(param.intValue());

		addChangeListener(new ParamListener(param, controller, view));
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

	@Override
	public void setFromParameter()
	{
		setValue(param.intValue());
	}

	@Override
	public Object getEditorValue()
	{
		return getValue();
	}
	

	@Override
	public void validateFailed() {
		setFromParameter();
	}
	
}
