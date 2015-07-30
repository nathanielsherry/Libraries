package fava.functionable;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class FStream<T> implements Stream<T> {

	private Stream<T> backer;

	public FStream(Stream<T> backer) {
		if (backer instanceof FStream) {
			this.backer = ((FStream) backer).backer;
		} else {
			this.backer = backer;
		}
	}
	
	public List<T> toSink() {
		return backer.collect(Collectors.toList());
	}
	
	public Iterator<T> iterator() {
		return backer.iterator();
	}

	public Spliterator<T> spliterator() {
		return backer.spliterator();
	}

	public boolean isParallel() {
		return backer.isParallel();
	}

	public Stream<T> sequential() {
		return new FStream<T>(backer.sequential());
	}

	public Stream<T> parallel() {
		return new FStream<T>( backer.parallel() );
	}

	public Stream<T> unordered() {
		return new FStream<T>( backer.unordered() );
	}

	public Stream<T> onClose(Runnable closeHandler) {
		return new FStream<T>( backer.onClose(closeHandler) );
	}

	public void close() {
		backer.close();
	}

	public Stream<T> filter(Predicate<? super T> predicate) {
		return new FStream<T>( backer.filter(predicate) );
	}

	public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
		return new FStream<R>( backer.map(mapper) );
	}

	public IntStream mapToInt(ToIntFunction<? super T> mapper) {
		return backer.mapToInt(mapper);
	}

	public LongStream mapToLong(ToLongFunction<? super T> mapper) {
		return backer.mapToLong(mapper);
	}

	public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
		return backer.mapToDouble(mapper);
	}

	public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
		return new FStream<R>( backer.flatMap(mapper) );
	}

	public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
		return backer.flatMapToInt(mapper);
	}

	public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
		return backer.flatMapToLong(mapper);
	}

	public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
		return backer.flatMapToDouble(mapper);
	}

	public Stream<T> distinct() {
		return new FStream<T>( backer.distinct() );
	}

	public Stream<T> sorted() {
		return new FStream<T>( backer.sorted() );
	}

	public Stream<T> sorted(Comparator<? super T> comparator) {
		return new FStream<T>( backer.sorted(comparator) );
	}

	public Stream<T> peek(Consumer<? super T> action) {
		return new FStream<T>( backer.peek(action) );
	}

	public Stream<T> limit(long maxSize) {
		return new FStream<T>( backer.limit(maxSize) );
	}

	public Stream<T> skip(long n) {
		return new FStream<T>( backer.skip(n) );
	}

	public void forEach(Consumer<? super T> action) {
		backer.forEach(action);
	}

	public void forEachOrdered(Consumer<? super T> action) {
		backer.forEachOrdered(action);
	}

	public Object[] toArray() {
		return backer.toArray();
	}

	public <A> A[] toArray(IntFunction<A[]> generator) {
		return backer.toArray(generator);
	}

	public T reduce(T identity, BinaryOperator<T> accumulator) {
		return backer.reduce(identity, accumulator);
	}

	public Optional<T> reduce(BinaryOperator<T> accumulator) {
		return backer.reduce(accumulator);
	}

	public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
		return backer.reduce(identity, accumulator, combiner);
	}

	public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
		return backer.collect(supplier, accumulator, combiner);
	}

	public <R, A> R collect(Collector<? super T, A, R> collector) {
		return backer.collect(collector);
	}

	public Optional<T> min(Comparator<? super T> comparator) {
		return backer.min(comparator);
	}

	public Optional<T> max(Comparator<? super T> comparator) {
		return backer.max(comparator);
	}

	public long count() {
		return backer.count();
	}

	public boolean anyMatch(Predicate<? super T> predicate) {
		return backer.anyMatch(predicate);
	}

	public boolean allMatch(Predicate<? super T> predicate) {
		return backer.allMatch(predicate);
	}

	public boolean noneMatch(Predicate<? super T> predicate) {
		return backer.noneMatch(predicate);
	}

	public Optional<T> findFirst() {
		return backer.findFirst();
	}

	public Optional<T> findAny() {
		return backer.findAny();
	}
		
}