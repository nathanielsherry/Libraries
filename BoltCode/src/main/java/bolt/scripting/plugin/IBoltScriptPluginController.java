package bolt.scripting.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import bolt.plugin.core.BoltPluginController;


public class IBoltScriptPluginController<T extends BoltScriptPlugin> implements BoltPluginController<T> {

	private File scriptFile;
	private Class<? extends T> implClass;
	private Class<T> pluginClass;
	private T instance;
	
	public IBoltScriptPluginController(File file, Class<? extends T> implClass, Class<T> pluginClass) {
		this.scriptFile = file;
		this.implClass = implClass;
		this.pluginClass = pluginClass;
		instance = create();
	}

	@Override
	public Class<? extends T> getImplementationClass() {
		return implClass;
	}

	@Override
	public Class<T> getPluginClass() {
		return pluginClass;
	}

	@Override
	public T create()
	{
		System.out.println("BoltScriptPlugin " + implClass.getName());
		
		try
		{
			T inst = implClass.newInstance();
			inst.setScriptFile(scriptFile);
			return inst;
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
			System.out.println(implClass);
			return null;
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
			System.out.println(implClass);
			return null;
		}
	}
	

	@Override
	public boolean isEnabled() {
		return (instance != null && instance.pluginEnabled());
	}
	
	/**
	 * A short, descriptive name for this plugin. If the plugin cannot be loaded, returns null.
	 */
	@Override
	public String getName() {
		if (instance == null) return null;
		return instance.pluginName();
	}

	/**
	 * A longer description of what this plugin is and what it does. If the plugin cannot be loaded, returns null.
	 * @return
	 */
	@Override
	public String getDescription() {
		if (instance == null) return null;
		return instance.pluginDescription();
	}
	
	/**
	 * A version string for this plugin. If the plugin cannot be loaded, returns null.
	 */
	@Override
	public String getVersion() {
		if (instance == null) return null;
		return instance.pluginVersion();
	}

	@Override
	public URL getSource() {
		try {
			return scriptFile.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
