package commonenvironment.zipfs;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import fava.Fn;
import fava.functionable.FList;
import fava.signatures.FnEach;
import fava.signatures.FnFold;
import fava.signatures.FnMap;


public class ZipFS {

	private ZipEntry root;
	private DirectedGraph<ZipEntry, Object> g;
	private ZipFile zf;
	
	public ZipFS(String filename) {
		this(new File(filename));
	}
	
	public ZipFS(File file) {
	
		
		try {
			
			zf = new ZipFile(file);
			
			Enumeration<? extends ZipEntry> entries = zf.entries();
			
			g = new SimpleDirectedGraph<ZipEntry, Object>(Object.class);
			
			
			root = new ZipEntry("");
			g.addVertex(root);
			
			
			FList<ZipEntry> entryList = new FList<ZipEntry>();
			while (entries.hasMoreElements()) {
				ZipEntry ze = entries.nextElement();
				entryList.add(ze);				
			}
			
			entryList.sort(new Comparator<ZipEntry>() {

				@Override
				public int compare(ZipEntry z1, ZipEntry z2) {
					return (z1.getName().compareTo(z2.getName()));
				}
			});
			
			entryList.each(new FnEach<ZipEntry>() {

				@Override
				public void f(ZipEntry z) {
					addChild(z);
				}
			});

				
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void addChild(ZipEntry child)
	{
		String name = child.getName();

		int lastSlash = name.substring(0, name.length() - 1).lastIndexOf("/");
		String parentName = (lastSlash == -1) ? "" : name.substring(0, lastSlash+1);

		ZipEntry parent = unwrap(getZippedFile(parentName));
				
		g.addVertex(child);
		g.addEdge(parent, child);
		
		
	}
	
	private ZippedFile wrap(ZipEntry z)
	{
		return new ZippedFile(z, zf);
	}
	
	private ZipEntry unwrap(ZippedFile zippedFile)
	{
		return zippedFile.getEntry();
	}
	
	public ZippedFile getZippedFile(String name) {
		
		if (name == "") return wrap(root);
		
		boolean isDir = name.lastIndexOf("/") == name.length()-1;
		
		String[] path = name.split("/");
		
		ZipEntry entry = root;
		for (int i = 0; i < path.length; i++){

			entry = unwrap(getChild(wrap(entry), path[i] + ((!isDir && i == path.length - 1) ? "" : "/")));
			if (entry == null) {
				System.out.println("null child");
			}
		}
		
		return wrap(entry);
		
	}
	
	public FList<ZippedFile> getChildren(ZippedFile z)
	{
		return Fn.map(g.outgoingEdgesOf(unwrap(z)), new FnMap<Object, ZippedFile>() {

			@Override
			public ZippedFile f(Object e) {
				return wrap(g.getEdgeTarget(e));
			}
		});
		
	}
	
	public ZippedFile getChild(final ZippedFile z, final String child) 
	{
		
		return Fn.filter(getChildren(z), new FnMap<ZippedFile, Boolean>() {

			@Override
			public Boolean f(ZippedFile zc) {
				String childname = unwrap(z).getName() + child;
				return unwrap(zc).getName().equals(childname);
			}
		}).head();
		
	}
	
	public boolean hasChildren(final ZippedFile z, final String child)
	{
		return (unwrap(getChild(z, child)) != null);
	}
	
	
	public static void main(String args[]){
		ZipFS t = new ZipFS("/home/nathaniel/Desktop/peakaboo_testing.jar");
		
		ZippedFile zhello = t.getZippedFile("license/LICENSE");
		BufferedReader r = zhello.getBufferedReader();
		try {
			System.out.println(r.readLine());
			System.out.println(r.readLine());
			System.out.println(r.readLine());
			System.out.println(r.readLine());
			System.out.println(r.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

