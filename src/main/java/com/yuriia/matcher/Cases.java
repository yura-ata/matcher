package com.yuriia.matcher;

import com.yuriia.matcher.impl.Case;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author yuriia
 */
public interface Cases<T, R> {

    R match(T value);
    
    <C extends T> Case<C, R> addCase(Predicate<? super C> pattern);

    void setDefaultValue(Supplier<R> defaultValue);
}
