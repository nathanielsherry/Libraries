package commonenvironment.zipfs;


import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import fava.functionable.FList;
import fava.signatures.FnCondition;
import fava.signatures.FnEach;
import fava.signatures.FnMap;
import fava.wip.FSet;


public class ZipFS {

	private ZippedFile root;
	private DirectedGraph<ZippedFile, Object> g;
	private ZipFile zf;
	
	public ZipFS(String filename) {
		this(new File(filename));
	}
	
	public ZipFS(File file) {
	
		
		try {
			
			zf = new ZipFile(file);
			
			Enumeration<? extends ZipEntry> entries = zf.entries();
			
			
			g = new SimpleDirectedGraph<ZippedFile, Object>(Object.class);
			
			
			root = wrap(new ZipEntry(""));
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
					addChild(wrap(z));
				}
			});

				
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void addChild(ZippedFile child)
	{
		String name = child.getName();

		int lastSlash = name.substring(0, name.length() - 1).lastIndexOf("/");
		String parentName = (lastSlash == -1) ? "" : name.substring(0, lastSlash+1);

		//get the parent for this entry
		ZippedFile parent = getZippedFile(parentName);
		if (parent == null) {
			parent = new ZippedFile(parentName);
			addChild(parent);
		}
				
		g.addVertex(child);
		g.addEdge(parent, child);
		
	}
	
	private ZippedFile wrap(ZipEntry z)
	{
		return new ZippedFile(z, zf);
	}
	
	private ZipEntry unwrap(ZippedFile zippedFile)
	{
		if (zippedFile == null) return null;
		return zippedFile.getEntry();
	}
	
	public ZippedFile getZippedFile(String name) {
		
		if (name == "") return root;
		
		boolean isDir = name.lastIndexOf("/") == name.length()-1;
		
		String[] path = name.split("/");
		
		ZippedFile entry = root;
		for (int i = 0; i < path.length; i++){

			entry = getChild(entry, path[i] + ((!isDir && i == path.length - 1) ? "" : "/"));
			if (entry == null) {
				return null;
			}
		}
		
		return entry;
		
	}
	
	public FList<ZippedFile> getChildren(ZippedFile z)
	{
		return FSet.wrap(g.outgoingEdgesOf(z)).map(new FnMap<Object, ZippedFile>() {

			@Override
			public ZippedFile f(Object e) {
				return g.getEdgeTarget(e);
			}
		}).toSink();
		
	}
	
	public ZippedFile getChild(final ZippedFile z, final String child) 
	{
		
		return getChildren(z).filter(new FnCondition<ZippedFile>() {

			@Override
			public Boolean f(ZippedFile zc) {
				String childname = z.getName() + child;
				return zc.getName().equals(childname);
			}
		}).head();
		
	}
	
	public boolean hasChildren(final ZippedFile z, final String child)
	{
		return (unwrap(getChild(z, child)) != null);
	}
	
	public FList<ZippedFile> getAllFiles()
	{
		return new FList<ZippedFile>(g.vertexSet());
	}
	
	
}

