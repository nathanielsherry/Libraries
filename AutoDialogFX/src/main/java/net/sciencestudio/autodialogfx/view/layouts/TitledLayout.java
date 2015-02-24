package net.sciencestudio.autodialogfx.view.layouts;

import net.sciencestudio.autodialogfx.view.View;
import javafx.scene.control.TitledPane;



public class TitledLayout extends VerticalLayout {

	protected TitledPane node(View view) {
		TitledPane pane = new TitledPane();
		pane.setText(view.getTitle());
		pane.setContent(view.getNode());
		pane.setCollapsible(false);
		return pane;
	}
	

}
