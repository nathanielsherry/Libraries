package bolt.compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
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

import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

import commonenvironment.Env;
import fava.functionable.FList;

public class BoltJavaCompiler {
	
	private List<File> classpath = new ArrayList<File>();
	private String sourcecode;
	private String classname;
	
	private List<Class<?>> classList;
	private Class<?> mainClass;
	

	public BoltJavaCompiler(String classname) {
		
		this.classname = classname;
					
	}
	
	public void setSourceCode(String source)
	{
		sourcecode = source;
	}
	

	
	public void compile()
	{
		File temp;
		StringWriter out = new StringWriter();
		StringWriter err = new StringWriter();
		
		try {
			
			temp = File.createTempFile("BoltCompiler ➤ Compilation Directory [temp:", "]");
			temp.delete();
			temp.mkdir();
			temp.deleteOnExit();
			
			File sourceFile = new File(temp.getCanonicalPath() + "/" + classname + ".java");
			

			BufferedWriter writer = new BufferedWriter(new FileWriter(sourceFile));
			writer.write(sourcecode);
			writer.close();

			
			

			boolean success = BatchCompiler.compile("-source 1.6 " + "\"" + sourceFile.getCanonicalPath() + "\"", new PrintWriter(out), new PrintWriter(err), null);
			
			if (!success)
			{
				throw new RuntimeException("Compilation Error:\n" + err.toString());
			}
			
			/*
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			
			compiler.run(null, null, null, sourceFile.getCanonicalPath());
			*/
			
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
			throw new RuntimeException("Compilation Error:\n" + err.toString());
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
