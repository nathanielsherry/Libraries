package autodialog.view.layouts;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import swidget.widgets.Spacing;
import autodialog.model.Parameter;
import autodialog.view.AutoPanel;

public class TabbedADLayout extends AbstractGroupingADLayout {

	private AutoPanel root;
	private JTabbedPane tabs;
	private List<Parameter<?>> general = new ArrayList<>();
	private ADLayoutFactory factory;
	private int level;
	
	public TabbedADLayout() {
		this(new ADLayoutFactory() {
			
			@Override
			public IADLayout getLayout(List<Parameter<?>> params) {
				return new SimpleADLayout();
			}
		});
	}
	
	public TabbedADLayout(ADLayoutFactory factory) {
		this.factory = factory;
	}

	
	@Override
	public void setAutoPanel(AutoPanel root, int level) {
		this.root = root;
		this.level = level;
	}

	@Override
	protected void startPanel() {
		
		//create tab panel
		tabs = new JTabbedPane();
		root.setLayout(new BorderLayout());
		
		
	}

	private AutoPanel subpanel(List<Parameter<?>> params)
	{
		AutoPanel panel = new AutoPanel(params, factory.getLayout(params), level+1);
		panel.setBorder(Spacing.bLarge());
		return panel;
	}
	
	@Override
	protected void finishPanel() {
		
		tabs.insertTab("General", null, subpanel(general), null, 0);
		tabs.setSelectedIndex(0);
		
		if (tabs.getTabCount() == 1) {
			root.add(tabs.getSelectedComponent(), BorderLayout.CENTER);
		} else {
			root.add(tabs, BorderLayout.CENTER);
		}
		
	}

	@Override
	protected void addParamGroup(List<Parameter<?>> params, String title) {
		tabs.addTab(title, subpanel(params));
	}

	@Override
	protected void addSingleParam(Parameter<?> param) {
		general.add(param);
	}

}
