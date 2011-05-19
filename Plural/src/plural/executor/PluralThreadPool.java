package plural.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



class PluralThreadPool
{

	private static ExecutorService executorService;

	private synchronized static ExecutorService getExecutorService()
	{
		if (executorService == null) executorService = Executors.newCachedThreadPool(new PluralThreadFactory());
		ThreadPoolUsers.incrementUsers();
		return executorService;
	}
	
	private synchronized static void finishedUsingExecutorService()
	{
		//if the thread-pool has no users, and has not been set to persistent, destroy it.
		if (ThreadPoolUsers.decrementUsers() == 0 && ! ThreadPoolUsers.isPersistent()) destroyThreadPool();
	}
	
	
	public static void execute(Runnable r, int numThreads){
	
		ExecutorService exec = getExecutorService();
		
	
		List<Future<?>> futures = new ArrayList<Future<?>>();
			
		for (int i = 0; i < numThreads; i++) futures.add(exec.submit(r));
		
		for (Future<?> f : futures){
			try {
				f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		finishedUsingExecutorService();
		
	}
	
	public static void destroyThreadPool()
	{
		if (executorService == null) return;
		executorService.shutdown();
		executorService = null;
	}
	
}
