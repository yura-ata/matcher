package com.yuriia.matcher.impl;

import com.yuriia.matcher.MatchStep;
import com.yuriia.matcher.Matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Matcher implementation. Holds Matcher state.
 * Default value OR exception can be specified to use when nothing matches.
 *
 * @author yuriia
 */
public class MatcherImpl<T, R> implements Matcher<T, R> {

    /**
     * Value to match (can be null).
     */
    private T value;
    /**
     * Collection of Match cases.
     */
    private List<Case<T, R>> cases = new ArrayList<>();
    /**
     * Default value (or exception) supplier.
     */
    private Supplier<R> defaultValue = () -> { throw new IllegalArgumentException("Nothing matches"); };

    @Override
    public MatchStep<T, R> match(T source) {
        this.value = source;
        return new MatchOrEndStepImpl<>(this);
    }

    /**
     * @return matched value.
     */
    R get() {
        return cases.stream()
                .filter(c -> c.matches(value))
                .map(c -> c.map(value))
                .findFirst()
                .orElseGet(defaultValue);
    }

    /**
     * Append match case with given predicate.
     *
     * @param predicate - predicate to match values with
     * @param <C>       - actual type of the value
     * @return match case
     */
    @SuppressWarnings("unchecked")
    <C extends T> Case<C, R> addCase(Predicate<C> predicate) {
        Case<C, R> matchCase = new Case<>(predicate);
        // it is guaranteed to call case[C, R] only when value is C (and not just T)
        cases.add((Case<T, R>) matchCase);
        return matchCase;
    }

    /**
     * Specify supplier for the default value when nothing matches.
     *
     * @param value - default value supplier.
     */
    void setDefault(Supplier<R> value) {
        this.defaultValue = value;
    }

    /**
     * Specify default value when nothing matches.
     *
     * @param value - default value (can be null).
     */
    void setDefault(R value) {
        this.defaultValue = () -> value;
    }

    /**
     * Specify supplier for exception to throw when nothing matches.
     *
     * @param error - unchecked exception supplier
     */
    void setThrowable(Supplier<? extends RuntimeException> error) {
        this.defaultValue = () -> { throw error.get(); };
    }

    /**
     * Specify exception to throw when nothing matches.
     *
     * @param error - unchecked exception
     */
    void setThrowable(RuntimeException error) {
        this.defaultValue = () -> { throw error; };
    }
}
