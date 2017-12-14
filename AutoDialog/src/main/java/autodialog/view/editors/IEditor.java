package autodialog.view.editors;

import autodialog.model.Parameter;
import eventful.IEventful;

public interface IEditor<T> extends IEventful
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
	
}
