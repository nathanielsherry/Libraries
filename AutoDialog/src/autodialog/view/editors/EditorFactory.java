package autodialog.view.editors;


import java.util.HashMap;
import java.util.Map;

import autodialog.controller.IAutoDialogController;
import autodialog.model.Parameter;
import autodialog.view.AutoPanel;


public class EditorFactory
{
	
	private static Map<String, ICustomEditor> customEditors = new HashMap<>();
	
	public static IEditor createEditor(Parameter param, IAutoDialogController controller, AutoPanel view)
	{
		IEditor editor = null;
		//generate the control which will display the value for this filter
		switch (param.getType())
		{
			case INTEGER:

				editor = new IntegerEditor(param, controller, view);
				break;

			case REAL:

				editor = new RealEditor(param, controller, view);
				break;
				
			case SET_ELEMENT:
			
				editor = new EnumEditor(param, controller, view);
				break;

			case BOOLEAN:
				
				editor = new BooleanEditor(param, controller, view);
				break;
				
			case SEPARATOR:
				
				editor = new SeparatorEditor();
				break;
				
			case OTHER:
				
				String customType = param.getCustomType();
				if (!customEditors.containsKey(customType)) return new DummyEditor(param, controller, view);
				
				ICustomEditor template = customEditors.get(customType);
				editor = template.construct(param, controller, view);
				
				break;
				
			default:
				break;
				
		}
		
		return editor;
		
	}

	public static void registerCustomEditor(String customType, ICustomEditor template)
	{
		customEditors.put(customType, template);
	}
	
}
