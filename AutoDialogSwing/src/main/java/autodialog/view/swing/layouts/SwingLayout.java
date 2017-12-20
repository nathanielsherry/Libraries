package autodialog.view.swing.layouts;


import autodialog.model.Group;
import autodialog.view.layouts.Layout;
import autodialog.view.swing.SwingView;

public interface SwingLayout extends Layout, SwingView {

	void initialize(Group group);
	void layout();
	
}
