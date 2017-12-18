package bolt.plugin.core;

import java.util.List;

public interface BoltPluginSet<T extends BoltPluginCore> {

	
	public List<BoltPluginController<? extends T>> getAll();


	public List<T> newInstances();
	
	
	public void addPlugin(BoltPluginController<? extends T> plugin);
	
}
