package bolt.scripting.languages;

public abstract class Language {

	public abstract String getName();
	public abstract boolean isCompilable();
	
	
	public static Language ruby(){ return new Ruby(); }
	public static Language python(){ return new Python(); }
	public static Language javascript(){ return new Javascript(); }
	
}
