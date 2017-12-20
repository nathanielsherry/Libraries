package net.sciencestudio.autodialogfx.old.model;

import net.sciencestudio.autodialogfx.old.view.View;
import net.sciencestudio.chanje.ChangeSource;

public interface Model<T> extends ChangeSource, Titled, Enabled, Validated<T> {

	Class<? extends View> getView();
	
}
