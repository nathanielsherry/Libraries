package commonenvironment;

import java.io.File;
import java.net.URISyntaxException;


public class ClassOperations {


	/**
	 * Return the base path for the code
	 * @param theclass
	 * @return
	 */
	public static File codePath(Class<?> theclass)
	{
		if (Env.inJar(theclass)) {
			return Env.jarFile(theclass);
		} else {
			try {
				return new File(theclass.getProtectionDomain().getCodeSource().getLocation().toURI());
			} catch (URISyntaxException e) {
				return null;
			}
		}
	}
	
	/**
	 * Gets the file which would needed to be added to the classpath to include the given class
	 * @param theclass
	 * @return
	 */
	public static File classpath(Class<?> theclass)
	{
		if (Env.inJar(theclass))
		{
			return Env.jarFile(theclass);
		} else {
			
			String pathname;
			try {
				pathname = theclass.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
				String packageName = theclass.getName().replace(".", "/");
				return new File(pathname + packageName + ".class");
			} catch (URISyntaxException e) {
				return null;
			}

			
		}
	}
	
	
	

	
}
