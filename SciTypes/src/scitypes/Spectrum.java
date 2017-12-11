package scitypes;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Spectrum extends Serializable, Iterable<Float> {

	/**
	 * Copies the values from the given spectrum into this one. 
	 * Values copied will be in the range of 0 .. min(size(), s.size())
	 * @param s
	 */
	void copy(Spectrum s);

	/**
	 * Adds a value to the Spectrum.  When a new spectrum is created 
	 * without being initialized with any values, it can have <tt>size</tt> 
	 * new values added to it. These values are added in order, just 
	 * as with {@link List#add(Object)}. The add method will return true
	 * if any new value was added to the spectrum, or false if there was
	 * no more space available. Calling {@link ISpectrum#set(int, float)}
	 * does not have any effect on the add method
	 * @param value
	 */
	boolean add(float value);

	/**
	 * Sets the value of the entry at index i.
	 * @param i
	 * @param value
	 */
	void set(int i, float value);

	/**
	 * Gets the value of the entry at index i
	 * @param i
	 */
	float get(int i);

	/**
	 * Gets the size of this Spectrum
	 */
	int size();

	/**
	 * Returns a copy of the data as an array
	 */
	float[] backingArrayCopy();

	/**
	 * Returns a new Spectrum containing a copy of the data for a subsection of this spectrum.
	 * @param start
	 * @param stop
	 */
	Spectrum subSpectrum(int start, int stop);

	/**
	 * Return the array which is backing this Spectrum. This method does not return a copy, 
	 * but the real array. Modifying the contents of this array will modify the contents 
	 * of the Spectrum.
	 */
	float[] backingArray();

	/**
	 * Return a stream accessing the backing array
	 * @return
	 */
	Stream<Float> stream();

	/**
	 * Returns an iterator over {@link Float} values for this Spectrum
	 */
	Iterator<Float> iterator();

	void each(Consumer<Float> f);

	void map_i(Function<Float, Float> f);

	Float fold(BiFunction<Float, Float, Float> f);

	<T2> T2 fold(T2 base, BiFunction<Float, T2, T2> f);

	Spectrum zipWith(ISpectrum other, BiFunction<Float, Float, Float> f);

	/**
	 * Hash code returns the integer sum of the first 10 (or less) elements
	 */
	int hashCode();

	boolean equals(Object oother);

	String toString();

}