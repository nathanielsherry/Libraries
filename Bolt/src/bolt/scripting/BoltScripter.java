package bolt.scripting;
import java.io.StringWriter;
import java.util.LinkedHashMap;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class BoltScripter {

	protected static final String LANGUAGE = "jython";
	
	
	
	
	private LinkedHashMap<Thread, ScriptEngine> threadEngines;
	private LinkedHashMap<Thread, CompiledScript> threadCompiledScripts;
	private ScriptEngine defaultEngine;
	
	private StringWriter writer, errorWriter;
	
	private String language;
	private String script;
	private CompiledScript defaultCompiledScript = null;
	
	protected boolean hasSideEffects = false;
	protected boolean multithreaded = false;
	
	
	
	public BoltScripter(String language, String script)  {
	
		this.language = language;
		
		threadEngines = new LinkedHashMap<Thread, ScriptEngine>();
		threadCompiledScripts = new LinkedHashMap<Thread, CompiledScript>();
		defaultEngine = createEngine();
		
		writer = new StringWriter();
		errorWriter = new StringWriter();
		
		setScript(script);
		
	}
	
	private ScriptEngine createEngine()
	{
		ScriptEngine engine = new ScriptEngineManager().getEngineByName(language);
		engine.getContext().setBindings(engine.createBindings(), ScriptContext.GLOBAL_SCOPE);
		engine.getContext().setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
		engine.getContext().setWriter(writer);
		engine.getContext().setErrorWriter(errorWriter);
		
		return engine;
	}
	
	//looks up the bindings for the current thread
	private ScriptEngine getEngine()
	{
		return getEngine(Thread.currentThread());
	}
	
	private ScriptEngine getEngine(Thread t) {
		//if we're allowing side-effects or if we're not multithreading, we can only use one set of bindings.
		if (hasSideEffects || !multithreaded) return defaultEngine;
		
		
		ScriptEngine engine;
		engine = threadEngines.get(t);
		
		if (engine == null) {
			
			engine = createEngine();
			threadEngines.put(t, engine);
			
		}
		
		return engine;
		
	}
	
	private Bindings getBindings(){
		return getBindings(Thread.currentThread());
	}
	
	private Bindings getBindings(Thread t){
		return getEngine(t).getBindings(ScriptContext.ENGINE_SCOPE);
	}
		

	
	private CompiledScript getCompiledScript()
	{
		if (hasSideEffects || !multithreaded) return defaultCompiledScript;
		
		CompiledScript compiledScript;
		compiledScript = threadCompiledScripts.get(Thread.currentThread());
		
		if (compiledScript == null) {
			compiledScript = compileScript(script);
			threadCompiledScripts.put(Thread.currentThread(), compiledScript);
		}
		
		
		return compiledScript;
		
	}
	
	
	

	
	public void run() throws ScriptException
	{
		//if we're not multithreading, then we can't have more than one
		//set of bindings, which means that we can't have more than one thread
		//using a set of bindings at once.
		if (!multithreaded) {
			synchronized (this) {
				eval();				
			}
		} else {
			eval();
		}
	}
	
	private void eval() throws ScriptException 
	{
		try {
			if (defaultCompiledScript == null) {
				getEngine().eval(script);
			} else {
				getCompiledScript().eval();
			}
		} catch (ScriptException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * If hasSideEffects is turned on, the scripting environment
	 * will not be cleared between invocations, allowing actions
	 * in one invocation to impact the next.
	 * @param sideEffects
	 */
	public void hasSideEffects(boolean sideEffects)
	{
		this.hasSideEffects = sideEffects;
	}

	/**
	 * If multithreading is turn on, multiple binding environments
	 * will be created -- one for each thread of execution -- to avoid
	 * interference. Turning multithreading on implies turning hasSideEffects
	 * off, as cross-invocation interaction becomes unreliable with 
	 * multiple execution environments. Getting and setting binding 
	 * values is only possible using the thread of execution (ie
	 * setting a value with Thread A and executing with Thread B will
	 * fail, as the value was set for Thread A's bindings). To set
	 * bindings cross-thread, use the method variants which accept a
	 * {@link Thread} object to indicate the intended thread of execution.
	 * 
	 * @param multithreaded
	 */
	public void setMultithreaded(boolean multithreaded)
	{
		this.multithreaded = multithreaded;
	}
	
	
	protected void clear()
	{
		getBindings().clear();
	}
	
	protected void set(String key, Object value)
	{
		getBindings().put(key, value);
	}
	
	protected Object get(String key)
	{
		return getBindings().get(key);
	}
	
	
	protected void clear(Thread t)
	{
		getBindings(t).clear();
	}
	
	protected void set(Thread t, String key, Object value)
	{
		getBindings(t).put(key, value);
	}
	
	protected Object get(Thread t, String key)
	{
		return getBindings(t).get(key);
	}
	
	
	
	public void setScript(String script)
	{
		if (script.equals(this.script)) return;
		
		threadCompiledScripts.clear();
		
		this.script = script;
		defaultCompiledScript = compileScript(this.script);
				
	}
	
	private CompiledScript compileScript(String script)
	{
		CompiledScript cs = null;
		
		if (getEngine() instanceof Compilable){
			
			try {
				cs = ((Compilable)getEngine()).compile(script);
			} catch (ScriptException e) {
				cs = null;
			}
			
		}
		
		return cs;
		
	}
	
	public String getStdErr()
	{
		return errorWriter.toString();
	}
	
	public String getStdOut()
	{
		return writer.toString();
	}
	
}


