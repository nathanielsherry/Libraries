package scratch.list;

import java.util.List;

import scratch.ScratchEncoder;

public interface IScratchList<T> extends List<T>{

	ScratchEncoder<T> getEncoder();
	void setEncoder(ScratchEncoder<T> encoder);
	
}
