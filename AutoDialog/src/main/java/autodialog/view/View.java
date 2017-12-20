package autodialog.view;

import autodialog.model.Value;
import autodialog.view.editors.Editor.LabelStyle;

public interface View {

	String getTitle();
	
	boolean expandVertical();
	boolean expandHorizontal();
	
	LabelStyle getLabelStyle();
	
	/**
	 * Returns the graphical interface component for this editor.
	 * @return
	 */
	Object getComponent();
	
	
	
	Value<?> getValue();
	
}
