package swidget.widgets.tabbedinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import swidget.widgets.ClearPanel;


public abstract class TabbedInterface<T extends Component> extends JTabbedPane
{

	private String defaultTitle;
	private boolean working = false;
	private int minTabWidth = 0;
	
	public TabbedInterface(String defaultTitle) {
		this(defaultTitle, 0);
	}
	
	public TabbedInterface(String defaultTitle, int minTabWidth)
	{
		this.defaultTitle = defaultTitle;
		this.minTabWidth = minTabWidth;
		//tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		//this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		
	}
	
	public void init() {
		makeNewTabButton();
		newTab();
		
		
		this.addChangeListener(e -> {
			int i = this.getSelectedIndex();
			if (i < 0) return;
			
			TabbedInterfaceTitle titleComponent = (TabbedInterfaceTitle) this.getTabComponentAt(i);
			if (titleComponent == null) return;
			
			titleChanged(titleComponent.getTitle());
		});
		

		this.addChangeListener(e -> {
			if (!working) {
				working = true;
				//is the new-tab tab the only tab?
				if (this.getTabCount() == 1)
				{
					newTab();
					//tabs.setSelectedIndex(0);
				}
				
				//does the new-tab tab the focused tab?
				if (this.getSelectedIndex() == this.getTabCount() -1)
				{
					newTab();
				}
				
				
				titleChanged(getActiveTabTitle().getTitle());
				working = false;
			}
			
		});
		
	}
	

	private void makeNewTabButton() {
		Icon newmapButton = StockIcon.WINDOW_TAB_NEW.toImageIcon(IconSize.BUTTON);
		this.addTab("", newmapButton, new ClearPanel());	
	}
	
	
	protected JTabbedPane getJTabbedPane()
	{
		return this;
	}
	
	public void addTab(T component)
	{
		int count = this.getTabCount() - 1;
		
		TabbedInterfaceTitle titleComponent = new TabbedInterfaceTitle(this, minTabWidth);
		this.insertTab("", null, component, "", count);
		this.setTabComponentAt(count, titleComponent);
		setTabTitle(component, defaultTitle);
	}
	
	public T newTab()
	{
		T component = createComponent();
		addTab(component);
		setActiveTab(component);
		return component;
	}
	
	@SuppressWarnings("unchecked")
	protected void closeTab(int i)
	{
		//If this is the last tab before the new-tab tab and it's focused, try to focus another tab first
		if (i == this.getTabCount() - 2 && this.getSelectedIndex() == i && i != 0) {
			setActiveTab(i-1);
		}
		destroyComponent((T)this.getComponentAt(i));
		this.remove(i);
		
		if (this.getTabCount() == 0) newTab();
		
	}
	
	public void closeTab(T component)
	{
		destroyComponent(component);
		this.remove(component);
	}
	
	@SuppressWarnings("unchecked")
	public T getActiveTab()
	{
		return (T) this.getSelectedComponent();
	}
	
	private TabbedInterfaceTitle getActiveTabTitle() {
		return (TabbedInterfaceTitle) this.getTabComponentAt(this.getSelectedIndex());
	}
		
	public void setActiveTab(T component)
	{
		this.setSelectedComponent(component);
		component.requestFocus();
		titleChanged(getActiveTabTitle().getTitle());
	}
	
	public void setActiveTab(int i)
	{
		this.setSelectedIndex(i);
		titleChanged(getActiveTabTitle().getTitle());
	}
	
	
	public void setTabTitle(T component, String title)
	{
		int i = this.indexOfComponent(component);
		if (i < 0) return;
		TabbedInterfaceTitle titleComponent = (TabbedInterfaceTitle) this.getTabComponentAt(i);
		titleComponent.setTitle(title);
		this.setToolTipTextAt(this.indexOfComponent(component), title);
		if (i == this.getSelectedIndex()) {
			titleChanged(title);
		}
	}
	
	protected abstract T createComponent();
	protected abstract void destroyComponent(T component);
	protected abstract void titleChanged(String title);	
	
}
