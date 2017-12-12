package autodialog.view.layouts;

import java.util.List;

import autodialog.model.Parameter;

public abstract class ADLayoutFactory {

	public abstract IADLayout getLayout(List<Parameter<?>> params, int level, String group);
	
}
