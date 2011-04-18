package bolt.plugin;



import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;



import commonenvironment.Env;
import commonenvironment.zipfs.ZipFS;
import commonenvironment.zipfs.ZippedFile;


import fava.Fn;
import fava.functionable.FList;
import fava.signatures.FnMap;




class AvailablePlugins<T extends BoltPlugin>
{

	public List<Class<T>>	availablePlugins;


	public AvailablePlugins(Class<T> c, String packageName)
	{
		
		if (!checkSuperclasses(c, BoltPlugin.class)) return;
		
		if (availablePlugins == null)
		{

			if (Env.inJar())
			{
				try {
					File jarFile = new File(AvailablePlugins.class.getProtectionDomain().getCodeSource().getLocation().toURI());
					
					ZipFS g = new ZipFS(jarFile);
					FList<ZippedFile> files = g.getChildren(g.getZippedFile(packageName.replace(".", "/") + "/"));
					
					availablePlugins = new ArrayList<Class<T>>();
					
					try {
						
						URL jarurl = new URL("jar", "","file:" + jarFile.getAbsolutePath()+"!/");
						
						URLClassLoader cl = URLClassLoader.newInstance(new URL[] {jarurl });
						
						
						for (ZippedFile e : files)
						{
							String[] path = e.getName().split("/");
							
							String className = path[path.length - 1];
							className = className.substring(0, className.length()-6);
							
							String classPath = packageName + "." + className;
							
							Class<?> loadedClass = cl.loadClass(classPath);
							if (checkSuperclasses(loadedClass, c)) {
								availablePlugins.add((Class<T>)loadedClass);
							}
						}
						
						
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
					
					
					
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//availablePlugins = generatePluginListStatic();
			}
			else
			{
				availablePlugins = generatePluginListDynamic(c, packageName);
			}
		}

	}
	
	public List<Class<T>> getAvailablePlugins()
	{
		return availablePlugins;
	}



	public List<T> getNewInstancesForAllPlugins()
	{
		return Fn.map(availablePlugins, new FnMap<Class<T>, T>() {

			public T f(Class<T> f)
			{
					return BoltPlugin.createNewInstanceFromClass(f);
			}});
	}
	
	








	// messy logic for getting a list of all available Plugins
	private List<Class<T>> generatePluginListDynamic(Class<T> c, String packageName)
	{

		List<Class<T>> list = new ArrayList<Class<T>>();

		List<Class<?>> classes = new FList<Class<?>>();

		try
		{
			classes.addAll(getClasses(c, packageName));
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Class<T> f;

		if (classes != null)
		{
			for (int i = 0; i < classes.size(); i++)
			{
				if (checkSuperclasses(classes.get(i), c))
				{

					f = (Class<T>) classes.get(i);
					if (
							BoltPlugin.createNewInstanceFromClass(f) != null && 
							BoltPlugin.createNewInstanceFromClass(f).pluginEnabled()
						) list.add(f);

				}
			}
		}

		return list;

	}

	private boolean checkSuperclasses(Class c, Class target)
	{
		Class sc = c.getSuperclass();
		
		while (sc != Object.class) {
			if (sc == target) return true;
			sc = sc.getSuperclass();
		}
		
		return false;
				
	}

	// really really messy logic for getting all classes which extend AbstractPlugin
	private List<Class<?>> getClasses(Class<T> c, String pckgname) throws ClassNotFoundException
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
			String path = pckgname.replace('.', '/');

			String abspath = c.getProtectionDomain().getCodeSource().getLocation() + path;
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
