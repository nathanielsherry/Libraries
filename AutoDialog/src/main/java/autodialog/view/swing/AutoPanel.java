package autodialog.view.swing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import autodialog.model.Parameter;
import autodialog.view.editors.IEditor;
import autodialog.view.swing.editors.SwingEditorFactory;
import autodialog.view.swing.layouts.IADLayout;
import autodialog.view.swing.layouts.SimpleADLayout;

public class AutoPanel extends JPanel {

	
	private List<IEditor<?>> editors;
	private IADLayout layout;
	



	public AutoPanel(List<IEditor<?>> editors) {
		this(editors, new SimpleADLayout());
	}
	
	public AutoPanel(List<IEditor<?>> editors, IADLayout layout) {
		this(editors, layout, 0);
	}
	
	public AutoPanel(List<IEditor<?>> editors, IADLayout layout, int level) {
		layout.setAutoPanel(this, level);
		this.editors = new ArrayList<>(editors);
		this.layout = layout;
		layout.addEditors(editors);
	}

	public static AutoPanel fromParameters(List<Parameter<?>> params) {
		return new AutoPanel(SwingEditorFactory.forParameters(params));
	}
	
	public static AutoPanel fromParameters(List<Parameter<?>> params, IADLayout layout) {
		return new AutoPanel(SwingEditorFactory.forParameters(params), layout);
	}
	
	
	public boolean expandVertical() {
		for (IEditor<?> editor : editors) {
			if (editor.expandVertical()) return true;
		}
		return false;
	}

	public boolean expandHorizontal() {
		for (IEditor<?> editor : editors) {
			if (editor.expandHorizontal()) return true;
		}
		return false;
	}

	public IADLayout getADLayout() {
		return layout;
	}

	
}
