package plural.interlacer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fava.functionable.FList;



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
	private static int defaultNumBackgroundJobs = 100;
	
	//Maps project name => work for project
	private Map<String, Project<T>> projects;
	
	private int iterationSize = defaultIterationSize;
	private int defaultPriority = defaultDefaultPriority;
	private int threadCount = defaultNumThreads;
	private int numBackgroundJobs = defaultNumBackgroundJobs;
	
	
	private boolean runAtLeastOnce = false;


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
	
	public void setRunAtLeastOnce()
	{
		runAtLeastOnce = true;
	}
	
	public void setRunAtMostOnce()
	{
		runAtLeastOnce = false;
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
		addJobs(projectName, new FList<T>(job));
	}
	
	
	public void addJobs(String projectName, List<T> jobs)
	{
		if (jobs.size() == 0) return;
		
		InterlacerProject<T> jobset = getProject(projectName);
		if (jobset == null) return;
		
		addJobs(jobset, jobs);

		
	}
	
	private void addJobs(InterlacerProject<T> project, List<T> jobs)
	{
		project.addJobs(jobs);

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
		data.projectName = projectName;
		
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
	
	public int getNumBackgroundJobs() {
		return numBackgroundJobs;
	}


	public void setNumBackgroundJobs(int numBackgroundJobs) {
		this.numBackgroundJobs = numBackgroundJobs;
	}
	
	public synchronized boolean projectExists(String projectName)
	{
		return (projects.get(projectName) != null);
	}
	
	public synchronized void removeProject(String projectName)
	{
		projects.remove(projectName);
	}

	public synchronized void markProjectComplete(String projectName)
	{
		projects.get(projectName).jobs.markDone();
	}
	
	public synchronized InterlacerProject<T> getProject(String projectName)
	{
		try {
			return projects.get(projectName).jobs;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
		
	public void doWork()
	{
		
		Project<T> project;
		List<Project<T>> projectList = new ArrayList<Project<T>>();
		List<Project<T>> completedProjects = new ArrayList<Project<T>>();
		List<T> jobList = new ArrayList<T>();
		
		
		while(true) {
			
			int totalPriority = 0;
			int workingProjects = 0;
			float blockSize;
			
			try {
				
				projectList.clear();
				completedProjects.clear();
				
				
				////////////////////////////////////////////////////////////
				// PLANNING
				////////////////////////////////////////////////////////////
				
				/* lock against this object to make sure there are no new projects created and no 
				 * modifications made to existing projects while we are deciding what jobs to run
				 */
				synchronized (this)
				{
					while (true) {
						
						
						for (String projectName : projects.keySet()){
							project = projects.get(projectName);
							
							if (project.jobs.hasJobs()) {
								
								//add this project to the list of projects this thread will work on
								projectList.add(project);
								
								//mark this project has being worked on by this thread
								project.workingThreads.add(Thread.currentThread());
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
						blockSize = Math.max(5, (float)iterationSize / (float)totalPriority);
					} else if(projectList.size() > 0) {
						//there are no foreground projects, but there are background projects
						//so we work on the background projects instead
						workingProjects = Math.min(numBackgroundJobs, projectList.size());
						blockSize = Math.max(1, (float)iterationSize / (float)workingProjects);
						
						//remove any projects above the cap for number of background projects
						while (projectList.size() > numBackgroundJobs) projectList.remove(numBackgroundJobs);
						
					} else {
						blockSize = 5;
					}
					
				}//synchronize
				
				
				
				////////////////////////////////////////////////////////////
				// RUNNING
				////////////////////////////////////////////////////////////
				
				//run jobs on the projects we have in our list. We time them for logging purposes.
				long totalTime = 0;
				long t1, t2;
				int jobCount, jobsExecuted = 0;
				boolean success = false;
				
				for (Project<T> currentProject : projectList)
				{
					
					if (totalPriority > 0) {
						jobCount = Math.round(blockSize * currentProject.priority);
					} else {
						jobCount = Math.round(blockSize);
					}
					
					
					t1 = System.currentTimeMillis();
					
					
					jobList = currentProject.jobs.getJobs(jobCount);
					jobsExecuted += jobList.size();
					
					success = currentProject.jobs.runJobs(jobList);
					
					//if this run failed, but this is configured to make sure every job runs at least once
					//place the jobs back in the project's job list
					if (!success && runAtLeastOnce)
					{
						//this will lock internally
						addJobs(currentProject.jobs, jobList);
					}
					
					t2 = System.currentTimeMillis();
					totalTime += (t2 - t1);
					
				}
	
				if (projectList.size() > 0 && debugOutput) System.out.println(DPName + " " + Thread.currentThread() + ": Processed " + jobsExecuted + " Jobs from " + workingProjects + " projects in " + totalTime/1000f + "s");
				
				
				
				
				////////////////////////////////////////////////////////////
				// CLEAN-UP
				////////////////////////////////////////////////////////////
				
				//all the projects in the set, mark them as us not working on them anymore, then clean up finished ones
				synchronized (this) {
					
					for (Project<T> currentProject : projects.values())
					{
						currentProject.workingThreads.remove(Thread.currentThread());
						
						//if there are no more jobs left, and it is marked as complete, AND there 
						//are no threads marked as working on it anymore, add it to the list of projects
						//to remove
						if (  
								!currentProject.jobs.hasJobs() && 
								currentProject.jobs.isDone() &&
								currentProject.workingThreads.size() == 0
						) 
						{
							completedProjects.add(currentProject);
						}
						
					}
					
					
					for (Project<T> currentProject : completedProjects)
					{
						
						//allow the project to clean up before being removed
						currentProject.jobs.done();
						projects.remove(currentProject.projectName);					
						
					}
					
					completedProjects.clear();
					
				}			
	
				
			} catch (Exception e) {
				
				e.printStackTrace();
								
			}
			
		}//while
			
	}
	
	
}

class Project<T>
{
	
	public String projectName;
	public int priority;
	public InterlacerProject<T> jobs;
	public Set<Thread> workingThreads = new HashSet<Thread>();
	
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
		dp.doWork();
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