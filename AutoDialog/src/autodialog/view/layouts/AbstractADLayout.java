package autodialog.view.layouts;

import autodialog.view.AutoPanel;

public abstract class AbstractADLayout implements IADLayout{

	@Override
	public void setAutoPanel(AutoPanel root) {
		setAutoPanel(root, 0);
	}
	
}
