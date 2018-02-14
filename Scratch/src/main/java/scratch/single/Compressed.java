package scratch.single;

import java.io.Serializable;

import scratch.ScratchEncoder;
import scratch.encoders.CompoundEncoder;
import scratch.encoders.compressors.Compressors;
import scratch.encoders.compressors.DeflateCompressionEncoder;
import scratch.encoders.serializers.Serializers;
import scratch.encoders.serializers.SerializingEncoder;

public class Compressed<T> implements ByteStorage<T> {

	byte[] data;
	ScratchEncoder<T> encoder;
	
	public static <T extends Serializable> Compressed<T> create(T value) {
		return create(value, new CompoundEncoder<T>(Serializers.java(), Compressors.deflate()));
	}
	
	public static <T> Compressed<T> create(T value, ScratchEncoder<T> encoder) {
		Compressed<T> c = new Compressed<>();
		c.data = encoder.encode(value);
		c.encoder = encoder;
		return c;
	}
	
	@Override
	public T get() {
		return this.encoder.decode(this.data);
	}
	
	@Override
	public byte[] getBytes() {
		return data;
	}

	@Override
	public ScratchEncoder<T> getEncoder() {
		return encoder;
	}
	
}
