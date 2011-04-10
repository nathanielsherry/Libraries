package bolt;
import java.io.StringWriter;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class Bolt {

	private ScriptEngine engine;
	private String script;
	private CompiledScript compiledScript = null;
	
	private StringWriter stderr;
	private StringWriter stdout;
	
	protected static final String LANGUAGE = "jython";
	
	protected boolean allowSideEffects = false;
	
	public Bolt(String language, String script)  {
	
		engine = new ScriptEngineManager().getEngineByName(language);
		engine.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
		
		setScript(script);
		
		clear();
		
	}
	

	protected void run() throws ScriptException
	{
		if (compiledScript == null) {
			engine.eval(script);
		} else {
			compiledScript.eval(engine.getContext());
		}
	}
	
	
	public void allowSideEffects(boolean allow)
	{
		this.allowSideEffects = allow;
	}

	
	protected void clear()
	{
		
		engine.getBindings(ScriptContext.ENGINE_SCOPE).clear();
		
		stdout = new StringWriter();
		stderr = new StringWriter();
		
		engine.getContext().setWriter(stdout);
		engine.getContext().setErrorWriter(stderr);
	}
	
	protected void set(String key, Object value)
	{
		engine.getBindings(ScriptContext.ENGINE_SCOPE).put(key, value);
	}
	
	protected Object get(String key)
	{
		return engine.getBindings(ScriptContext.ENGINE_SCOPE).get(key);
	}
	
	public void setScript(String script)
	{
		if (script.equals(this.script)) return;
		
		this.script = script;
		compiledScript = null;
		
		if (engine instanceof Compilable){
			
			try {
				compiledScript = ((Compilable)engine).compile(script);
			} catch (ScriptException e) {
				compiledScript = null;
			}
			
		}
	}
	
	public String getStdErr()
	{
		return stderr.toString();
	}
	
	public String getStdOut()
	{
		return stdout.toString();
	}
	
}
