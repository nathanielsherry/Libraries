package scratch.encoders.compressors;

import java.awt.Color;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4SafeDecompressor;
import scitypes.ISpectrum;
import scitypes.SparsedList;
import scitypes.Spectrum;
import scratch.ScratchEncoder;
import scratch.ScratchList;

/**
 * SpectrumList is an implementation of the List interface which writes 
 * out values to a temporary file, rather than storing elements in memory.
 * This is useful for lists which are sufficiently large to cause memory
 * concerns in a typical JVM.
 * <br /><br /> 
 * Storing elements on disk means that get operations will return copies 
 * of the objects in the list rather than the originally stored objects. 
 * If an element is retrieved from the list, modified, and then 
 * retrieved a second time, the second copy retrieved will lack the 
 * modifications made to the first copy.
 * 
 * To create a new SpectrumList, call {@link LZ4CompressionEncoder#create(String)}.
 * If the SpectrumList cannot be created for whatever reason, a memory-based
 * list will be created instead.
 * <br/><br/>
 * Note that this class depends on the specific implementation of Spectrum
 * being {@link ISpectrum} 
 * @author Nathaniel Sherry, 2011-2012
 *
 */

public class LZ4CompressionEncoder implements ScratchEncoder<byte[]>{

	private int maxSize = 2<<16; //16k default max entry size
	private LZ4Compressor compressor = LZ4Factory.fastestInstance().fastCompressor(); 
	private LZ4SafeDecompressor decompressor = LZ4Factory.fastestInstance().safeDecompressor();
	
	public LZ4CompressionEncoder() {}
	public LZ4CompressionEncoder(int maxSize) {
		this.maxSize = maxSize;
	}

	
	public byte[] encode(byte[] input) {
		if (input.length > maxSize) {
			synchronized (this) {
				maxSize = Math.max(input.length, maxSize);	
			}
		}
		
		return compressor.compress(input);
	}

	public byte[] decode(byte[] input) {
		return decompressor.decompress(input, maxSize);
	}

	
}
