package com.yuriia.matcher;

import java.util.function.Supplier;

/**
 * Step to specify default value to return (or to throw) when given value does not match with any of the given cases.
 *
 * @author yuriia
 */
public interface DefaultValueStep<T, R> {

    /**
     * Provide default value when current then does not match any of the specified cases.
     *
     * @param value - default value
     * @return terminal step
     */
    Matcher<T, R> orDefault(R value);

    /**
     * Provide supplier for the default value when current then does not match any of the specified cases.
     *
     * @param value - supplier to lazily then default value
     * @return terminal step
     */
    Matcher<T, R> orDefault(Supplier<R> value);

    /**
     * Provide exception to throw when current value does not match any of the specified cases.
     *
     * @param exception - unchecked exception
     * @return terminal step
     */
    Matcher<T, R> orThrow(RuntimeException exception);

    /**
     * Provide supplier for the exception to throw when current value does not match any of the specified cases.
     *
     * @param exception - supplier to lazily then unchecked exception
     * @return terminal step
     */
    Matcher<T, R> orThrow(Supplier<? extends RuntimeException> exception);
}
