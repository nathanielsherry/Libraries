package autodialog.model;


import java.io.Serializable;

/**
 * 
 * This class defines a parameter for a filter.
 * 
 * @author Nathaniel Sherry, 2009-2012
 */

public class Parameter implements Serializable
{

	public static enum ValueType
	{
		INTEGER, REAL, BOOLEAN, SET_ELEMENT, SEPARATOR, CODE, OTHER
	}
	
	private ValueType	type;
	private String		customType;
	
	public String		name;
	private Object		value;
	
	public boolean		enabled;

	public Object[]		possibleValues;
	
	//private Map<String, String>		properties;

	
	public Parameter(String name, ValueType type, Object value)
	{
		this.type = type;
		this.name = name;
		this.value = value;
		this.possibleValues = null;
		this.enabled = true;
		
		//properties = new HashMap<String, String>();
	}
	
	public Parameter(String name, String customType, Object value)
	{
		this(name, ValueType.OTHER, value);
		this.customType = customType;
	}
	

	public Parameter(String name, ValueType type, Object value, Object[] possibleValues)
	{
		this(name, type, value);
		this.possibleValues = possibleValues;
	}
	
	public void setValue(Object value)
	{
		this.value = value;
	}
	
	public Object getValue()
	{
		return value;
	}
	
	
	public int intValue()
	{
		return (Integer)value;

	}

	public float realValue()
	{
		if (value instanceof Double) return ((Double)value).floatValue();
		return (Float)value;

	}
	
	public boolean boolValue()
	{
		return (Boolean)value;
	}

	
	public String codeValue()
	{
		return (String)value;	
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Enum<T>> T enumValue()
	{
		return (T)value;
	}

	public ValueType getType() {
		return type;
	}

	public String getCustomType() {
		return customType;
	}
	
	/*
	public void setProperty(String propertyName, String propertyValue)
	{
		properties.put(propertyName, propertyValue);
	}
	
	public String getProperty(String propertyName)
	{
		return properties.get(propertyName);
	}
	*/

	
	
}
