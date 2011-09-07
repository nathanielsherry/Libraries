package bolt.plugin;


import java.io.File;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import commonenvironment.Env;
import commonenvironment.zipfs.ZipFS;
import commonenvironment.zipfs.ZippedFile;

import fava.functionable.FList;
import fava.signatures.FnCondition;
import fava.signatures.FnMap;




public class BoltPluginLoader<T extends BoltPlugin>
{

	public FList<Class<T>>	availablePlugins;

	private Class<T> parentClass;

	private FnCondition<Class<T>> filter;
	
	
	private BoltPluginLoader()
	{
		availablePlugins = new FList<Class<T>>();
	}
	
	/**
	 * Creates a PluginLoader which will locate any plugins which are subclasses of parentClass
	 * @param parentClass
	 * @throws ClassInheritanceException
	 */
	public BoltPluginLoader(final Class<T> parentClass) throws ClassInheritanceException
	{
		
		this();
		
		this.parentClass = parentClass;
		
		filter = new FnCondition<Class<T>>() {

			@Override
			public Boolean f(Class<T> c) {
				
				//make sure the plugin is a subclass of the given class
				if (!checkSuperclasses(c, parentClass)) return false;
				
				//make sure its not an interface or an abstract class
				if (!isActualPlugin(c)) return false;
				
				//if its not annotated as a plugin, return false.
				if (c.getAnnotation(Plugin.class) == null) return false;
				
				return true;
			}};
		
		if (!checkSuperclasses(parentClass, BoltPlugin.class)) {
			throw new ClassInheritanceException();
		}
		
	}
	
	public BoltPluginLoader(final String pluginGroup) {
		
		this();
		
		this.parentClass = (Class<T>)BoltPlugin.class;
		
		
		filter = new FnCondition<Class<T>>() {

			@Override
			public Boolean f(Class<T> c) {
				
				//make sure the plugin is a subclass of BoltPlugin
				if (!checkSuperclasses(c, BoltPlugin.class)) return false;
				
				//make sure its not an interface or an abstract class
				if (!isActualPlugin(c)) return false;
				
				//if its not annotated as a plugin with the right group, return false.
				if (c.getAnnotation(Plugin.class) == null) return false;
				if (! pluginGroup.equals(c.getAnnotation(Plugin.class).group())) return false;
				
				return true;
				
			}};
		
	}
	
	
	public BoltPluginLoader(final Class<T> parentClass, final String pluginGroup) throws ClassInheritanceException
	{
		
		this();
		
		this.parentClass = parentClass;
		
		filter = new FnCondition<Class<T>>() {

			@Override
			public Boolean f(Class<T> c) {
				
				//make sure the plugin is a subclass of the given class
				if (!checkSuperclasses(c, parentClass)) return false;
				
				//make sure its not an interface or an abstract class
				if (!isActualPlugin(c)) return false;
				
				//if its not annotated as a plugin with the right group, return false.
				if (c.getAnnotation(Plugin.class) == null) return false;
				if (! pluginGroup.equals(c.getAnnotation(Plugin.class).group())) return false;
				
				return true;
				
				
			}};
		
		if (!checkSuperclasses(parentClass, BoltPlugin.class)) {
			throw new ClassInheritanceException();
		}
		
	}
	
	
	public List<Class<T>> getAvailablePlugins()
	{
		return availablePlugins.toSink();
	}


	public List<T> getNewInstancesForAllPlugins()
	{
		return availablePlugins.map(new FnMap<Class<T>, T>() {

			public T f(Class<T> f)
			{
					return BoltPlugin.createNewInstanceFromClass(f);
			}});
	}
	

	/**
	 * Attempts to load the plugins which are distrubuted with the applcication.
	 * If the application is in a jar file, attempts to load plugins from within the jar file
	 */
	public void loadLocalPlugins(String packageName)
	{

		if (Env.isClassInJar(BoltPluginLoader.class))
		{

			File jarFile = Env.getJarForClass(BoltPluginLoader.class);
			if (jarFile == null) return;
			
			loadPluginsFromJar(jarFile, packageName);

		}
		else
		{
			loadPluginsFromFilesystem(packageName);
		}

		
	}
	
	
	public void loadPluginsFromJar(File jarFile)
	{
		loadPluginsFromJar(jarFile, null);
	}

	// messy logic for getting a list of all available Plugins from inside of a jar file
	public void loadPluginsFromJar(File jarFile, String packageName)
	{
		
		//File jarFile:			the jar file we want to look inside of
		//Class<T> c:			the class that we want all of our plugins to descend from
		//String packageName:	the package that we want to restrict our view to
		
		
		//in a jar file, org.some.package should become org/some/package 
		ZipFS g = new ZipFS(jarFile);
		FList<ZippedFile> files = g.getAllFiles(); //g.getChildren(g.getZippedFile(packageName.replace(".", "/") + "/"));
		
		List<Class<T>> foundPlugins = new ArrayList<Class<T>>();
		
		try {
			
			//get this jar file loaded by a class loader
			URL jarurl = new URL("jar", "","file:" + jarFile.getAbsolutePath()+"!/");
			URLClassLoader cl = URLClassLoader.newInstance(new URL[] {jarurl });
			
			//for each file in the jar
			for (ZippedFile e : files)
			{
				if (e.getName().length() < 6) continue;
				if (e.isDirectory()) continue;
				if (!e.getName().endsWith(".class")) continue;
				
				String classPathName = e.getName().substring(0, e.getName().length()-6);
				String qualifiedClassName = classPathName.replace("/", ".");
							
				//build a qualified class name out of the filename. If packageName is not null, we have
				//to check to make sure that the package names match 
				if (packageName != null){
								
					int lastDot = qualifiedClassName.lastIndexOf('.');
					if (lastDot == -1) continue;
					String thisPackageName = qualifiedClassName.substring(0, lastDot);
					if (! thisPackageName.equals(packageName)) continue;
					
				}
				
				
				//attempts to load this class using the class loader we just fed this jar file to
				Class<?> loadedClass;
				try{
					loadedClass = cl.loadClass(qualifiedClassName);
				} catch (Throwable exception) {
					continue;
				}
				Class<T> clazz = (Class<T>)loadedClass;
				if (filter.f(clazz)) {
					if (isEnabledPlugin(clazz)) foundPlugins.add(clazz);
				}
			}
			
			availablePlugins.addAll( foundPlugins );
			availablePlugins = availablePlugins.unique();
			
			
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
				
	}


	public void loadPluginsFromJarsInDirectory(File directory)
	{
		loadPluginsFromJarsInDirectory(directory, null);
	}
	
	public void loadPluginsFromJarsInDirectory(File directory, String packageName)
	{
		
		if (!directory.exists()) return;
		if (!directory.isDirectory()) return;
		
		File[] filesInDir = directory.listFiles();
		
		for (File file : filesInDir)
		{
			if (file.isDirectory()) continue;
			String filename = file.getName();
			if (!filename.endsWith(".jar")) continue;
			
			loadPluginsFromJar(file, packageName);			
			
		}
		
	}





	// messy logic for getting a list of all available Plugins from the filesystem
	public void loadPluginsFromFilesystem(String packageName)
	{

		List<Class<T>> foundPlugins = new ArrayList<Class<T>>();

		List<Class<?>> classes = new FList<Class<?>>();

		try
		{
			classes.addAll(getClasses(packageName));
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if (classes != null)
		{
			for (int i = 0; i < classes.size(); i++)
			{
				Class<T> clazz = (Class<T>)classes.get(i);
				if (filter.f(clazz)) {
					if (isEnabledPlugin(clazz)) foundPlugins.add(clazz);
				}
			}
		}

		availablePlugins.addAll( foundPlugins );
		availablePlugins = availablePlugins.unique();

	}

	private boolean isEnabledPlugin(Class<T> clazz)
	{
		return (
				BoltPlugin.createNewInstanceFromClass(clazz) != null && 
				BoltPlugin.createNewInstanceFromClass(clazz).pluginEnabled()
		);
			
	}
	
	private boolean checkSuperclasses(Class<?> c, Class<?> target)
	{
		
		if (c == null) return false;
		
		if (Modifier.isInterface(	c.getModifiers()  )) return false;
		Class<?> sc = c.getSuperclass();
		
		while (sc != Object.class) {

			if (Modifier.isInterface(	sc.getModifiers()  )) return false;
			if (sc == target) return true;
			sc = sc.getSuperclass();
		}
		
		return false;
				
	}
	
	private boolean isActualPlugin(Class<?> c)
	{

		if (Modifier.isInterface(	c.getModifiers()  )) return false;
		if (Modifier.isAbstract(	c.getModifiers()  )) return false;
		
		return true;
	}

	
	// really really messy logic for getting all classes which extend AbstractPlugin
	private List<Class<?>> getClasses(String pckgname) throws ClassNotFoundException
	{

		List<Class<?>> classes = new ArrayList<Class<?>>();
		// Get a File object for the package
		File directory = null;
		try
		{
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null)
			{
				throw new ClassNotFoundException("Can't get class loader.");
			}

			String relpath = pckgname.replace('.', '/');

			
			String abspath = parentClass.getProtectionDomain().getCodeSource().getLocation() + relpath;
			URI uri = new URI(abspath);
					
			directory = new File(uri.getPath());
		}
		catch (Exception x)
		{
			throw new ClassNotFoundException(pckgname + " (" + directory + ") does not appear to be a valid package");
		}

		if (directory.exists())
		{
			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++)
			{
				// we are only interested in .class files
				if (files[i].endsWith(".class"))
				{
					// removes the .class extension
					String classname = files[i].substring(0, files[i].length() - 6);
					classes.add(Class.forName(pckgname + '.' + classname));
				}
			}
		}
		else
		{
			throw new ClassNotFoundException(pckgname + " does not appear to be a valid package");
		}

		return classes;
	}
	
	
	
	

	
	
}
