package net.sciencestudio.autodialogfx.old.model.group;

import java.util.ArrayList;
import java.util.List;

import net.sciencestudio.autodialogfx.old.model.AbstractModel;
import net.sciencestudio.autodialogfx.old.model.Model;
import net.sciencestudio.autodialogfx.old.view.layouts.Layout;

public class IGroup extends AbstractModel<List<Model<?>>> implements Group {

	private List<Model<?>> models = new ArrayList<>();
	
	public IGroup(Class<? extends Layout> view, String title) {
		super(view, title);
	}

	public Group add(Model<?> model) {
		models.add(model);
		model.addValidator(v -> validate(getModels()));
		return this;
	}

	@Override
	public List<Model<?>> getModels() {
		return new ArrayList<>(models);
	}

	@Override
	public Group addAll(Model<?>... models) {
		for (Model<?> model : models) {
			add(model);
		}
		return this;
	}

	@Override
	public Group addAll(List<Model<?>> models) {
		this.models.addAll(models);
		return this;
	}

	
}
