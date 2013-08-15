package autodialog.controller;

import java.util.ArrayList;
import java.util.List;

import autodialog.model.Parameter;
import autodialog.view.ParamListener;

public class SimpleADController implements IADController {

	private List<Parameter<?>> params;
	private boolean accepted = false;
	
	public SimpleADController(List<Parameter<?>> params) {
		this.params = params;
		for(Parameter<?> param : params)
		{
			param.getEditor().addListener(new ParamListener<>(param, this));
		}
	}
	
	@Override
	public boolean validateParameters() {
		return true;
	}

	@Override
	public void parametersUpdated() {}

	@Override
	public List<Parameter<?>> getParameters() {
		return new ArrayList<>(params);
	}

	@Override
	public void submit() {
		accepted = true;
	}

	@Override
	public void cancel() {}

	@Override
	public void close() {}
	
	public boolean getDialogAccepted()
	{
		return accepted;
	}


}
