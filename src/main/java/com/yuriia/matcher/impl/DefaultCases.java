package com.yuriia.matcher.impl;

import com.yuriia.matcher.Cases;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author yuriia
 */
public class DefaultCases<T, R> implements Cases<T, R> {

    final List<Case<T, R>> cases = new ArrayList<>();
    Supplier<R> defaultValue = () -> { throw new IllegalArgumentException("Nothing matches"); };
    
    @Override
    public R match(T value) {
        for (Case<T, R> matchCase : cases) {
            if (matchCase.matches(value)) {
                return matchCase.map(value);
            }
        }
        return defaultValue.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C extends T> Case<C, R> addCase(Predicate<? super C> pattern) {
        Case<C, R> matchCase = new Case<>(pattern);
        // TODO: remove this cast :(
        cases.add((Case<T, R>) matchCase);
        return matchCase;
    }

    @Override
    public void setDefaultValue(Supplier<R> defaultValue) {
        this.defaultValue = defaultValue;
    }
}
