package com.yuriia.matcher;

import java.util.function.Function;

/**
 * Case step. Similar to the expression after 'case' keyword in the 'switch' statement.
 *
 * @param <T> - type of the matching value
 * @param <C> - actual type of the matching value the current case. Example:
 *            {@code .match(object).with(String.class)} - now C when String instead of just Object
 * @author yuriia
 */
public interface CaseStep<T, C extends T, R> {

    /**
     * When given value matches matches to the current case - use it to compute result of the the current matcher.
     *
     * @param mapper - function to map matched value into result
     * @return next step (terminal step or more cases)
     */
    EndStep<T, R> then(Function<C, R> mapper);
}