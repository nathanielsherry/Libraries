package scratch.single;

import java.io.Serializable;

import scratch.ScratchEncoder;
import scratch.encoders.CompoundEncoder;
import scratch.encoders.DeflateCompressionEncoder;
import scratch.encoders.SerializingEncoder;

public class Compressed<T> {

	private byte[] data;
	private ScratchEncoder<T> encoder;
	
	public static <T extends Serializable> Compressed<T> create(T value) {
		return create(value, new CompoundEncoder<T>(new SerializingEncoder<>(), new DeflateCompressionEncoder()));
	}
	
	public static <T> Compressed<T> create(T value, ScratchEncoder<T> encoder) {
		Compressed<T> c = new Compressed<>();
		c.data = encoder.encode(value);
		c.encoder = encoder;
		return c;
	}
	public T get() {
		return this.encoder.decode(this.data);
	}
	
}
