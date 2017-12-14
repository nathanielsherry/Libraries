package autodialog.controller;

import java.util.ArrayList;
import java.util.List;

import autodialog.model.Parameter;
import autodialog.view.editors.Editor;

public abstract class AbstractADController implements IADController {


	protected List<Editor<?>> editors;
	
	/**
	 * Creates an new controller to manage the provided {@link Parameter}s 
	 * @param params
	 */
	public AbstractADController(List<Editor<?>> editors) {
		this.editors = new ArrayList<>(editors);
		for(Editor<?> editor : editors)
		{
			editor.getValueHook().addListener(v -> editorUpdated(editor));
		}
	}
	
	
	/**
	 * This method is called by {@link ParamListener} when a parameter's editor component is updated.
	 * It will call {@link IADController#validate}, and if validation succeeds,
	 * it will call {@link #parameterUpdated}. If validation fails, it will restore call 
	 * {@link Editor#validateFailed validateFailed} on the {@link Editor} for this {@link Parameter}
	 */
	@Override
	public <T> void editorUpdated(Editor<T> editor) {

		Parameter<T> param = editor.getParameter();
		T oldValue = param.getValue();
		param.setValue(editor.getEditorValue());
		
		// if this input validates, signal the change, otherwise, reset
		// the value
		if (validate()) {
			parameterUpdated(param);
			
		} else {
			
			// reset the parameter
			param.setValue(oldValue);
			
			//notify the editor that validation failed
			editor.validateFailed();
			
		}
		
	}


	@Override
	public List<Editor<?>> getEditors() {
		return new ArrayList<>(editors);
	}
	
	/**
	 * This method will be called once an {@link Editor} for a {@link Parameter} has been changed,
	 * and the new value has been validated with a call to {@link IADController#validate() validateParameters} 
	 * @param param
	 */
	public abstract void parameterUpdated(Parameter<?> param);

}
