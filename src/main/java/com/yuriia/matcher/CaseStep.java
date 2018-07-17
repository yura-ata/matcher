package com.yuriia.matcher;

import java.util.function.Function;

/**
 * Case step. Allows to specify expression (function) to execute when specified value matches current pattern (case).
 * Similar to the expression after 'case' keyword in the 'switch' statement.
 *
 * @param <T> - type of the matching value
 * @param <C> - actual type of the matching value the current case. <br/>
 *            Example: {@code .match(object).when(String.class).then(...)} - now C is String instead of just Object
 * @param <R> - type of matcher result
 * @author yuriia
 */
public interface CaseStep<T, C extends T, R> {

    /**
     * When given value matches matches to the current case - use given function to compute result of the the current matcher.
     * Note that type of function input is specified by case predicate (and may differ from type of object that we match).
     *
     * @param mapper - function to map matched value into result.
     * @return next step (terminal step or more cases)
     */
    EndStep<T, R> then(Function<C, R> mapper);
}