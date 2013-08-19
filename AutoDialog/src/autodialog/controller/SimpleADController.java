package autodialog.controller;

import java.util.List;

import autodialog.model.Parameter;

public class SimpleADController extends AbstractADController {

	public SimpleADController(List<Parameter<?>> params) {
		super(params);
	}
	
	@Override
	public boolean validateParameters() {
		return true;
	}


	@Override
	public void parameterUpdated(Parameter<?> param) {}


}
