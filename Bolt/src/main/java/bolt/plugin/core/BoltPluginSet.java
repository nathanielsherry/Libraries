package bolt.plugin.core;

import java.util.List;

public interface BoltPluginSet<T extends BoltPluginCore> {

	
	public List<BoltPluginController<? extends T>> getAvailablePlugins();


	public List<? extends T> getNewInstancesForAllPlugins();
	
	
	public void addPlugin(BoltPluginController<? extends T> plugin);
	
}
