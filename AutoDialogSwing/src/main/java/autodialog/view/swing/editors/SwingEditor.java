package autodialog.view.swing.editors;

import javax.swing.JComponent;

import autodialog.model.Parameter;
import autodialog.view.editors.Editor;
import eventful.IEventful;

public interface SwingEditor<T> extends Editor<T>
{
	
	
	/**
	 * Returns the graphical interface component for this editor.
	 * @return
	 */
	JComponent getComponent();
	
}
