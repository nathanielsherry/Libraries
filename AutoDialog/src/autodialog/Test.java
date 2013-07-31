package autodialog;

import java.util.List;

import swidget.Swidget;
import fava.functionable.FList;
import autodialog.controller.IAutoDialogController;
import autodialog.model.Parameter;
import autodialog.model.Parameter.ValueType;
import autodialog.view.AutoDialog;

public class Test {

	public static void main(String[] args)
	{
		
		Swidget.initialize();
		
		final FList<Parameter> params = new FList<>();
		
		params.add(new Parameter("Enabled", ValueType.BOOLEAN, Boolean.FALSE));
		params.add(new Parameter("Height", ValueType.INTEGER, 0));
		params.add(new Parameter("Width", ValueType.INTEGER, 0));
		params.add(new Parameter("Horizontal Shift", ValueType.INTEGER, 0));
		params.add(new Parameter("Vertical Shift", ValueType.INTEGER, 0));
		
		AutoDialog d = new AutoDialog(new IAutoDialogController() {
			
			@Override
			public boolean validateParameters() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public void parametersUpdated() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public List<Parameter> getParameters() {
				// TODO Auto-generated method stub
				return params;
			}

			@Override
			public void submit() {
				// TODO Auto-generated method stub
				System.out.println("OK");
				for (Parameter param : params)
				{
					System.out.println(param.getValue());
				}
			}

			@Override
			public void cancel() {
				// TODO Auto-generated method stub
				System.out.println("Cancel");
			}
		});
		
		d.setModal(true);
		d.initialize();
	}
	
}
