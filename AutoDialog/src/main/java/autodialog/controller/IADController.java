package autodialog.controller;

import java.util.List;

import autodialog.model.Parameter;
import autodialog.view.editors.Editor;

/**
 * Controller for a set of {@link Parameter}s added to an {@link AutoPanel} or {@link AutoDialog}. This
 * controller is responsible for adding listeners to Parameters. These Parameters should only ever belong
 * to one controller at a time. Certain methods only apply when a controller is intended for use in an 
 * AutoDialog (eg {@link #submit()})
 * @author Nathaniel Sherry, 2013
 *
 */

public interface IADController {

	/**
	 * Allows validation of the Editors.
	 * @return true if all Editor values are satisfactory, false otherwise
	 */
	boolean validate();
	
	/**
	 * Allows real-time monitoring of the Editor values. This is useful if you don't want to wait
	 * until the dialog is dismissed before applying values.
	 */
	<T> void editorUpdated(Editor<T> editor);
	
	
	/** 
	 * Returns a list of {@link Editor}s to be used in this controller.
	 **/
	List<Editor<?>> getEditors();
	

	
}
