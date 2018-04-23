package com.yuriia.matcher.impl;

import com.yuriia.matcher.MatchStep;
import com.yuriia.matcher.Matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private T source;
    /**
     * Collection of Match cases.
     * TODO: add optimization to replace list with Map when all (most?) cases are ConstantPredicates
     */
    private List<Case<T, R>> cases = new ArrayList<>();
    /**
     * Optional supplier to provide default value when all cases doesn't match.
     */
    private Supplier<R> defaultValue;
    /**
     * Supplier to provide unchecked exception to throw when all cases doesn't match.
     */
    private Supplier<? extends RuntimeException> error = () -> new IllegalArgumentException("Nothing matches");

    @Override
    public MatchStep<T, R> match(T source) {
        this.source = source;
        return new MatchOrEndStepImpl<>(this);
    }

    /**
     * @return matched value.
     */
    R get() {
        for (Case<T, R> matchCase : cases) {
            if (matchCase.matches(source)) {
                return matchCase.map(source);
            }
        }
        if (defaultValue != null) {
            return defaultValue.get();
        }
        throw error.get();
    }

    /**
     * Append match case with given predicate.
     *
     * @param predicate - predicate to match values with
     * @param <C>       - actual type of the value
     * @return match case
     */
    @SuppressWarnings("unchecked")
    <C extends T> Case<C, R> addCase(Predicate<? super C> predicate) {
        Case<C, R> matchCase = new Case<>(predicate);
        // TODO: remove this cast :(
        cases.add((Case<T, R>) matchCase);
        return matchCase;
    }

    /**
     * Specify supplier for the default value when nothing matches.
     *
     * @param value - default value supplier.
     */
    void setDefault(Supplier<R> value) {
        this.defaultValue = Objects.requireNonNull(value);
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
        this.error = Objects.requireNonNull(error);
    }

    /**
     * Specify exception to throw when nothing matches.
     *
     * @param error - unchecked exception
     */
    void setThrowable(RuntimeException error) {
        Objects.requireNonNull(error);
        this.error = () -> error;
    }
}
