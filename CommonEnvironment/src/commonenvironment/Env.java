package commonenvironment;



import java.io.File;
import java.net.URISyntaxException;

import javax.jnlp.ServiceManager;




public class Env
{

	public static boolean inJar(Class<?> classInJar)
	{
		Env env = new Env();
		String className = classInJar.getName().replace('.', '/');
		String classJar = env.getClass().getResource("/" + className + ".class").toString();
		return classJar.startsWith("jar:");
	}
	public static boolean inJar()
	{
		return inJar(Env.class);
	}
	

	public static File jarFile(Class<?> classInJar)
	{
		if (!inJar(classInJar)) return null;
		try {
			return new File(classInJar.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			return null;
		}
	}
	
	public static File jarFile()
	{
		return jarFile(Env.class);
	}
	

	public static boolean isWindows()
	{

		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);

	}


	public static boolean isMac()
	{

		String os = System.getProperty("os.name").toLowerCase();
		// Mac
		return (os.indexOf("mac") >= 0);

	}


	public static boolean isUnix()
	{

		String os = System.getProperty("os.name").toLowerCase();
		// linux or unix
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);

	}


	public static boolean isWebStart()
	{
		return (ServiceManager.getServiceNames() != null && ServiceManager.getServiceNames().length != 0);
	}
	
	public static int heapSize()
	{
		return (int)(Runtime.getRuntime().maxMemory() / 1024f / 1024f);
	}
	
	
	public static void perOS(PerOS action)
	{
		if (isWindows()) 	{ action.windows(); return; }
		if (isMac()) 		{ action.mac(); 	return; }
		if (isUnix()) 		{ action.unix();	return; }
		
		action.other();
	}

	public interface PerOS
	{
		public void windows();
		public void mac();
		public void unix();
		public void other();
	}
	
}
