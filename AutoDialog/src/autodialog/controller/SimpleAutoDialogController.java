package autodialog.controller;

import java.util.List;

import autodialog.model.Parameter;

public class SimpleAutoDialogController implements IAutoDialogController {

	private List<Parameter> params;
	private boolean accepted = false;
	
	public SimpleAutoDialogController(List<Parameter> params) {
		this.params = params;
	}
	
	@Override
	public boolean validateParameters() {
		return true;
	}

	@Override
	public void parametersUpdated() {}

	@Override
	public List<Parameter> getParameters() {
		return params;
	}

	@Override
	public void submit() {
		accepted = true;
	}

	@Override
	public void cancel() {}
	
	
	public boolean getDialogAccepted()
	{
		return accepted;
	}

}
