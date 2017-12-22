package bolt.plugin.core;

import java.net.URL;

public interface BoltPluginController<T extends BoltPlugin> {

	Class<? extends T> getImplementationClass();

	Class<T> getPluginClass();

	T create();

	boolean isEnabled();

	/**
	 * A short, descriptive name for this plugin. If the plugin cannot be loaded, returns null.
	 */
	String getName();

	/**
	 * A longer description of what this plugin is and what it does. If the plugin cannot be loaded, returns null.
	 * @return
	 */
	String getDescription();

	/**
	 * A version string for this plugin. If the plugin cannot be loaded, returns null.
	 */
	String getVersion();

	URL getSource();

}