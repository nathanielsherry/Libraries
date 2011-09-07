package fava.wip;

import java.util.Collection;
import java.util.Map.Entry;

import fava.Functions;
import fava.functionable.FList;
import fava.functionable.Functionable;
import fava.functionable.Range;
import fava.signatures.FnMap;

public class Testing {

	public static void main(String[] args) {
		
		FSet<Integer> set = new FSet<Integer>();
		set.add(1);
		set.add(2);
		set.add(3);
		
		Functionable<Integer> newset = set.map(Functions.<Integer>id());
		
		System.out.println(newset.getClass());
		
		
		
	}
	
	
}
