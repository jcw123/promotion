package com.im.sky.functional;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author jiangchangwei
 * @date 2020-1-6 下午 12:52
 **/
@FunctionalInterface
public interface ZBiFunction<T, U, R> {

    R apply(T t, U u);

    default <V> BiFunction<T, U, V> andThen(Function<R, V> function) {
        Objects.requireNonNull(function);
        return (t, u) -> function.apply(apply(t, u));
    }
}
