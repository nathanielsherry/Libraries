package autodialog.view.swing.layouts;

import java.util.List;

import autodialog.model.Parameter;
import autodialog.view.editors.IEditor;

public abstract class ADLayoutFactory {

	public abstract IADLayout getLayout(List<IEditor<?>> editors, int level, String group);
	
}
