package com.yuriia.matcher;

import java.util.function.Supplier;

/**
 * Terminal step of the Matcher.
 *
 * @author yuriia
 */
public interface EndStep<R> {

    /**
     * Get matched value.
     *
     * @return result of the pattern matching
     */
    R get();

    /**
     * Provide default value when current get does not match any of the specified cases.
     *
     * @param value - default value
     * @return terminal step
     */
    R orGet(R value);

    /**
     * Provide supplier for the default value when current get does not match any of the specified cases.
     *
     * @param value - supplier to lazily get default value
     * @return terminal step
     */
    R orGet(Supplier<R> value);

    /**
     * Provide exception to throw when current value does not match any of the specified cases.
     *
     * @param exception - unchecked exception
     * @return terminal step
     */
    R orThrow(RuntimeException exception);

    /**
     * Provide supplier for the exception to throw when current value does not match any of the specified cases.
     *
     * @param exception - supplier to lazily get unchecked exception
     * @return terminal step
     */
    R orThrow(Supplier<? extends RuntimeException> exception);
}