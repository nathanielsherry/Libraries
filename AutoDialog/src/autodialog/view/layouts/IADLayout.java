package autodialog.view.layouts;

import java.util.List;

import javax.swing.border.Border;

import autodialog.model.Parameter;
import autodialog.view.AutoPanel;

public interface IADLayout {

	void setAutoPanel(AutoPanel root, int level);
	
	void addParameters(List<Parameter<?>> params);
	
	Border topLevelBorder();
	

}