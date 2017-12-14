package autodialog.view.swing.layouts;

import autodialog.view.swing.AutoPanel;

public abstract class AbstractADLayout implements IADLayout{

	@Override
	public void setAutoPanel(AutoPanel root) {
		setAutoPanel(root, 0);
	}
	
}
