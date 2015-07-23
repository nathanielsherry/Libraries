package net.sciencestudio.autodialogfx.view.layouts;

import net.sciencestudio.autodialogfx.model.group.Group;
import net.sciencestudio.autodialogfx.view.View;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;



public class TitledLayout extends VerticalLayout {

	private TitledPane titlenode;
	
	protected TitledLayout() {
		super();
		titlenode = new TitledPane();
		titlenode.setCollapsible(false);
	}
	
	@Override
	public void layout() {
		titlenode.setText(group.getTitle());
		titlenode.setContent(node);
		super.layout();
	}
	
	@Override
	public Node getNode() {
		return titlenode;
	}
	
}
