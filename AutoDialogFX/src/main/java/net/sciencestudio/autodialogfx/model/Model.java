package net.sciencestudio.autodialogfx.model;

import net.sciencestudio.autodialogfx.Enabled;
import net.sciencestudio.autodialogfx.Titled;
import net.sciencestudio.autodialogfx.Validated;
import net.sciencestudio.autodialogfx.view.View;
import net.sciencestudio.chanje.ChangeSource;

public interface Model<T> extends ChangeSource, Titled, Enabled, Validated<T> {

	Class<? extends View> getView();
	
}
