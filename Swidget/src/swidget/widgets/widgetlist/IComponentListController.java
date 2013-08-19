package swidget.widgets.widgetlist;


public interface IComponentListController<T> {

	public abstract T generateComponent();
	public abstract String getComponentName();
	
	public abstract void add(T component);
	public abstract void remove(T component);
	public abstract void edit(T component);
	
	
	
}
