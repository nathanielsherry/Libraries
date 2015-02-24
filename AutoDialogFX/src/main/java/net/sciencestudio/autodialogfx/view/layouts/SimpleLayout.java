package net.sciencestudio.autodialogfx.view.layouts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.sciencestudio.autodialogfx.model.Model;
import net.sciencestudio.autodialogfx.model.group.Group;
import net.sciencestudio.autodialogfx.view.View;
import net.sciencestudio.autodialogfx.view.ViewProperties.LabelStyle;
import net.sciencestudio.autodialogfx.view.editors.Editor;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class SimpleLayout extends AbstractLayout {

	GridPane node;
	
		
	public SimpleLayout() {
		node = new GridPane();
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHgrow(Priority.NEVER);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHgrow(Priority.ALWAYS);
		column2.setHalignment(HPos.RIGHT);
		
		node.getColumnConstraints().addAll(column1, column2);
		
		node.setVgap(6);
		node.setHgap(6);
	}


	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public void layout() {

		node.getChildren().clear();
	
		int row = 0;
		for (View child : getChildren()) {

			Label label = new Label(child.getTitle());

			Node control = child.getNode();
			
			LabelStyle style = child.getProperties().labelStyle; 
			if (child instanceof Group) { style = LabelStyle.LABEL_HIDDEN; }
			
			switch (style) {
			
				case LABEL_HIDDEN:
					GridPane.setRowIndex(control, row);
					GridPane.setColumnIndex(control, 1);
					GridPane.setHgrow(control, Priority.NEVER);
					
					node.getChildren().addAll(control);
					row++;
					break;
					
				case LABEL_ON_SIDE:
					
					GridPane.setRowIndex(label, row);
					GridPane.setColumnIndex(label, 0);
					GridPane.setHalignment(label, HPos.LEFT);
					GridPane.setRowIndex(control, row);
					GridPane.setColumnIndex(control, 1);
					GridPane.setHgrow(control, Priority.NEVER);
					GridPane.setHalignment(control, HPos.RIGHT);
					
					node.getChildren().addAll(label, control);
					row++;
					break;
					
				case LABEL_ON_TOP:
					
					GridPane.setRowIndex(label, row);
					GridPane.setColumnIndex(label, 0);
					GridPane.setColumnSpan(label, 2);
					GridPane.setHalignment(label, HPos.LEFT);
					GridPane.setRowIndex(control, row+1);
					GridPane.setColumnIndex(control, 0);
					GridPane.setColumnSpan(control, 2);
					GridPane.setHgrow(control, Priority.NEVER);
					GridPane.setHalignment(control, HPos.RIGHT);
					
					node.getChildren().addAll(label, control);
					row+=2;
					break;
					
				default:
					break;
			
			}
			

		}

	}



}
