package autodialog.view.swing.layouts;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import autodialog.model.Parameter;
import autodialog.view.editors.Editor;
import autodialog.view.swing.AutoPanel;
import swidget.widgets.Spacing;

public class TabbedADLayout extends AbstractGroupingADLayout {

	private AutoPanel root;
	private JTabbedPane tabs;
	private List<Editor<?>> general = new ArrayList<>();
	private ADLayoutFactory factory;
	private int level;
	
	public TabbedADLayout() {
		this(new ADLayoutFactory() {
			
			@Override
			public IADLayout getLayout(List<Editor<?>> editors, int level, String group) {
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

	private AutoPanel subpanel(List<Editor<?>> editors, String title)
	{
		AutoPanel panel = new AutoPanel(editors, factory.getLayout(editors, level+1, title), level+1);
		panel.setBorder(Spacing.bLarge());
		return panel;
	}
	
	@Override
	protected void finishPanel() {
		
		if (general.size() > 0) {
			tabs.insertTab("General", null, subpanel(general, null), null, 0);
		}
		
		tabs.setSelectedIndex(0);
		
		if (tabs.getTabCount() == 1) {
			root.add(tabs.getSelectedComponent(), BorderLayout.CENTER);
		} else {
			root.add(tabs, BorderLayout.CENTER);
		}
		
	}

	@Override
	protected void addEditorGroup(List<Editor<?>> editors, String title) {
		tabs.addTab(title, subpanel(editors, title));
	}

	@Override
	protected void addSingleEditor(Editor<?> editors) {
		general.add(editors);
	}

	@Override
	public Border topLevelBorder() {
		return Spacing.bNone();
	}
	
}
