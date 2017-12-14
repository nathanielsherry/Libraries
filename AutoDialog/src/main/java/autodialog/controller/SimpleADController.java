package autodialog.controller;

import java.util.List;

import autodialog.model.Parameter;
import autodialog.view.editors.IEditor;

public class SimpleADController extends AbstractADController {

	public SimpleADController(List<IEditor<?>> params) {
		super(params);
	}
	
	@Override
	public boolean validate() {
		return true;
	}


	@Override
	public void parameterUpdated(Parameter<?> param) {}


}
