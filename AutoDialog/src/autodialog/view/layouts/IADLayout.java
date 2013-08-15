package autodialog.view.layouts;

import java.util.List;

import autodialog.model.Parameter;
import autodialog.view.AutoPanel;

public interface IADLayout {

	public abstract void setAutoPanel(AutoPanel root, boolean topLevel);
	
	public abstract void addParameters(List<Parameter<?>> params);
	

}