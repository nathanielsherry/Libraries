package net.sciencestudio.chanje;

import java.util.function.Consumer;

public class ChangeUtil {

    public static <T> void when(Class<T> cls, Object event, Runnable runnable) {

        if (cls.isInstance(event)) {
            runnable.run();
        }
    }

    public static <T> void when(Class<T> cls, Object event, Consumer<T> consumer) {

        if (cls.isInstance(event)) {
            consumer.accept((T)event);
        }
    }
	
}
