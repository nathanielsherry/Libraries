package bolt.plugin;

import java.util.List;


public abstract class BoltPlugin {

	
	public BoltPlugin() {

	}
	
	
	protected static <S extends BoltPlugin> S createNewInstance(S f)
	{
		return createNewInstanceFromClass((Class<S>)f.getClass());
	}


	protected static <S extends BoltPlugin> S createNewInstanceFromClass(Class<S> f)
	{
		try
		{
			return f.newInstance();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public abstract void initialize();
	
	public abstract String getPluginName();
	public abstract String getPluginDescription();
	
	public abstract boolean pluginEnabled();

	
}
