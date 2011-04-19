import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import commonenvironment.Env;


import bolt.dynamiccompiling.BoltCompiler;
import bolt.scripting.BoltScripter;
import bolt.scripting.BoltMap;

import fava.Fn;
import fava.functionable.FList;
import fava.functionable.Range;
import fava.signatures.FnMap;


public class Test {

	private static final int rangeSize = 1000000;
	
	public static void main(String args[])
	{
		
		//listScriptingEngines();
		compiling();
		script();
		
	}
	
	public static void compiling()
	{
		
		BoltCompiler c = new BoltCompiler("TestCompile");
		//c.addClassToClassPath(BoltCompiler.class);
		//c.addClassToClassPath(org.python.Version.class);
		
		String source = "" + 
		"import fava.signatures.*;" +
		"" +
		"public class TestCompile implements TestInterface {" +
		"" +
		"  public static int hello(){" +
		"    FnMap<String, String> mapper = new FnMap<String, String>() {" +
		"      @Override" +
		"      public String f(String v) {" +
		"        return null;" +
		"      }};" +
		"" +
		"    mapper.f(\"\");" +
		"    return 5;" +
		"  }" +
		"}";
		
		source = "" + 
		"import fava.signatures.*;" +
		"" +
		"public class TestCompile implements FnMap<Integer, Integer> {" +
		"" +
		"	public Integer f(Integer i) {" +
		"		return i+1;" +
		"	}" +
		"}";
		
		c.setSourceCode(source);
		
		c.compile();
		
		try {
			
			Class<?> cls = c.getMainClass();
			
			Object o = cls.newInstance();
			Method myMethod = cls.getMethod("f", Integer.class);
			
			Range ints = new Range(1, rangeSize);
			
			long startTime = System.currentTimeMillis();
			
			Fn.map(ints, (FnMap<Integer, Integer>)o);

			System.out.println("BoltCompiler:");
			System.out.println(System.currentTimeMillis() - startTime);
			
			//System.out.println(myMethod.invoke(o, new Integer(1)));
			
			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		/*
		System.out.println("Code Path:");
		System.out.println(Env.codePath(Test.class));
		System.out.println(Env.codePath(org.python.Version.class));
		
		System.out.println();
		System.out.println("Class Path");
		System.out.println(Env.classpath(BoltCompiler.class));
		System.out.println(Env.classpath(org.python.Version.class));
		*/
		
	}
	
	public static void script()
	{
		
		
		long startTime;
		Range ints = new Range(1, rangeSize);
		
		BoltMap<Integer, Integer> inc;
		/*
		
		inc = new BoltMap<Integer, Integer>("jruby", "int", "inc", "$inc = $int+1");
				
		startTime = System.currentTimeMillis();
			
			ints.map(inc);
			//System.out.println(ints.toSink().show());
			//System.out.println(ints.map(inc).show());
		System.out.println("Increment:");
		System.out.println(System.currentTimeMillis() - startTime);	
		
		
		
		
		
		
		BoltMap<FList<Integer>, FList<Integer>> listinc;
		listinc = new BoltMap<FList<Integer>, FList<Integer>>("jruby", "$incs = $ints.map{|n| n+1}", "ints", "incs");
		
		
		FList<Integer> intlist = ints.toSink();
		startTime = System.currentTimeMillis();
			
			//ints.map(inc);
			//System.out.println(ints.toSink().show());
			for (int i = 0; i < 500; i++)
			{
				listinc.f(intlist);
				
			}
		
		System.out.println(System.currentTimeMillis() - startTime);	
		
		*/
		
		
		
		startTime = System.currentTimeMillis();
			
			ints.map(new FnMap<Integer, Integer>() {

				@Override
				public Integer f(Integer v) {
					return v+1;
				}
			});
			//System.out.println(ints.toSink().show());
			//System.out.println(ints.map(inc).show());
		System.out.println("\nJAVA:");
		System.out.println(System.currentTimeMillis() - startTime);	
			
			
			
		
/*
		inc = new BoltMap<Integer, Integer>("jruby", "i", "j", "def inc(v)\n\tv+=1\nend\n\n$j = inc($i)");
		
		startTime = System.currentTimeMillis();
			
			ints.map(inc);
			
			//FList<Float> floats = new FList<Float>(1f, 2f, 3f);
			//System.out.println(inc2.f(floats).get(0));
			//System.out.println(ints.toSink().show());
			//System.out.println(ints.map(inc).show());
		System.out.println("\nJRUBY:");
		System.out.println(System.currentTimeMillis() - startTime);	
		
		
		System.exit(0);
		*/
		
		
		/*
		
		inc = new BoltMap<Integer, Integer>("groovy", "j = i+1;", "i", "j");
		
		startTime = System.currentTimeMillis();
		
			ints.map(inc);

		System.out.println("\nGROOVY:");
		System.out.println(System.currentTimeMillis() - startTime);
		
		*/
		
		
		inc = new BoltMap<Integer, Integer>("python", "i", "j", 
			"j = i+1;"
		);
		
		startTime = System.currentTimeMillis();
		
			ints.map(inc);

		System.out.println("\nJYTHON:");
		System.out.println(System.currentTimeMillis() - startTime);	
		
		
		
		
		
		inc = new BoltMap<Integer, Integer>("js", "i", "j", 
				"j = i+1"
		);
		
		startTime = System.currentTimeMillis();
		
			ints.map(inc);

		System.out.println("\nJAVASCRIPT:");
		System.out.println(System.currentTimeMillis() - startTime);	
		
		
		
		/*
		inc = new BoltMap<Integer, Integer>("BeanShell", "i", "j", 
			"j = i+1;"		
		);
		
		startTime = System.currentTimeMillis();
		
			ints.map(inc);

		System.out.println("\nJYTHON:");
		System.out.println(System.currentTimeMillis() - startTime);	
		
*/
		
		
		
		
	}
	
	public static FList<ScriptEngineFactory> getAvailableLanguages()
	{
		FList<ScriptEngineFactory> factories = new FList<ScriptEngineFactory>();
		
        //ScriptEngineManager mgr = new ScriptEngineManager();
        
        for (ScriptEngineFactory factory : new ScriptEngineManager().getEngineFactories()) {
        	
        	try {
        		System.out.print(factory.getEngineName() + ": ");        		
        		
        		BoltScripter b = new BoltScripter(factory.getLanguageName(), "");
        		        		
        		System.out.println("PASS");
        		factories.add(factory);
        	} catch (NoClassDefFoundError e) {
        		System.out.println("x FAIL");
        	} catch (Exception e){
        		System.out.println("x FAIL");
        	}
        	
        	
        }
        
        return factories.unique();
	}
	
    public static void listScriptingEngines() {
    	
    	
    	
        for (ScriptEngineFactory factory : getAvailableLanguages()) {
            
        	System.out.println(factory.getLanguageName());
        	//System.out.printf("\tScript Engine: %s (%s)\n", factory.getEngineName(), factory.getEngineVersion());
            //System.out.printf("\tLanguage: %s (%s)\n", factory.getLanguageName(), factory.getLanguageVersion());
            //for (String name : factory.getNames()) {
            //    System.out.printf("\tEngine Alias: %s\n", name);
            //}
        }
    }
	
}
