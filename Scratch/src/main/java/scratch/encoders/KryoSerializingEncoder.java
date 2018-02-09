package scratch.encoders;

import java.awt.Color;
import java.io.Serializable;
import java.nio.ByteBuffer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ObjectBuffer;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serialize.SimpleSerializer;

import scratch.ScratchEncoder;
import scratch.ScratchException;

public class KryoSerializingEncoder<T extends Serializable> implements ScratchEncoder<T>{

	private Kryo kryo;
	private ObjectBuffer kryoBuffer;
	private Class<? extends T> clazz;
	
	public KryoSerializingEncoder(Class<? extends T> clazz, Class<?>... others) {
		this.clazz = clazz;
		registerKryoClass(clazz);
		for (Class<?> other : others) {
			registerKryoClass(other);
		}
	}
	
	public void registerKryoClass(Class<?> c)
	{
		getKryo().register(c);
	}
	
	public <S> void registerKryoSerializer(Class<S> c, SimpleSerializer<S> serializer) {
		getKryo().register(c, serializer);
	}
	
	private Kryo getKryo()
	{
		
		if (kryo != null) return kryo;
		
		kryo = new Kryo();
		kryo.setRegistrationOptional(true);
		
		kryo.register(Color.class, new SimpleSerializer<Color>() {
	        public void write (ByteBuffer buffer, Color color) {
	                buffer.putInt(color.getRGB());
	        }
	        public Color read (ByteBuffer buffer) {
	                return new Color(buffer.getInt());
	        }
		});
		
		return kryo;
	}
	
	private ObjectBuffer getKryoBuffer()
	{
		if (kryoBuffer != null) return kryoBuffer;
		kryoBuffer = new ObjectBuffer(getKryo(), 1000 << 16);
		return kryoBuffer;
	}
	
	
	@Override
	public byte[] encode(T data) throws ScratchException {
		ObjectBuffer buffer = getKryoBuffer();
		return buffer.writeObject(data);
	}

	@Override
	public T decode(byte[] data) throws ScratchException {
		ObjectBuffer buffer = getKryoBuffer();
		return buffer.readObject(data, clazz);
	}

	
	
}
