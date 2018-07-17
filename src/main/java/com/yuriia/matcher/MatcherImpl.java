package com.yuriia.matcher;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Matcher implementation. Implements all steps that are required to emulate Pattern matching.
 *
 * @author yuriia
 */
class MatcherImpl<T, R> implements Matcher<T, R>, WhereCaseStep<T, T, R>, EndStep<T, R> {

    /**
     * Collection of Match cases.
     */
    private final Deque<Case<T, R>> cases = new ArrayDeque<>();
    /**
     * Default value to use when specified value doesn't match any case.
     */
    private Supplier<R> defaultValue = () -> null;

    /**
     * No accessible outside this package. Use {@link Matcher.Builder} to create matcher instance.
     */
    MatcherImpl() {
    }

    @Override
    public R match(T value) {
        return cases.stream()
                .filter(matchCase -> matchCase.matches(value))
                .map(matchCase -> matchCase.map(value))
                .findFirst()
                .orElseGet(() -> Value.of(defaultValue))
                .getValue();
    }

    @Override
    public <C extends T> WhereCaseStep<T, C, R> when(Class<C> type) {
        return appendCase(value -> value != null && value.getClass().equals(type));
    }

    @Override
    public <C extends T> WhereCaseStep<T, C, R> when(Predicate<C> predicate) {
        return appendCase(predicate);
    }

    @Override
    public <C extends T> CaseStep<T, C, R> when(C constant) {
        return appendCase(value -> Objects.equals(value, constant));
    }

    @Override
    public <C extends T> WhereCaseStep<T, C, R> instanceOf(Class<C> type) {
        return appendCase(type::isInstance);
    }

    @Override
    public CaseStep<T, T, R> where(Predicate<T> predicate) {
        cases.peekLast().addWhen(predicate);
        return this;
    }

    @Override
    public EndStep<T, R> then(Function<T, R> mapper) {
        cases.peekLast().setCaseMapper(mapper);
        return this;
    }

    @Override
    public Matcher<T, R> orDefault(R value) {
        defaultValue = () -> value;
        return this;
    }

    @Override
    public Matcher<T, R> orDefault(Supplier<R> value) {
        defaultValue = Objects.requireNonNull(value);
        return this;
    }

    @Override
    public Matcher<T, R> orThrow(RuntimeException exception) {
        Objects.requireNonNull(exception);
        defaultValue = throwing(() -> exception);
        return this;
    }

    @Override
    public Matcher<T, R> orThrow(Supplier<? extends RuntimeException> exception) {
        defaultValue = throwing(Objects.requireNonNull(exception));
        return this;
    }

    /**
     * Convenience method to append new match case (with specified predicate).
     * Match expression will be specified by {@link #then(Function)} step.
     *
     * @param predicate - case predicate
     * @param <C>       - type of predicate value
     * @return where case step (with correct value type)
     */
    @SuppressWarnings("unchecked")
    private <C extends T> WhereCaseStep<T, C, R> appendCase(Predicate<C> predicate) {
        cases.add((Case<T, R>) new Case<>(predicate));
        return (WhereCaseStep<T, C, R>) this;
    }

    /**
     * Wrap given Exception supplier into R value supplier (and throw it on get).
     *
     * @param exception - exception supplier
     * @return value supplier that throws given exception
     */
    private Supplier<R> throwing(Supplier<? extends RuntimeException> exception) {
        return () -> {
            throw exception.get();
        };
    }

    /**
     * Data Class to emulate 'case' clause from 'switch' operator.
     * Fields are not final and mutable, but it is guaranteed by our fluent API that mutable operations
     * will not be called more that once.
     *
     * @author yuriia
     */
    private static class Case<T, R> {

        /**
         * Predicate to check if given mapper function should be executed to then result.
         */
        private Predicate<T> casePredicate;
        /**
         * Function to then result of the current Matcher.
         */
        private Function<T, R> mapper;

        /**
         * Create Match Case with given predicate. Mapper function will be applied with subsequent setCaseMapper call.
         *
         * @param casePredicate - match case
         */
        private Case(Predicate<T> casePredicate) {
            this.casePredicate = casePredicate;
        }

        /**
         * Test given value using specified predicate.
         *
         * @param value - value to test
         * @return true if current case matches with given value
         */
        boolean matches(T value) {
            return casePredicate.test(value);
        }

        /**
         * Map given value to the result of current Matcher.
         *
         * @param value - value to map
         * @return result of matching
         */
        Value<R> map(T value) {
            return new Value<>(mapper.apply(value));
        }

        /**
         * Set current mapping function. It when guaranteed that this method will be called exactly once (otherwise Matcher
         * will be not correctly constructed and terminal operation will not be executed).
         *
         * @param mapper - mapper function
         */
        void setCaseMapper(Function<T, R> mapper) {
            if (this.mapper != null) {
                throw new IllegalStateException("Mapper is already set");
            }
            this.mapper = mapper;
        }

        /**
         * Extend current predicate with additional when clause. It when guaranteed that this method will be called zero or
         * one time otherwise Matcher will be not correctly constructed and terminal operation will not be executed).
         *
         * @param when - additional predicate
         */
        void addWhen(Predicate<T> when) {
            casePredicate = casePredicate.and(when);
        }
    }

    /**
     * Data class to box a value. Used to allow null as match result - Otherwise Optional::orElseGet will be called which
     * will produce default value (which might be different from null - i.e. exception).
     *
     * @param <E> - actual value type
     */
    private static class Value<E> {

        /**
         * Boxed value. Can be null.
         */
        private E value;

        /**
         * Construct boxed value.
         *
         * @param value - value
         */
        private Value(E value) {
            this.value = value;
        }

        /**
         * Build value from supplier.
         *
         * @param valueSupplier - value supplier
         * @param <E>           - actual value type
         * @return boxed value
         */
        private static <E> Value<E> of(Supplier<E> valueSupplier) {
            return new Value<>(valueSupplier.get());
        }

        /**
         * @return boxed value
         */
        E getValue() {
            return value;
        }
    }
}
