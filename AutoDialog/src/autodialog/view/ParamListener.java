package autodialog.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import autodialog.controller.IAutoDialogController;
import autodialog.model.Parameter;
import autodialog.view.editors.IEditor;

public class ParamListener implements ActionListener, ChangeListener
{

	private Parameter				param;
	private AutoPanel				view;
	private IAutoDialogController	controller;


	public ParamListener(Parameter param, IAutoDialogController controller, AutoPanel view)
	{
		this.param = param;
		this.controller = controller;
		this.view = view;
	}


	public void actionPerformed(ActionEvent e)
	{

		update((IEditor)e.getSource());
	}


	public void stateChanged(ChangeEvent e)
	{

		update((IEditor)e.getSource());
	}


	public void update(IEditor editor)
	{
		
		Object oldValue = param.getValue();
		param.setValue(editor.getEditorValue());
		
		// if this input validates, signal the change, otherwise, reset
		// the value
		if (controller.validateParameters()) {
			
			controller.parametersUpdated();
			view.updateWidgetsEnabled();
			
		} else {
			// reset the parameter
			param.setValue(oldValue);
			
			//notify the editor that validation failed
			editor.validateFailed();
			
		}


	}

}