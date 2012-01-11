package bolt.plugin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This interface is used to mark classes as Bolt plugins to be loaded for use.
 * @author Nathaniel Sherry, 2010-2012
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin {

	String group()	default "";
	
}
