package swidget.widgets.tabbedinterface;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public abstract class TabbedInterface<T extends JPanel> extends JPanel
{

	private JTabbedPane tabs;
	private String defaultTitle;
	
	public TabbedInterface(String defaultTitle)
	{
		
		this.defaultTitle = defaultTitle;
		tabs = new JTabbedPane();
		//tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		setLayout(new BorderLayout());
		add(tabs, BorderLayout.CENTER);
		
		tabs.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				int i = tabs.getSelectedIndex();
				if (i < 0) return;
				
				ButtonTabComponent titleComponent = (ButtonTabComponent) tabs.getTabComponentAt(i);
				if (titleComponent == null) return;
				
				tabsChanged(titleComponent.getTitle());
			}
		});
		
	}
	
	protected JTabbedPane getJTabbedPane()
	{
		return tabs;
	}
	
	public void addTab(T component)
	{
		int count = tabs.getTabCount();
		ButtonTabComponent titleComponent = new ButtonTabComponent(this);
		tabs.addTab("", component);
		tabs.setTabComponentAt(count, titleComponent);
		setTabTitle(component, defaultTitle);
	}
	
	public T newTab()
	{
		T component = createComponent();
		addTab(component);
		return component;
	}
	
	@SuppressWarnings("unchecked")
	protected void closeTab(int i)
	{
		destroyComponent((T)tabs.getComponentAt(i));
		tabs.remove(i);
		
		if (tabs.getTabCount() == 0) newTab();
		
	}
	
	public void closeTab(T component)
	{
		destroyComponent(component);
		tabs.remove(component);
	}
	
	@SuppressWarnings("unchecked")
	public T getActiveTab()
	{
		return (T) tabs.getSelectedComponent();
	}
	
	public void setActiveTab(T component)
	{
		tabs.setSelectedComponent(component);
	}
	
	public void setActiveTab(int i)
	{
		tabs.setSelectedIndex(i);
	}
	
	
	public void setTabTitle(T component, String title)
	{
		int i = tabs.indexOfComponent(component);
		if (i < 0) return;
		ButtonTabComponent titleComponent = (ButtonTabComponent) tabs.getTabComponentAt(i);
		titleComponent.setTitle(title);
		tabsChanged(title);
	}
	
	protected abstract T createComponent();
	protected abstract void destroyComponent(T component);
	protected abstract void tabsChanged(String title);	
	
}
