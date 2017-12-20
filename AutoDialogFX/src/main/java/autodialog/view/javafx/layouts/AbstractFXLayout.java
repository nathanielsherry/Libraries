package autodialog.view.javafx.layouts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.JPanel;

import autodialog.model.Group;
import autodialog.view.editors.Editor;
import autodialog.view.editors.Editor.LabelStyle;
import autodialog.view.javafx.editors.FXEditor;
import autodialog.view.layouts.Layout;
import javafx.scene.Node;


public abstract class AbstractFXLayout implements FXLayout {

	protected Group group;

	public void initialize(Group group) {
		this.group = group;
		layout();
	}
	

	@Override
	public String getTitle() {
		return group.getName();
	}
	
	@Override
	public Group getValue() {
		return group;
	}

	
	@Override
	public LabelStyle getLabelStyle() {
		return LabelStyle.LABEL_HIDDEN;
	}
	
	
	@Override
	public boolean expandVertical() {
		return false;
	}

	@Override
	public boolean expandHorizontal() {
		return true;
	}
	
	
//	@Override
//	public void addEditors(List<Editor<?>> editors) {
//		this.children = editors;
//		layout();
//	}
//	
//	protected List<Editor<?>> getChildren() {
//		return children;
//	}
//	
//	protected List<Node> getChildNodes() {
//		List<Node> nodes = getChildren().stream().map(child -> ((FXEditor<?>)child).getComponent()).collect(Collectors.toList());
//		return nodes;
//	}

	public abstract void layout();

}
