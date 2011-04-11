package bolt;
import java.io.StringWriter;
import java.util.LinkedHashMap;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.python.modules.synchronize;


public class Bolt {

	protected static final String LANGUAGE = "jython";
	
	
	
	private ScriptEngine engine;
	
	private LinkedHashMap<Thread, Bindings> threadBindings;
	
	private String script;
	private CompiledScript compiledScript = null;
	
	protected boolean hasSideEffects = false;
	
	
	
	public Bolt(String language, String script)  {
	
		threadBindings = new LinkedHashMap<Thread, Bindings>();
		
		engine = new ScriptEngineManager().getEngineByName(language);
		engine.getContext().setWriter(new StringWriter());
		engine.getContext().setErrorWriter(new StringWriter());
		
		setScript(script);
		
		clear();
		
	}
	
	//looks up the bindings for the current thread
	private Bindings getBindings()
	{
		//if we're allowing side-effects, we can only use one set of bindings.
		if (hasSideEffects) return threadBindings.values().iterator().next();
		
		
		Bindings bindings;
		bindings = threadBindings.get(Thread.currentThread());
		
		if (bindings == null) {
			bindings = engine.createBindings();
			threadBindings.put(Thread.currentThread(), bindings);			
		}
		
		return bindings;
	}

	
	protected void run() throws ScriptException
	{
		//if we're allowing side-effects, then we can't have more than one
		//set of bindings, which means that we can't have more than one thread
		//using a set of bindings at once.
		if (hasSideEffects) {
			synchronized (this) {
				eval();				
			}
		} else {
			eval();
		}
	}
	
	private void eval() throws ScriptException
	{
		if (compiledScript == null) {
			engine.eval(script, getBindings());
		} else {
			compiledScript.eval(getBindings());
		}
	}
	
	public void hasSideEffects(boolean sideEffects)
	{
		this.hasSideEffects = sideEffects;
	}

	
	protected void clear()
	{
		
		engine.getBindings(ScriptContext.ENGINE_SCOPE).clear();
		
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
		return engine.getContext().getErrorWriter().toString();
	}
	
	public String getStdOut()
	{
		return engine.getContext().getWriter().toString();
	}
	
}
