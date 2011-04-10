package plural.interlacer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class contains the information on all of the jobs for a specific project 
 * @author Nathaniel Sherry
 *
 * @param <T>
 */

public abstract class InterlacerProject<T>
{

	private static final int stagingSize = 500;
	
	private List<T> staging;
	private Queue<T> jobs;
	
	
	public InterlacerProject()
	{
		staging = new LinkedList<T>();
		jobs = new LinkedList<T>();
	}
	
	protected abstract boolean doJob(T job);
	protected abstract boolean doJobs(List<T> jobs);
	
	
	public boolean runJobs(List<T> jobs)
	{
		try {
			return doJobs(jobs);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean runJob(T job)
	{

		if (job == null) return false;
		
		try {
			return doJob(job);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	
	//locks: staging
	public void addJob(T job)
	{
		//outer lock
		synchronized (staging)
		{
			staging.add(job);
			
			if (staging.size() > stagingSize) {
				//inner lock
				commitJobs();
			}
			
		}
	}
	
	//locks: staging
	public void addJobs(List<T> jobs)
	{
		synchronized (staging)
		{
			staging.addAll(jobs);
			
			if (staging.size() > stagingSize) {
				//inner lock
				commitJobs();
			}
			
		}
	}
	
	//locks: none
	public void addJobs(T[] jobs)
	{
		addJobs(Arrays.asList(jobs));
	}
	
	//locks: jobs
	public T getJob()
	{
		T job;
		
		synchronized (jobs)
		{
			job = jobs.poll();
		}
		
		if (job == null) {
			commitJobs();

			synchronized (jobs)
			{
				job = jobs.poll();
			}
			
		}

		
		return job;
	}
	
	//locks: jobs
	public List<T> getJobs(int count)
	{
		List<T> joblist = new LinkedList<T>();
		T job;
		
		synchronized (jobs)
		{
			for (int i = 0; i < count; i++) {
		
				job = jobs.poll();
				if (job == null) {
					commitJobs();
					job = jobs.poll();
					if (job == null) break;
				}
				
				joblist.add(job);			
				
			}


		}
		
		return joblist;
	}
	
	//locks: staging + jobs
	private void commitJobs()
	{
		synchronized (staging)
		{	
			synchronized (jobs)
			{
				for (T job : staging){
					jobs.offer(job);
				}
				
				staging.clear();
			}
		}
	}
	
	//locks: jobs, staging
	public boolean hasJobs()
	{
		synchronized (jobs)
		{
			if (jobs.size() > 0) return true;
		}
		
		synchronized (staging)
		{
			if (staging.size() > 0) return true;
		}
		
		return false;
		
	}
	
	
	
}
