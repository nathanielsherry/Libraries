package plural.interlacer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;



/**
 * For when OS-level threading won't work. This class allows the submission of jobs 
 * associated with various projects. Each project can have a priority which determines
 * the relative rate at which its jobs are processed. This class assumes that all jobs 
 * being submitted will be of roughly equal running time, and there is no running-time 
 * tracking or adjusted time quantum associated with the priority of a project.
 * <br/><br/>
 * As the name implies, this class implements cooperative multithreading using green 
 * threads, and so cannot preempt a task which takes too long, or even deadlocks. In 
 * many cases, using OS-level multithreading will be preferable, as the OS scheduler 
 * will be both more sophisticated, and more knowledgeable about blocked processes. 
 * @author Nathaniel Sherry, 2011
 *
 */

public class CooperativeProjectInterlacer<T>
{

	public boolean debugOutput = false;
	
	private static int defaultDefaultPriority = 3;
	private static int defaultIterationSize = 250;
	private static int defaultNumThreads = Runtime.getRuntime().availableProcessors();
	
	//Maps project name => work for project
	private Map<String, Project<T>> projects;
	
	private int iterationSize = 250;
	private int defaultPriority = 3;
	private int threadCount = Runtime.getRuntime().availableProcessors();
	private String DPName = "DP";

	public static int availableCores()
	{
		return Runtime.getRuntime().availableProcessors();
	}
	
	
	public CooperativeProjectInterlacer() {
		this("DP");
	}
	
	public CooperativeProjectInterlacer(String name) {
		this(defaultNumThreads, name);
	}
	
	
	
	
	
	public CooperativeProjectInterlacer(float percentageOfCores) {
		this(percentageOfCores, "DP");
	}
	
	public CooperativeProjectInterlacer(float percentageOfCores, String name) {
		this(percentageOfCores, defaultIterationSize, name);
	}
	
	public CooperativeProjectInterlacer(float percentageOfCores, int iterationJobCount, String name) {
		this(percentageOfCores, iterationJobCount, defaultDefaultPriority, name);
	}
	
	public CooperativeProjectInterlacer(float percentageOfCores, int iterationJobCount, int defaultPriority, String name) {
		this(
				Math.max(1, Math.round(  Runtime.getRuntime().availableProcessors() * percentageOfCores  )), 
				iterationJobCount, 
				defaultPriority, 
				name
			);
	}
	
	

	
	public CooperativeProjectInterlacer(int numThreads) {
		this(numThreads, "DP");
	}
	
	public CooperativeProjectInterlacer(int numThreads, String name) {
		this(numThreads, defaultIterationSize, name);
	}
	
	public CooperativeProjectInterlacer(int numThreads, int iterationJobCount, String name) {
		this(numThreads, iterationJobCount, defaultDefaultPriority, name);
	}
	
	public CooperativeProjectInterlacer(int numThreads, int iterationJobCount, int defaultPriority, String name)
	{
		projects = new LinkedHashMap<String, Project<T>>();
		
		DPName = name;
		iterationSize = iterationJobCount;
		this.defaultPriority = defaultPriority;
		threadCount = numThreads;
		
	}
	
	public void start()
	{
		for (int i = 0; i < threadCount; i++) {
			DataProcessorThread<T> dpt = new DataProcessorThread<T>(this, "Thread " + (i+1));
			dpt.start();
		}
	}
		
	
	public void addJob(String projectName, T job)
	{
		InterlacerProject<T> jobset = getProject(projectName);
		if (jobset == null) return;
		
		jobset.addJob(job);

		synchronized (this) {
			notifyAll();
		}
		
	}
	
	
	public void addJobs(String projectName, List<T> jobs)
	{
		if (jobs.size() == 0) return;
		
		InterlacerProject<T> jobset = getProject(projectName);
		if (jobset == null) return;
		
		jobset.addJobs(jobs);

		synchronized (this) {
			notifyAll();	
		}
		
	}
	
	
	public synchronized void addProject(String projectName, InterlacerProject<T> job)
	{
		addProject(projectName, job, defaultPriority);
	}
	
	public synchronized void addProject(String projectName, InterlacerProject<T> job, int priority)
	{
		
		Project<T> data = new Project<T>();
		data.jobs = job;
		data.priority = defaultPriority;
		
		projects.put(projectName, data);
	}
	
	public synchronized void setProjectPriority(String projectName, int priority)
	{
		if (priority < 0) priority = 0;
		if (priority > 10) priority = 10;
		
		Project<T> data = projects.get(projectName);
		if (data == null) 
		{
			throw new ProjectNotFoundException("Could not find project " + projectName);
		}
		data.priority = priority;
	}
	
	public int getProjectPriority(String projectName)
	{
		Project<T> data = projects.get(projectName);
		if (data == null) return -1;
		return data.priority;
	}
	
	public synchronized boolean projectExists(String projectName)
	{
		return (projects.get(projectName) != null);
	}
	
	public synchronized void removeProject(String projectName)
	{
		projects.remove(projectName);
	}

	public synchronized InterlacerProject<T> getProject(String projectName)
	{
		return projects.get(projectName).jobs;
	}
		
	public void doIteration()
	{
		
		Project<T> project;
		List<Project<T>> projectList = new ArrayList<Project<T>>();
		List<T> jobList = new ArrayList<T>();
		
		int totalPriority = 0;
		int workingProjects = 0;
		int blockSize;
		
		try {
			
			projectList.clear();
			
			
			/* lock against this object to make sure there are no new projects created and no 
			 * modifications made to existing projects while we are deciding what jobs to run
			 */
			synchronized (this)
			{
				while (true) {
					
					for (String projectName : projects.keySet()){
						project = projects.get(projectName);
						
						if (project.jobs.hasJobs()) {
							projectList.add(project);
						}
					}
					
					if (projectList.size() > 0) break;
					
					if (debugOutput) System.out.println(DPName + " " + Thread.currentThread() + ": Waiting");
					wait();
					if (debugOutput) System.out.println(DPName + " " + Thread.currentThread() + ": Awoken");
				}
				
				
				
				
				/* calculate the size of a single block of work.
				 * if a project has a priority of p, then it will be
				 * allowed to execute p blocks of work. The total
				 * number of blocks of work should be <= iterationSize.
				 * We don't allow the block size to dip below 5 in order to 
				 * make sure that we don't waste too much time on disk seeking
				 * or cache misses or whatnot caused by jumping from project to 
				 * project too quickly
				 */
				totalPriority = 0;
				workingProjects = 0;
				for (Project<T> currentProject : projectList)
				{
					totalPriority += currentProject.priority;
					if (currentProject.priority > 0) workingProjects++;
				}
							
				
				if (workingProjects > 0) {
					//there are foreground projects
					blockSize = Math.max(5, iterationSize / totalPriority);
				} else if(projectList.size() > 0) {
					//there are no foreground projects, but there are background projects
					//so we work on the background projects instead
					blockSize = Math.max(1, iterationSize / projectList.size());
					workingProjects = projectList.size();
				} else {
					blockSize = 5;
				}
				
			}//synchronize
				
			//run jobs on the projects we have in our list. We time them for logging purposes.
			long totalTime = 0;
			long t1, t2;
			int jobCount, jobsExecuted = 0;
			for (Project<T> currentProject : projectList)
			{
				
				if (totalPriority > 0) {
					jobCount = blockSize * currentProject.priority;
				} else {
					jobCount = blockSize;
				}
				
				
				t1 = System.currentTimeMillis();
				jobList = currentProject.jobs.getJobs(jobCount);
				if (!currentProject.jobs.runJobs(jobList)) break;
				t2 = System.currentTimeMillis();
				totalTime += (t2 - t1);
				jobsExecuted += jobList.size();
			}
			
			if (projectList.size() > 0) {
				if (debugOutput) System.out.println(DPName + " " + Thread.currentThread() + ": Processed " + jobsExecuted + " Jobs from " + workingProjects + " projects in " + totalTime/1000f + "s");
			}	
			
		} catch (Exception e) {
			
			e.printStackTrace();
							
		}
		
	}
	
	
}

class Project<T>
{
	
	public int priority;
	public InterlacerProject<T> jobs;
	
}

class DataProcessorThread<T> extends Thread
{
	
	CooperativeProjectInterlacer<T> dp;
	String title;
	
	public DataProcessorThread(CooperativeProjectInterlacer<T> dp, String title) {
		this.dp = dp;
		this.title = title;
		setDaemon(true);
	}
	
	public void run()
	{
		while (true) {
			dp.doIteration();
		}
	}
	
	public String toString()
	{
		return title;
	}
	
}

class ProjectNotFoundException extends RuntimeException
{
	public ProjectNotFoundException() {
		super();
	}
	
	public ProjectNotFoundException(String message)	{
		super(message);
	}
}