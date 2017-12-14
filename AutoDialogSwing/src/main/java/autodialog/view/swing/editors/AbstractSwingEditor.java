package autodialog.view.swing.editors;

import autodialog.model.Parameter;
import eventful.EventfulType;

public abstract class AbstractSwingEditor<T> implements SwingEditor<T> {

	protected Parameter<Boolean> param;
	
	private EventfulType<T> valueHook = new EventfulType<>();

	public EventfulType<T> getValueHook() {
		return valueHook;
	}
	
	
}
