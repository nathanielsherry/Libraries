package net.sciencestudio.autodialogfx.old.view.decor;

import net.sciencestudio.autodialogfx.old.model.Model;
import net.sciencestudio.autodialogfx.old.model.dummy.Dummy;
import net.sciencestudio.autodialogfx.old.model.group.Group;
import net.sciencestudio.autodialogfx.old.model.group.IGroup;
import net.sciencestudio.autodialogfx.old.view.AbstractView;

public abstract class AbstractDecor extends AbstractView implements Decor {

	Dummy dummy;
	
	public AbstractDecor() {}
	
	@Override
	public Decor initialize(Dummy dummy) {
		this.dummy = dummy;
		return this;
	}
	
	@Override
	public Model<?> getModel() {
		return dummy;
	}

	@Override
	public String getTitle() {
		return dummy.getTitle();
	}

	
	@SuppressWarnings("rawtypes")
	public static Group wrap() {
		Class cls = new Object() { }.getClass().getEnclosingClass();
		Dummy model = new Dummy(cls);
		return new IGroup(cls, model.getTitle()).add(model);
	}
}
