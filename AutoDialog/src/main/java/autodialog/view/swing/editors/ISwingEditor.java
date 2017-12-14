package autodialog.view.swing.editors;

import javax.swing.JComponent;

import autodialog.model.Parameter;
import autodialog.view.editors.IEditor;
import eventful.IEventful;

public interface ISwingEditor<T> extends IEditor<T>
{
	
	
	/**
	 * Returns the graphical interface component for this editor.
	 * @return
	 */
	JComponent getComponent();
	
}
