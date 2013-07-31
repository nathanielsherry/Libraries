package autodialog.view.editors;

import javax.swing.JComponent;
import javax.swing.JSeparator;

public class SeparatorEditor extends JSeparator implements IEditor
{
	
	public SeparatorEditor()
	{
		super(JSeparator.HORIZONTAL);
	}

	@Override
	public boolean expandVertical()
	{
		return false;
	}

	@Override
	public boolean expandHorizontal()
	{
		return true;
	}

	@Override
	public JComponent getComponent()
	{
		return this;
	}

	@Override
	public LabelStyle getLabelStyle()
	{
		return LabelStyle.LABEL_HIDDEN;
	}

	@Override
	public void setFromParameter() {}

	@Override
	public Object getEditorValue()
	{
		return null;
	}

	@Override
	public void validateFailed() {}
	
}
