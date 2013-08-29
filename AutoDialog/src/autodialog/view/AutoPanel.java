package autodialog.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import autodialog.model.Parameter;
import autodialog.view.layouts.IADLayout;
import autodialog.view.layouts.SimpleADLayout;

public class AutoPanel extends JPanel {

	
	private List<Parameter<?>> params;
	
	public AutoPanel(List<Parameter<?>> params) {
		this(params, new SimpleADLayout(), 0);
	}
	
	public AutoPanel(List<Parameter<?>> params, IADLayout layout, int level) {
		layout.setAutoPanel(this, level);
		this.params = new ArrayList<>(params);
		layout.addParameters(params);
	}

	
	public boolean expandVertical() {
		for (Parameter<?> param : params) {
			if (param.getEditor().expandVertical()) return true;
		}
		return false;
	}

	public boolean expandHorizontal() {
		for (Parameter<?> param : params) {
			if (param.getEditor().expandHorizontal()) return true;
		}
		return false;
	}

	

	
}
