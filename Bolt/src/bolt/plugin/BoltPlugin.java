package bolt.plugin;

/**
 * This is the base class that any plugins using the Bolt plugin system must extend.
 * @author Nathaniel Sherry, 2010-2012
 *
 */

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
			System.out.println(f);
			return null;
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
			System.out.println(f);
			return null;
		}
	}
	

	/**
	 * Returns a name for this plugin
	 */
	public abstract String getPluginName();
	
	/**
	 * Returns a short description for this plugin
	 */
	public abstract String getPluginDescription();
	
	/**
	 * Returns true if this plugin is able to be used, false otherwise
	 * <br /><br />
	 * There may be cases where plugins have certain requirements which 
	 * must be met in order to function properly in a given situation. If
	 * a plugin returns false for this method, it will not be exposed 
	 * to the software using the plugins.
	 */
	public abstract boolean pluginEnabled();

	
}
