package wtf.pants.bindings.util;

import java.util.function.BiFunction;

public class Functional {

    public static <A, B> A foldLeft(final B[] array, final A startValue, final BiFunction<A, B, A> func) {
        A lastValue = startValue;

        for (B b : array) {
            lastValue = func.apply(lastValue, b);
        }

        return lastValue;
    }

}
