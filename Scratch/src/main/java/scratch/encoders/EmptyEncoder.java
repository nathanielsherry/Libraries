package scratch.encoders;

import scratch.ScratchEncoder;
import scratch.ScratchException;

public class EmptyEncoder<T> implements ScratchEncoder<T> {

	@Override
	public byte[] encode(T data) throws ScratchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T decode(byte[] data) throws ScratchException {
		// TODO Auto-generated method stub
		return null;
	}

}
