package net.sciencestudio.autodialogfx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.sciencestudio.autodialogfx.changes.EnabledChange;
import net.sciencestudio.autodialogfx.view.View;
import net.sciencestudio.chanje.IChangeSource;

public class AbstractModel<T> extends IChangeSource implements Model<T>{

	private String title;
	private boolean enabled = true;
	private List<Predicate<T>> validators = new ArrayList<>();
	private Class<? extends View> view;
	
	public AbstractModel(Class<? extends View> view, String title) {
		this.view = view;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		broadcast(new EnabledChange(this));
	}



	@Override
	public void addValidator(Predicate<T> validator) {
		validators.add(validator);
	}

	@Override
	public void removeValidator(Predicate<T> validator) {
		validators.remove(validator);
	}

	@Override
	public void clearValidators() {
		validators.clear();
	}
	
	@Override
	public List<Predicate<T>> getValidators() {
		return new ArrayList<Predicate<T>>(validators);
	}

	@Override
	public Class<? extends View> getView() {
		return view;
	}


}
