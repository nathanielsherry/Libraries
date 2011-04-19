package bolt.dynamiccompiling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import commonenvironment.Env;

public class BoltCompiler {

	private List<File> classpath = new ArrayList<File>();
	private String sourcecode;
	
	public BoltCompiler() {
		
		/*

		*/
				
	}
	
	public void setSourceCode(String source)
	{
		sourcecode = source;
	}
	
	
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
	
	public void compile()
	{
		File temp;
		try {
			temp = File.createTempFile(this.getClass().getName() + ": ", ".java");
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
			writer.write(sourcecode);
			writer.close();
			
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			compiler.run(null, null, null, "-classpath " + getClassPathString(),  "\"" + temp.getCanonicalPath() + "\"");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
