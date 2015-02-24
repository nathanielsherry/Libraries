package net.sciencestudio.autodialogfx.model.dummy;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import net.sciencestudio.autodialogfx.model.Model;
import net.sciencestudio.autodialogfx.view.View;

public class Dummy implements Model<Void>{

	
	Class<? extends View> view;
	
	public Dummy(Class<? extends View> view) {
		this.view = view;
	}
	
	@Override
	public Class<? extends View> getView() {
		return view;
	}

	@Override
	public void listen(Consumer<Object> l) {}

	@Override
	public void unlisten(Consumer<Object> l) {}

	@Override
	public String getTitle() {
		return "";
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void setEnabled(boolean enabled) {}

	@Override
	public void addValidator(Predicate<Void> validator) {}

	@Override
	public void removeValidator(Predicate<Void> validator) {}

	@Override
	public List<Predicate<Void>> getValidators() {
		return Collections.emptyList();
	}

	@Override
	public void clearValidators() {}


}
