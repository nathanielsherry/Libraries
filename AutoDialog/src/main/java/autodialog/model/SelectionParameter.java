package autodialog.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import autodialog.model.style.Style;

public class SelectionParameter<T> extends Parameter<T> {

	private List<T> possibleValues = new ArrayList<>();
	
	public SelectionParameter(String name, Style<T> style, T value)
	{
		super(name, style, value);
	}
	
	public SelectionParameter(String name, Style<T> style, T value, String group)
	{
		super(name, style, value, group);
	}
	
	public SelectionParameter(String name, Style<T> style, T value, String... group){
		super(name, style, value, group);
	}
	
	public SelectionParameter(String name, Style<T> style, T value, List<String> groups)
	{
		super(name, style, value, groups);
	}

	public List<T> getPossibleValues() {
		return possibleValues;
	}

	public void setPossibleValues(List<T> possibleValues) {
		this.possibleValues = possibleValues;
	}
	
	
	
}
