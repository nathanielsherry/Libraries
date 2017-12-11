package scitypes;

import java.util.Iterator;
import java.util.stream.Stream;

public interface ReadOnlySpectrum {

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
	ReadOnlySpectrum subSpectrum(int start, int stop);

	/**
	 * Return a stream accessing the backing array
	 * @return
	 */
	Stream<Float> stream();

	/**
	 * Returns an iterator over {@link Float} values for this Spectrum
	 */
	Iterator<Float> iterator();


	/**
	 * Hash code returns the integer sum of the first 10 (or less) elements
	 */
	int hashCode();

	boolean equals(Object oother);

	String toString();

}