package com.yuriia.matcher;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author yuriia
 */
@FunctionalInterface
public interface MatcherPredicate<T> extends Predicate<T> {

    default boolean isConstant() {
        return false;
    }
    
    default MatcherPredicate<T> and(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) && other.test(t);
    }
}
