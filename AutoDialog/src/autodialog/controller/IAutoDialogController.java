package autodialog.controller;

import java.util.List;

import autodialog.model.Parameter;

public interface IAutoDialogController {

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
	public List<Parameter> getParameters();
	
	/** 
	 * User has selected 'OK'
	 */
	public void submit();
	
	/**
	 * User has selected 'Cancel'
	 */
	public void cancel();
	
}
