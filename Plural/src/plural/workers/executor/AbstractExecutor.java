package plural.workers.executor;

public abstract class AbstractExecutor<T1> {

	/**
	 * This method will be called once by each ThreadWorker after being dispatched from
	 * {@link MapExecutor#execute(int threadCount)}. Work should be assigned to the {@link Task} from
	 * here.
	 */
	protected abstract void workForExecutor();

	/**
	 * Implementations of MapExecutor should call this method to begin processing with the desired number of
	 * threads. The appropriate number of threads will be acquired, and each will call into
	 * {@link MapExecutor#workForExecutor()}
	 * 
	 * @param numThreads
	 */
	protected void execute(int numThreads)
	{
		if (numThreads <= 0) numThreads = 1;
				
		Runnable r = new Runnable() {
		
			public void run()
			{
				workForExecutor();
			}
		};
				
		PluralThreadPool.execute(r, numThreads);
		
	}
	
	/**
	 * Calculates the number of threads that should be used by this {@link MapExecutor}
	 * 
	 * @param threadsPerCore
	 *            a multiplier that determines the number of threads per processor to use.
	 * @return the total number of threads which should be used.
	 */
	public int calcNumThreads(double threadsPerCore)
	{
		int threads = (int) Math.round(Runtime.getRuntime().availableProcessors() * threadsPerCore);
		if (threads <= 0) threads = 1;
		return threads;
	}


	/**
	 * Calculates the number of threads that should be used by this {@link MapExecutor}
	 * 
	 * @return the total number of threads which should be used.
	 */
	public int calcNumThreads()
	{
		return calcNumThreads(1.0);
	}
	
	
	/**
	 * The size of the data set that the provided {@link AbstractPlural} will be operating on.
	 * 
	 * @return number of required iterations.
	 */
	public abstract int getDataSize();
	
}
