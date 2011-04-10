package plural.workers.executor;

import java.util.concurrent.ThreadFactory;


class PluralThreadFactory implements ThreadFactory
{

	public Thread newThread(Runnable r)
	{

		Thread t = new Thread(r);
		t.setPriority(Thread.MIN_PRIORITY);
		return new Thread(r);

	}

}
