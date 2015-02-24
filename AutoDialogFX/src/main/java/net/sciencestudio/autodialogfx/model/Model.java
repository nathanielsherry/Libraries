package net.sciencestudio.autodialogfx.model;

import net.sciencestudio.autodialogfx.view.View;
import net.sciencestudio.chanje.ChangeSource;

public interface Model<T> extends ChangeSource, Titled, Enabled, Validated<T> {

	Class<? extends View> getView();
	
}
