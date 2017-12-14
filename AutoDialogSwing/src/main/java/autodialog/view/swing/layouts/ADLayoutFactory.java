package autodialog.view.swing.layouts;

import java.util.List;

import autodialog.model.Parameter;
import autodialog.view.editors.Editor;

public abstract class ADLayoutFactory {

	public abstract IADLayout getLayout(List<Editor<?>> editors, int level, String group);
	
}
