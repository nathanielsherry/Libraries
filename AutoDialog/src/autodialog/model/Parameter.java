package autodialog.model;


import java.awt.Component;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fava.functionable.FList;
import autodialog.view.editors.DoubleEditor;
import autodialog.view.editors.IEditor;
import autodialog.view.editors.IntegerEditor;

/**
 * 
 * This class defines a parameter for a filter.
 * 
 * @author Nathaniel Sherry, 2009-2012
 */

public class Parameter<T> implements Serializable
{
	
	public String			name;
	private IEditor<T>		editor;
	
	private boolean			enabled;

	private T				value;
	private List<String>	groups;
	

	public Parameter(String name, IEditor<T> editor, T value)
	{
		this(name, editor, value, new ArrayList<String>());
	}
	
	public Parameter(String name, IEditor<T> editor, T value, String group)
	{
		this(name, editor, value, new FList<>(group));
	}
	
	public Parameter(String name, IEditor<T> editor, T value, String... group){
		this(name, editor, value, Arrays.asList(group));
	}
	
	public Parameter(String name, IEditor<T> editor, T value, List<String> groups)
	{
		this.editor = editor;
		this.name = name;
		this.value = value;
		this.enabled = true;
		this.groups = groups;
		
		editor.initialize(this);
	}

	
	public void setValue(T value)
	{
		this.value = value;
	}
	
	public T getValue()
	{
		return value;
	}

	
	public IEditor<T> getEditor() {
		return editor;
	}


	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		
		Component component = getEditor().getComponent();
		if (component == null) return;
		
		component.setEnabled(enabled);
		
	}
	
	public String toString()
	{
		String str =  "Parameter " + name;
		if (value != null) str += ": " + value.toString();
		return str;
	}
	
	public String getGroup(int level)
	{
		if (groups.size() <= level) return null;
		return groups.get(level);
	}
	
	public List<String> getGroups()
	{
		return new ArrayList<>(groups);
	}
	
}
