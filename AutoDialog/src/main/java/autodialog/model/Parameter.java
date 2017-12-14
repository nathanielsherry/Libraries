package autodialog.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import autodialog.model.style.Style;
import eventful.EventfulType;

/**
 * 
 * This class defines a parameter for a filter.
 * 
 * @author Nathaniel Sherry, 2009-2012
 */

public class Parameter<T> implements Serializable
{
	
	public String			name;
	private Style<T>		style;
	
	private boolean			enabled;

	private T				value;
	private List<String>	groups;
	
	private EventfulType<T>	valueHook = new EventfulType<>();
	private EventfulType<Boolean> enabledHook = new EventfulType<>();
		
	
	public Parameter(String name, Style<T> style, T value)
	{
		this(name, style, value, new ArrayList<String>());
	}
	
	public Parameter(String name, Style<T> style, T value, String group)
	{
		this(name, style, value, new ArrayList<>(Collections.singletonList(group)));
	}
	
	public Parameter(String name, Style<T> style, T value, String... group){
		this(name, style, value, Arrays.asList(group));
	}
	
	public Parameter(String name, Style<T> style, T value, List<String> groups)
	{
		this.style = style;
		this.name = name;
		this.value = value;
		this.enabled = true;
		this.groups = groups;
	}

	
	public void setValue(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}

	
	public Style<T> getStyle() {
		return style;
	}


	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		getEnabledHook().updateListeners(enabled);
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

	public EventfulType<T> getValueHook() {
		return valueHook;
	}

	public EventfulType<Boolean> getEnabledHook() {
		return enabledHook;
	}
	

	
	
}
