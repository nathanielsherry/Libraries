package bolt.scripting.plugin;

import bolt.plugin.core.BoltPluginSet;

public class IBoltScriptPluginLoader<T extends BoltScriptPlugin> {

	private BoltPluginSet<T> plugins;

	public void registerPlugin(BoltPluginSet<T> pluginset, IBoltScriptPluginController<T> plugin) {
		this.plugins = pluginset;
		plugins.addPlugin(plugin);
	}
	
}
