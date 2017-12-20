package autodialog.view.javafx.layouts;

import autodialog.model.Group;
import autodialog.model.Parameter;
import autodialog.model.Value;
import autodialog.view.editors.Editor.LabelStyle;
import autodialog.view.javafx.FXView;
import autodialog.view.javafx.editors.FXEditorFactory;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class SimpleFXLayout extends AbstractFXLayout {

	GridPane node;
	
	public SimpleFXLayout() {
		node = new GridPane();
		node.setHgap(6);
		node.setVgap(6);
	}
	
	
	@Override
	public void layout() {
		
		node.getChildren().clear();
		
		int row = 0;
		for (Value<?> value : group.getValue()) {
			
		
			FXView view = fromValue(value);
			if (view == null) { continue; }
			Node child = component(view);
			
			GridPane.setFillWidth(child, view.expandHorizontal());
			if (view.getLabelStyle() == LabelStyle.LABEL_HIDDEN) {
				GridPane.setRowIndex(child, row);
				GridPane.setColumnIndex(child, 0);
				GridPane.setColumnSpan(child, 2);
				
				node.getChildren().add(child);
				row++;
			} else if (view.getLabelStyle() == LabelStyle.LABEL_ON_SIDE) {
				Label label = new Label(view.getTitle());
				
				GridPane.setRowIndex(label, row);
				GridPane.setColumnIndex(label, 0);
				GridPane.setHgrow(label, Priority.ALWAYS);
				GridPane.setRowIndex(child, row);
				GridPane.setColumnIndex(child, 1);
				
				node.getChildren().addAll(label, child);
				row++;
			} else if (view.getLabelStyle() == LabelStyle.LABEL_ON_TOP) {
				Label label = new Label(view.getTitle());
				
				
				GridPane.setRowIndex(label, row);
				GridPane.setColumnIndex(label, 0);
				GridPane.setHgrow(label, Priority.ALWAYS);
				GridPane.setColumnSpan(child, 2);
				node.getChildren().add(label);
				row++;
				
				GridPane.setRowIndex(child, row);
				GridPane.setColumnIndex(child, 1);
				GridPane.setHgrow(child, Priority.ALWAYS);
				GridPane.setColumnSpan(child, 2);
				node.getChildren().add(child);
				row++;
			}
		}
		
		node.getColumnConstraints().clear();
		node.getColumnConstraints().add(new ColumnConstraints());
		node.getColumnConstraints().add(new ColumnConstraints());
		node.getColumnConstraints().get(0).setHgrow(Priority.ALWAYS);
		
	}

	public Node getComponent() {
		return node;
	}
	
	protected FXView fromValue(Value<?> value) {
		if (value instanceof Parameter) {
			return FXEditorFactory.forParameter((Parameter<?>)value);
		} else if (value instanceof Group) {
			return FXLayoutFactory.forGroup((Group)value);
		} else {
			return null;
		}
	}
	
	protected Node component(FXView view) {
		return view.getComponent();
	}


	
}
