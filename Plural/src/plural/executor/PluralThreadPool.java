package plural.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



class PluralThreadPool
{

	private static ExecutorService executorService = Executors.newCachedThreadPool(new PluralThreadFactory());

	
	
	public static void execute(Runnable r, int numThreads){
		
	
		List<Future<?>> futures = new ArrayList<Future<?>>();
			
		for (int i = 0; i < numThreads; i++) futures.add(executorService.submit(r));
		
		for (Future<?> f : futures){
			try {
				f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}
