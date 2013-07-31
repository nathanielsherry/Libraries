package autodialog.view.editors;

import autodialog.model.Parameter;
import autodialog.view.AutoPanel;
import autodialog.controller.IAutoDialogController;

public interface ICustomEditor extends IEditor {

	public abstract ICustomEditor construct(Parameter param, IAutoDialogController controller, AutoPanel view);
	
}
