package net.sciencestudio.chanje;

import java.util.function.Consumer;

public interface ChangeSource {

    void listen(Consumer<Object> l);

    default <T> void listen(Class<T> cls, Runnable l) {
        listen(change -> ChangeUtil.when(cls, change, l));
    }

    default <T> void listen(Class<T> cls, Consumer<T> l) {
        listen(change -> ChangeUtil.when(cls, change, l));
    }

    void unlisten(final Consumer<Object> l);
	
}
