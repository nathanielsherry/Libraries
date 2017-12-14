package autodialog.view.editors;

import autodialog.model.Parameter;
import eventful.EventfulType;
import eventful.IEventful;

public interface Editor<T>
{
	
		
	public enum LabelStyle {
		LABEL_ON_TOP,
		LABEL_ON_SIDE,
		LABEL_HIDDEN
	}
	

	void initialize(Parameter<T> param);

	boolean expandVertical();
	boolean expandHorizontal();
	LabelStyle getLabelStyle();
	
	/**
	 * Restores the graphical interface component to the value of the Parameter it is derived
	 * from.
	 */
	void setFromParameter();
	
	/**
	 * Notification hook to be informed that validation by the {@link Parameter} failed. The
	 * usual response is to call {@link #setFromParameter()} to restore the graphical 
	 * component to a valid state.
	 */
	void validateFailed();
	
	/**
	 * Returns the current value of the graphical interface component.
	 * @return
	 */
	T getEditorValue();
	
	
	Parameter<T> getParameter();
	
	/**
	 * Returns the graphical interface component for this editor.
	 * @return
	 */
	Object getComponent();
	
	
	/**
	 * Returns a hook which can be used to listen for changes to the editor's value
	 * @return
	 */
	EventfulType<T> getValueHook();
}
