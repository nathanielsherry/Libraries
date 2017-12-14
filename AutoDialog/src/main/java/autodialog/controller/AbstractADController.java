package autodialog.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import autodialog.model.Parameter;
import autodialog.view.editors.IEditor;
import autodialog.view.swing.EditorListener;

public abstract class AbstractADController implements IADController {


	private List<IEditor<?>> editors;
	
	/**
	 * Creates an new controller to manage the provided {@link Parameter}s 
	 * @param params
	 */
	public AbstractADController(Collection<IEditor<?>> editors) {
		this.editors = new ArrayList<>(editors);
		for(IEditor<?> editor : editors)
		{
			editor.addListener(new EditorListener<>(editor, this));
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
	public <T> void editorUpdated(IEditor<T> editor) {

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
	public List<IEditor<?>> getEditors() {
		return new ArrayList<>(editors);
	}
	
	/**
	 * This method will be called once an {@link IEditor} for a {@link Parameter} has been changed,
	 * and the new value has been validated with a call to {@link IADController#validate() validateParameters} 
	 * @param param
	 */
	public abstract void parameterUpdated(Parameter<?> param);

}
