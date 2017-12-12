package fava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import fava.functionable.FList;

public class FCollector<T> implements Collector<T, FList<T>, FList<T>> {


	@Override
	public Supplier<FList<T>> supplier() {
		return () -> FList.wrap(Collections.synchronizedList(new ArrayList<>()));
	}

	@Override
	public BiConsumer<FList<T>, T> accumulator() {
		return (l, t) -> l.add(t);
	}

	@Override
	public BinaryOperator<FList<T>> combiner() {
		return (l1, l2) -> { l1.addAll(l2); return l1; };
	}

	@Override
	public Function<FList<T>, FList<T>> finisher() {
		return a -> a;
	}

	@Override
	public Set<java.util.stream.Collector.Characteristics> characteristics() {
		Set<Characteristics> c = new HashSet<>();
		c.add(Characteristics.CONCURRENT);
		c.add(Characteristics.IDENTITY_FINISH);
		
		return c;
		 
	}

	
	
}
