package bolt.plugin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This interface is used to mark classes as Bolt plug-ins to be loaded for use. 
 * Since not every class which implements BoltPlugin (or extends a class which does) 
 * is necessarily a plug-in, this annotation interface is used for this purpose.  
 * @author Nathaniel Sherry, 2010-2012
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin {

	String group()	default "";
	
}
