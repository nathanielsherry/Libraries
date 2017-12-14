package autodialog.view.swing.layouts;

import java.util.List;

import javax.swing.border.Border;

import autodialog.view.editors.IEditor;
import autodialog.view.swing.AutoPanel;

public interface IADLayout {

	void setAutoPanel(AutoPanel root);
	void setAutoPanel(AutoPanel root, int level);

	void addEditors(List<IEditor<?>> params);
	
	Border topLevelBorder();
	

}