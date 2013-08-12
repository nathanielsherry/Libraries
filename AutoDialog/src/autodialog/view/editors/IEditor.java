package autodialog.view.editors;

import javax.swing.JComponent;

import autodialog.model.Parameter;
import eventful.IEventful;

public interface IEditor<T> extends IEventful
{
	
	public enum LabelStyle {
		LABEL_ON_TOP,
		LABEL_ON_SIDE,
		LABEL_HIDDEN
	}
	

	public abstract void initialize(Parameter<T> param);

	public abstract boolean expandVertical();
	public abstract boolean expandHorizontal();
	public abstract LabelStyle getLabelStyle();
	
	/**
	 * Restores the graphical interface component to the value of the Parameter it is derived
	 * from.
	 */
	public abstract void setFromParameter();
	
	/**
	 * Notification hook to be informed that validation by the {@link Parameter} failed. The
	 * usual response is to call {@link #setFromParameter()} to restore the graphical 
	 * component to a valid state.
	 */
	public abstract void validateFailed();
	
	/**
	 * Returns the current value of the graphical interface component.
	 * @return
	 */
	public abstract T getEditorValue();
	
	/**
	 * Returns the graphical interface component for this editor.
	 * @return
	 */
	public abstract JComponent getComponent();
	
}
