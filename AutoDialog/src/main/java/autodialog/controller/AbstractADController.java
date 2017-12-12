package autodialog.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import autodialog.model.Parameter;
import autodialog.view.ParamListener;
import autodialog.view.editors.IEditor;

public abstract class AbstractADController implements IADController {


	private List<Parameter<?>> params;
	
	/**
	 * Creates an new controller to manage the provided {@link Parameter}s 
	 * @param params
	 */
	public AbstractADController(Collection<Parameter<?>> params) {
		this.params = new ArrayList<>(params);
		for(Parameter<?> param : params)
		{
			param.getEditor().addListener(new ParamListener<>(param, this));
		}
	}
	
	
	/**
	 * This method is called by {@link ParamListener} when a parameter's editor component is updated.
	 * It will call {@link IADController#validateParameters validateParameters}, and if validation succeeds,
	 * it will call {@link #parameterUpdated}. If validation fails, it will restore call 
	 * {@link IEditor#validateFailed validateFailed} on the {@link IEditor} for this {@link Parameter}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void editorUpdated(Parameter parameter) {

		Object oldValue = parameter.getValue();
		parameter.setValue(parameter.getEditor().getEditorValue());
		
		// if this input validates, signal the change, otherwise, reset
		// the value
		if (validateParameters()) {
			
			parameterUpdated(parameter);
			
		} else {
			
			// reset the parameter
			parameter.setValue(oldValue);
			
			//notify the editor that validation failed
			parameter.getEditor().validateFailed();
			
		}
		
	}


	@Override
	public List<Parameter<?>> getParameters() {
		return new ArrayList<>(params);
	}
	
	/**
	 * This method will be called once an {@link IEditor} for a {@link Parameter} has been changed,
	 * and the new value has been validated with a call to {@link IADController#validateParameters() validateParameters} 
	 * @param param
	 */
	public abstract void parameterUpdated(Parameter<?> param);

}
