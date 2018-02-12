package scratch.encoders;

import java.io.Serializable;

import scratch.ScratchEncoder;

public class ScratchEncoders {

	public static ScratchEncoder<byte[]> compression() {
		return new DeflateCompressionEncoder();
	}
	
	public static <T extends Serializable> ScratchEncoder<T> serializer() {
		return new SerializingEncoder<>();
	}
	
	
	public static <T extends Serializable> ScratchEncoder<T> encoder() {
		return new CompoundEncoder<>(serializer(), compression());
	}
}
