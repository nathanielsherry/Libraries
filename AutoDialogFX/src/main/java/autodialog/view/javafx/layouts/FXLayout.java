package autodialog.view.javafx.layouts;

import autodialog.model.Group;
import autodialog.view.javafx.FXView;
import autodialog.view.layouts.Layout;

public interface FXLayout extends Layout, FXView {

	void initialize(Group group);
	void layout();
	
}
