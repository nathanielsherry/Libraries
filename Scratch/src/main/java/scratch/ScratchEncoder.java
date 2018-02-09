package scratch;

public interface ScratchEncoder<T> {

	byte[] encode(T data) throws ScratchException;
	T decode(byte[] data) throws ScratchException;
	
}
