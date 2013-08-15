package autodialog.controller;

import java.util.List;

import autodialog.model.Parameter;
import autodialog.view.AutoDialog.AutoDialogButtons;
import autodialog.view.AutoDialog;
import autodialog.view.AutoPanel;

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
	 * Allows validation of the Parameters in this dialog.
	 * @return true if all Parameters are satisfactory, false otherwise
	 */
	public boolean validateParameters();
	
	/**
	 * Allows real-time monitoring of the Parameter values. This is useful if you don't want to wait
	 * until the dialog is dismissed before applying values.
	 */
	public void parametersUpdated();
	
	
	/** 
	 * Returns a list of parameters to be used in this controller.
	 **/
	public List<Parameter<?>> getParameters();
	
	
	
	
	/** 
	 * User has selected 'OK' from a dialog
	 */
	public void submit();
	
	/**
	 * User has selected 'Cancel' from a dialog
	 */
	public void cancel();
	
	/**
	 * User has closed the dialog without selecting 'OK' or 'Cancel'
	 * This will happen when the dialog is contructed with {@link AutoDialogButtons#CANCEL}
	 */
	public void close();
	
}
