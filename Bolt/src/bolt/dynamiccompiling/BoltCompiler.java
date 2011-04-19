package bolt.dynamiccompiling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import commonenvironment.Env;
import fava.functionable.FList;

public class BoltCompiler {

	private List<File> classpath = new ArrayList<File>();
	private String sourcecode;
	private String classname;
	
	private List<Class<?>> classList;
	private Class<?> mainClass;
	
	public BoltCompiler(String classname) {
		
		this.classname = classname;
		
		/*

		*/
				
	}
	
	public void setSourceCode(String source)
	{
		sourcecode = source;
	}
	

	
	/*
	public void addClassToClassPath(Class<?> newclass)
	{
		File classfile = null;
				
		System.out.println(Env.classpath(newclass));
		classfile = Env.classpath(newclass);
		
		classpath.add(classfile);
		
	}
	
	private String getClassPathString()
	{
		String cp = "";
		
		for (File f : classpath)
		{
			try {
				
				String path = f.getCanonicalPath();
				if (cp.length() > 0) {
					cp += ":" + path;
				} else {
					cp += path;
				}
				
			} catch (IOException e) {
			}
		}
		
		return cp;
		
	}
	*/
	
	public void compile()
	{
		File temp;
		try {
			
			temp = File.createTempFile("BoltCompiler", ".java");
			temp.delete();
			temp.mkdir();
			
			File sourceFile = new File(temp.getCanonicalPath() + "/" + classname + ".java");
			

			BufferedWriter writer = new BufferedWriter(new FileWriter(sourceFile));
			writer.write(sourcecode);
			writer.close();

			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

			
			compiler.run(null, null, null, sourceFile.getCanonicalPath());
			
			File[] classes = temp.listFiles(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".class");
				}
			});
			
			
			classList = new ArrayList<Class<?>>();
			
			
			for (File f : classes)
			{
				URLClassLoader cl = URLClassLoader.newInstance(new URL[] {temp.toURI().toURL()});
				try {
					
					Class<?> currentClass = cl.loadClass(classname);
					classList.add(currentClass);
					if (f.getName().equals(classname + ".class")) {
						mainClass = currentClass;
					}
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
						
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Class<?> getMainClass()
	{
		return mainClass;
	}
	
	public List<Class<?>> getClassList()
	{
		return classList;
	}
	
	
}
