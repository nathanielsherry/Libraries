package scratch;

import java.util.List;

public interface IScratchList<T> extends List<T>{

	ScratchEncoder<T> getEncoder();
	void setEncoder(ScratchEncoder<T> encoder);
	
}
