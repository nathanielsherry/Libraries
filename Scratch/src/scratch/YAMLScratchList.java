package scratch;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.util.List;

import org.yaml.snakeyaml.Yaml;

import fava.datatypes.Pair;
import fava.functionable.FList;


public class YAMLScratchList<V> extends AbstractScratchList<V> {

	

	public static void main(String args[])
	{
		Pair<Integer, Integer> s1 = new Pair<Integer, Integer>(1, 500);
		Pair<Integer, Integer> s2 = new Pair<Integer, Integer>(2, 1);
		Pair<Integer, Integer> s3 = new Pair<Integer, Integer>(3, 7142);
		Pair<Integer, Integer> s4 = new Pair<Integer, Integer>(4, 9);
		Pair<Integer, Integer> s5 = new Pair<Integer, Integer>(5, 20);
		
		try {
			
			YAMLScratchList<Pair<Integer, Integer>> list = new YAMLScratchList<Pair<Integer, Integer>>("test");
			
			list.add(s1);
			list.add(s5);
			list.add(s2);
			
			list.set(0, s3);
			list.set(2, s4);
			
			list.add(s5);
			list.add(s4);
			
			list.set(1, s4);
			
			for (int i = 0; i < 100; i++) list.set(3, s5);
			
			System.out.println(list.get(0).show());
			System.out.println(list.get(2).show());
			System.out.println(list.get(3).show());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static <T extends Serializable> List<T> create(String name)
	{
		try
		{
			return new YAMLScratchList<T>(name);
		}
		catch (IOException e)
		{
			//FList can also behave sparsely
			e.printStackTrace();
			return new FList<T>();
		}
	}
	
	
	private Yaml y;
	
	public YAMLScratchList(File file) throws IOException {
		super(file);
		y = new Yaml();
	}
	
	public YAMLScratchList(String name) throws IOException {
		super(name);
		y = new Yaml();
	}

	private YAMLScratchList()
	{
		y = new Yaml();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	protected V decodeObject(byte[] byteArray) throws IOException {
		String yamltext = new String(byteArray);
		System.out.println(yamltext);
		return (V)y.load(yamltext);
	}

	@Override
	protected byte[] encodeObject(V element) throws IOException {
		Yaml y = new Yaml();
		return y.dump(element).getBytes();
	}

	@Override
	public List<V> subList(int fromIndex, int toIndex) {
		YAMLScratchList<V> sublist = new YAMLScratchList<V>();
		this.makeSublist(sublist, fromIndex, toIndex);
		return sublist;
	}
	


}
