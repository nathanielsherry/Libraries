package net.sciencestudio.autodialogfx.view.layouts;

import java.util.List;
import java.util.stream.Collectors;

import net.sciencestudio.autodialogfx.view.View;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class VerticalLayout extends AbstractLayout {

	VBox node;
	
	public VerticalLayout() {
		node = new VBox(6);
	}
	
	@Override
	public void layout() {
		List<Node> nodes = getChildren().stream().map(this::node).collect(Collectors.toList());
		node.getChildren().setAll(nodes);
	}

	@Override
	public Node getNode() {
		return node;
	}
	
	protected Node node(View view) {
		return view.getNode();
	}

	
	
	
}
