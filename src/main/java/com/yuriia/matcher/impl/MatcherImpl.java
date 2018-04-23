package com.yuriia.matcher.impl;

import com.yuriia.matcher.MatchStep;
import com.yuriia.matcher.Matcher;
import com.yuriia.matcher.MatcherPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * @author yuriia
 */
public class MatcherImpl<T, R> implements Matcher<T, R> {

    private T source;
    private List<Case<T, R>> cases = new ArrayList<>();
    private Supplier<R> valueSupplier;
    private AtomicReference<R> value = null;

    @Override
    public MatchStep<T, R> match(T source) {
        this.source = source;
        return new MatchStepImpl<>(this);
    }

    R get() {
        for (Case<T, R> matchCase : cases) {
            if (matchCase.matches(source)) {
                return matchCase.map(source);
            }
        }
        if (valueSupplier != null) {
            return valueSupplier.get();
        }
        if (value != null) {
            return value.get();
        }
        throw new IllegalArgumentException("Nothing matches");
    }
    
    <C extends T> Case<C, R> addCase(MatcherPredicate<C> matcher) {
        Case<C, R> matchCase = new Case<>(matcher);
        @SuppressWarnings("unchecked") Case<T, R> storedCase = (Case<T, R>) matchCase;
        cases.add(storedCase);
        return matchCase;
    }

    void setSource(T source) {
        this.source = source;
    }

    void setDefault(Supplier<R> valueSupplier) {
        this.valueSupplier = valueSupplier;
    }

    void setDefault(R value) {
        this.value = new AtomicReference<>(value);
    }
}
