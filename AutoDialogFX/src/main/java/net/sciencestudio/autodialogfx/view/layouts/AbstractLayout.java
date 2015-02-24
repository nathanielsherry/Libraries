package net.sciencestudio.autodialogfx.view.layouts;

import java.util.ArrayList;
import java.util.List;

import net.sciencestudio.autodialogfx.model.Model;
import net.sciencestudio.autodialogfx.model.group.Group;
import net.sciencestudio.autodialogfx.model.group.IGroup;
import net.sciencestudio.autodialogfx.view.AbstractView;
import net.sciencestudio.autodialogfx.view.View;
import net.sciencestudio.autodialogfx.view.ViewProperties.LabelStyle;

public abstract class AbstractLayout extends AbstractView implements Layout {

	private List<View> children = new ArrayList<>();
	protected Group group;
	
	@Override
	public void initialize(Group group) {
		this.group = group;
		for(Model<?> model : group.getModels()) {
			addChild(View.forModel(model, model.getView()));
		}
		layout();
		getProperties().labelStyle = LabelStyle.LABEL_ON_TOP;
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


	@Override
	public Model<?> getModel() {
		return group;
	}
	

	public abstract void layout();

}
