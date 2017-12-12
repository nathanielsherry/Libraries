package autodialog.view;


import autodialog.controller.IADController;
import autodialog.model.Parameter;
import eventful.EventfulListener;

public class ParamListener<T> implements EventfulListener
{

	private Parameter<T>	param;
	private IADController	controller;


	public ParamListener(Parameter<T> param, IADController controller)
	{
		this.param = param;
		this.controller = controller;
	}

	@Override
	public void change()
	{
		controller.editorUpdated(param);
	}


}