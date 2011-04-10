package plural.workers.executor;

/**
 * Static class to monitor the number of clients that the Thread Pool has. 
 * If the number of clients reaches 0, and the thread-pool is set to non-persistent, the thread
 * pool will be shutdown
 * @author nathaniel
 *
 */
public class ThreadPoolUsers {
	
	private static int users;
	private static boolean persistent = false;
	
	public synchronized static boolean isPersistent() {
		return persistent;
	}

	public synchronized static void setPersistent(boolean persistent) {
		ThreadPoolUsers.persistent = persistent;
		
		if (!persistent && users == 0) PluralThreadPool.destroyThreadPool();
		
	}

	public synchronized static int incrementUsers()
	{
		return ++users;
	}
	
	public synchronized static int decrementUsers()
	{
		return --users;
	}
	
	public synchronized static int getUsers()
	{
		return users;
	}
	
	
	
}
