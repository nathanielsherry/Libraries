package autodialog.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import swidget.widgets.Spacing;
import autodialog.model.Parameter;
import autodialog.view.layouts.IADLayout;
import autodialog.view.layouts.SimpleADLayout;

public class AutoPanel extends JPanel {

	
	private List<Parameter<?>> params;
	
	public AutoPanel(List<Parameter<?>> params) {
		this(params, new SimpleADLayout(), false);
	}
	
	public AutoPanel(List<Parameter<?>> params, IADLayout layout, boolean topLevel) {
		layout.setAutoPanel(this, topLevel);
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
