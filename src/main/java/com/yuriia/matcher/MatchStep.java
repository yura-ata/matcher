package com.yuriia.matcher;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author yuriia
 */
public interface MatchStep<T, R> extends EndStep<T, R> {

    <C extends T> WhereCaseStep<T, C, R> with(Class<C> type);

    <C extends T> WhereCaseStep<T, C, R> with(Predicate<? super C> predicate);

    EndStep<T, R> defaultCase(Supplier<R> value);

    EndStep<T, R> defaultCase(R value);
}
