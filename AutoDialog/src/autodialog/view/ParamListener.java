package autodialog.view;


import autodialog.controller.IAutoDialogController;
import autodialog.model.Parameter;
import eventful.EventfulListener;

public class ParamListener<T> implements EventfulListener
{

	private Parameter<T>			param;
	private IAutoDialogController	controller;


	public ParamListener(Parameter<T> param, IAutoDialogController controller)
	{
		this.param = param;
		this.controller = controller;
	}

	@Override
	public void change()
	{
		
		T oldValue = param.getValue();
		param.setValue(param.getEditor().getEditorValue());
		
		// if this input validates, signal the change, otherwise, reset
		// the value
		if (controller.validateParameters()) {
			
			controller.parametersUpdated();
			
		} else {
			// reset the parameter
			param.setValue(oldValue);
			
			//notify the editor that validation failed
			param.getEditor().validateFailed();
			
		}


	}


}