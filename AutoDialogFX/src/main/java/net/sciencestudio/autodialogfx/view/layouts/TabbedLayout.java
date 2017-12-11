package net.sciencestudio.autodialogfx.view.layouts;

import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import net.sciencestudio.autodialogfx.view.View;

public class TabbedLayout extends AbstractLayout {

	TabPane node;
	
	public TabbedLayout() {
		node = new TabPane();
	}
	
	@Override
	public void layout() {
		List<Tab> nodes = getChildren().stream().map(this::node).collect(Collectors.toList());
		node.getTabs().setAll(nodes);
	}

	@Override
	public Node getNode() {
		return node;
	}
	
	protected Tab node(View view) {
		Tab tab = new Tab();
		tab.setText(view.getTitle());
		tab.setClosable(false);
		
		BorderPane content = new BorderPane();
		content.setCenter(view.getNode());
		content.setPadding(new Insets(6));
		tab.setContent(content);
		
		return tab;
	}

	
}
