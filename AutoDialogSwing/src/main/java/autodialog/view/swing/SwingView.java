package autodialog.view.swing;

import javax.swing.JComponent;

import autodialog.view.View;

public interface SwingView extends View {

	/**
	 * Returns the graphical interface component for this editor.
	 * @return
	 */
	JComponent getComponent();
	
}
