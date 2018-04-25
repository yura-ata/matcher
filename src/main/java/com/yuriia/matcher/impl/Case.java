package com.yuriia.matcher.impl;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Data Class to emulate 'case' clause from 'switch' operator.
 * Fields are not final and mutable, but it is guaranteed by our fluent API that mutable operations
 * will not be called more that once.
 *
 * @author yuriia
 */
class Case<T, R> {

    /**
     * Predicate to check if given mapper function should be executed to get result.
     */
    private Predicate<T> casePredicate;
    /**
     * Function to get result of the current Matcher.
     */
    private Function<T, R> mapper;

    Case(Predicate<T> casePredicate) {
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
    R map(T value) {
        return this.mapper.apply(value);
    }

    /**
     * Set current mapping function. It is guaranteed that this method will be called exactly once (otherwise Matcher
     * will be not correctly constructed and terminal operation will not be executed).
     *
     * @param mapper - mapper function
     */
    void setCaseMapper(Function<T, R> mapper) {
        this.mapper = mapper;
    }

    /**
     * Extend current predicate with additional when clause. It is guaranteed that this method will be called zero or
     * one time otherwise Matcher will be not correctly constructed and terminal operation will not be executed).
     *
     * @param when - additional predicate
     */
    void addWhen(Predicate<T> when) {
        this.casePredicate = casePredicate.and(when);
    }
}
