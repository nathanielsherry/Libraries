package bolt.plugin;


import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.function.Predicate;

import commonenvironment.Env;

import fava.Functions;
import fava.functionable.FList;




public class BoltPluginLoader<T extends BoltPlugin>
{

	public FList<Class<T>>			availablePlugins;
	private Class<T> 				target;

	private Predicate<Class<T>> 	filter;
		
	
	/**
	 * Creates a PluginLoader which will locate any plugins which are subclasses or implementations of the target
	 * @param target
	 * @throws ClassInheritanceException
	 */
	public BoltPluginLoader(final Class<T> target) throws ClassInheritanceException
	{
		
		availablePlugins = new FList<Class<T>>();
		
		this.target = target;
		
		filter = new Predicate<Class<T>>() {

			boolean isTargetInterface = Modifier.isInterface(target.getModifiers());
			
			@Override
			public boolean test(Class<T> c) {
				
				//make sure its not an interface or an abstract class
				if (!isActualPlugin(c)) return false;
								
				//if target is an interface, c must implement it
				if (isTargetInterface)
				{
					//make sure the class implements the target interface
					if (!checkImplementsInterface(c, target)) return false;
				}
				//if target is a class, c must extend it
				else
				{
					//make sure the plugin is a subclass of the given class
					if (!checkSuperclasses(c, target)) return false;
				}
				
				
				return true;
				
				
			}};
		
		if (!checkImplementsInterface(target, BoltPlugin.class)) {
			throw new ClassInheritanceException();
		}
		
	}
	
	
	public List<Class<T>> getAvailablePlugins()
	{
		return availablePlugins.toSink();
	}


	public List<T> getNewInstancesForAllPlugins()
	{	
		return availablePlugins.map(new Function<Class<T>, T>() {

			public T apply(Class<T> f)
			{
					return createNewInstanceFromClass(f);
			}}).filter(Functions.<T>notNull());
	}
	

	public void registerPlugin(Class<?> loadedClass) throws ClassInstantiationException
	{
		
		try 
		{
			
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>)loadedClass;
					
			if (filter.test(clazz) && isEnabledPlugin(clazz)) availablePlugins.add(clazz);
			
			availablePlugins = availablePlugins.unique();
			
		}
		catch (ServiceConfigurationError e)
		{
			e.printStackTrace();
			throw new ClassInstantiationException(e);
		}
	}
	
	

	private boolean isEnabledPlugin(Class<T> clazz)
	{
		T instance = createNewInstanceFromClass(clazz);
		return instance != null && instance.pluginEnabled();			
	}
	
	@SuppressWarnings("unchecked")
	protected static <S extends BoltPlugin> S createNewInstance(S f)
	{
		return createNewInstanceFromClass((Class<S>)f.getClass());
	}


	protected static <S extends BoltPlugin> S createNewInstanceFromClass(Class<S> f)
	{
		try
		{
			return f.newInstance();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
			System.out.println(f);
			return null;
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
			System.out.println(f);
			return null;
		}
	}
	
	private boolean checkSuperclasses(Class<?> c, Class<?> target)
	{
		
		if (c == null) return false;
		
		if (Modifier.isInterface(	c.getModifiers()  )) return false;	
		
		return target.isAssignableFrom(c);
				
	}
	
	private boolean checkImplementsInterface(Class<?> c, Class<?> targetInterface)
	{
		if (c == null) return false;
		
		while (c != Object.class) {
		
			Class<?> ifaces[] = c.getInterfaces();
			for (Class<?> iface : ifaces)
			{
				if (iface.equals(targetInterface)) return true;
			}
						
			c = c.getSuperclass();
			
		}
		return false;
		
	}
	
	private boolean isActualPlugin(Class<?> c)
	{
		if (c.isInterface()) return false;
		if (c.isAnnotation()) return false;
		if (Modifier.isAbstract(	c.getModifiers()  )) return false;
		
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void register(File file)
	{
				
		File[] files;
		if (file.isDirectory())
		{
			files = file.listFiles(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name)
				{
					return name.toLowerCase().endsWith(".jar");
				}
			});
		}
		else
		{
			files = new File[1];
			files[0] = file;
		}
		
		
		for (int i = 0; i < files.length; i++)
		{
			try
			{
				register(files[i].toURI().toURL());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.err.println("Failed to load plugin at " + files[i]);
			}
		}
		
	}
	
	public void register(URL url) throws ClassInstantiationException
	{

		URLClassLoader urlLoader = new URLClassLoader(new URL[]{url});
		ServiceLoader<T> loader = ServiceLoader.load(target, urlLoader);
		
		for (T t : loader)
		{
			registerPlugin(t.getClass());
		}
	}
	
	public void register()
	{
		
		if (Env.isClassInJar(target))
		{
			register(Env.getJarForClass(target).getParentFile());
		}
		else
		{
			register(new File(".").getAbsoluteFile());
		}
	}
	
	
}
