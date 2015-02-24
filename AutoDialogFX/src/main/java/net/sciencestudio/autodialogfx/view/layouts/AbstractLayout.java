package net.sciencestudio.autodialogfx.view.layouts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.sciencestudio.autodialogfx.model.Model;
import net.sciencestudio.autodialogfx.model.group.Group;
import net.sciencestudio.autodialogfx.view.View;
import net.sciencestudio.autodialogfx.view.ViewProperties;
import net.sciencestudio.autodialogfx.view.Views;

public abstract class AbstractLayout implements Layout {

	private List<View> children = new ArrayList<>();
	private ViewProperties properties = new ViewProperties();
	protected Group group;
	
	@Override
	public void initialize(Group group) {
		this.group = group;
		for(Model<?> model : group.getModels()) {
			addChild(Views.forModel(model, model.getView()));
		}
		layout();
	}

	protected void addChild(View editor) {
		children.add(editor);
	}
	

	
	protected List<View> getChildren() {
		return children;
	}
	
	public String getTitle() {
		return group.getTitle();
	}
	
	public ViewProperties getProperties() {
		return properties;
	}
	

	@Override
	public Model<?> getModel() {
		return group;
	}
	

	public abstract void layout();

}
