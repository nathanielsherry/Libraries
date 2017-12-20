package net.sciencestudio.autodialogfx.old.view.layouts;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import net.sciencestudio.autodialogfx.old.view.View;

public class VerticalLayout extends AbstractLayout {

	GridPane node;
	
	public VerticalLayout() {
		node = new GridPane();
		node.setHgap(6);
		node.setVgap(6);
	}
	
	@Override
	public void layout() {
		
		node.getChildren().clear();
		
		int row = 0;
		for (View child : getChildren()) {
			Node childnode = child.getNode();
			GridPane.setFillWidth(childnode, true);
			if (child instanceof Layout) {
				System.out.println("child");
				GridPane.setRowIndex(childnode, row);
				GridPane.setColumnIndex(childnode, 0);
				GridPane.setColumnSpan(childnode, 2);
				
				node.getChildren().add(childnode);
				row++;
			} else {
				Label label = new Label(child.getTitle());
				
				GridPane.setRowIndex(label, row);
				GridPane.setColumnIndex(label, 0);
				GridPane.setHgrow(label, Priority.ALWAYS);
				GridPane.setRowIndex(childnode, row);
				GridPane.setColumnIndex(childnode, 1);
				
				node.getChildren().addAll(label, childnode);
				row++;
			}
		}
		
		node.getColumnConstraints().clear();
		node.getColumnConstraints().add(new ColumnConstraints());
		node.getColumnConstraints().add(new ColumnConstraints());
		node.getColumnConstraints().get(0).setHgrow(Priority.ALWAYS);
		
	}

	@Override
	public Node getNode() {
		return node;
	}

	
}
