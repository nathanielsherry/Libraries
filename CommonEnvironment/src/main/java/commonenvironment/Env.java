package commonenvironment;



import java.io.File;
import java.net.URISyntaxException;




public class Env
{

	public enum OS {
		WINDOWS,
		MAC,
		UNIX,
		OTHER
	}
	
	public static boolean isClassInJar(Class<?> classInJar)
	{
		Env env = new Env();
		String className = classInJar.getName().replace('.', '/');
		String classJar = env.getClass().getResource("/" + className + ".class").toString();
		return classJar.startsWith("jar:");
	}
	

	public static File getJarForClass(Class<?> classInJar)
	{
		if (!isClassInJar(classInJar)) return null;
		try {
			return new File(classInJar.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			return null;
		}
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

	
	public static Env.OS getOS()
	{
		if (isWindows()) return OS.WINDOWS;
		if (isMac()) return OS.MAC;
		if (isUnix()) return OS.UNIX;
		return OS.OTHER;
	}
	
	public static long heapSize()
	{
		return (long)(heapSizeBytes() / 1024f / 1024f);
	}
	
	public static long heapSizeBytes()
	{
		return Runtime.getRuntime().maxMemory();
	}
	
	
	public static File appDataDirectory(String appname) {
		return appDataDirectory(appname, "");
	}
	
	public static File appDataDirectory(String appname, String subpath)
	{
		switch (getOS())
		{
			case WINDOWS: return new File(System.getenv("APPDATA") + "\\" + appname + "\\" + subpath);
			case MAC: return new File(homeDirectory() + "/Library/Application Support/" + appname + "/" + subpath);
			
			case OTHER:
			case UNIX:
			default:
				return new File(homeDirectory() + "/.config/" + appname + "/" + subpath);
		}

	}
	
	public static void main(String[] args) {
		
	}


	public static File homeDirectory() {
		return new File(System.getProperty("user.home"));
	}
	

}
