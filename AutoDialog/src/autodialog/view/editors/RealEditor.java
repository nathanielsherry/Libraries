package autodialog.view.editors;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import autodialog.controller.IAutoDialogController;
import autodialog.model.Parameter;
import autodialog.view.AutoPanel;
import autodialog.view.ParamListener;


public class RealEditor extends JSpinner implements IEditor
{

	private Parameter param;
	
	public RealEditor(Parameter param, IAutoDialogController controller, AutoPanel view)
	{
		this.param = param;
		
		setModel(new SpinnerNumberModel(param.realValue(), null, null, 0.1));
		getEditor().setPreferredSize(new Dimension(70, getEditor().getPreferredSize().height));
		setValue(param.realValue());
		
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
		setValue(param.realValue());
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
