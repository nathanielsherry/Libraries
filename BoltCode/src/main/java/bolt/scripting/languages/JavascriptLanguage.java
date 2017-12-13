package bolt.scripting.languages;

import javax.script.ScriptEngine;

public class JavascriptLanguage extends Language{

	@Override
	public String getName() {
		return "javascript";
	}

	@Override
	public boolean isCompilable() {
		return true;
	}

	

	
}
