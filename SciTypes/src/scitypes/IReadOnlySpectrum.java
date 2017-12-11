package scitypes;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class IReadOnlySpectrum implements ReadOnlySpectrum {

	ReadOnlySpectrum backer;
	
	public IReadOnlySpectrum(ReadOnlySpectrum backer) {
		this.backer = backer;
	}

	public float get(int i) {
		return backer.get(i);
	}

	public int size() {
		return backer.size();
	}

	public float[] backingArrayCopy() {
		return backer.backingArrayCopy();
	}

	public ReadOnlySpectrum subSpectrum(int start, int stop) {
		return backer.subSpectrum(start, stop);
	}

	public Stream<Float> stream() {
		return backer.stream();
	}

	public Iterator<Float> iterator() {
		return backer.iterator();
	}

	public void each(Consumer<Float> f) {
		backer.each(f);
	}

	public Float fold(BiFunction<Float, Float, Float> f) {
		return backer.fold(f);
	}

	public <T2> T2 fold(T2 base, BiFunction<Float, T2, T2> f) {
		return backer.fold(base, f);
	}

	public ReadOnlySpectrum zipWith(ISpectrum other, BiFunction<Float, Float, Float> f) {
		return backer.zipWith(other, f);
	}

	public int hashCode() {
		return backer.hashCode();
	}

	public boolean equals(Object oother) {
		return backer.equals(oother);
	}

	public String toString() {
		return backer.toString();
	}
	
	
	
}
