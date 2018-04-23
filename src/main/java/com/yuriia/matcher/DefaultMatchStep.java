package com.yuriia.matcher;

import java.util.function.Supplier;

/**
 * Step to provide default value (or exception to throw) when nothing matches.
 * It is guaranteed that only one of this methods can be called (and only once).
 * TODO: or refactor to return R instead of EndStep
 *
 * @param <R>
 */
public interface DefaultMatchStep<R> {

    /**
     * Provide default value when current value does not match any of the specified cases.
     *
     * @param value - default value
     * @return terminal step
     */
    EndStep<R> orGet(R value);

    /**
     * Provide supplier for the default value when current value does not match any of the specified cases.
     *
     * @param value - supplier to lazily get default value
     * @return terminal step
     */
    EndStep<R> orGet(Supplier<R> value);

    /**
     * Provide exception to throw when current value does not match any of the specified cases.
     *
     * @param exception - unchecked exception
     * @return terminal step
     */
    EndStep<R> orThrow(RuntimeException exception);

    /**
     * Provide supplier for the exception to throw when current value does not match any of the specified cases.
     *
     * @param exception - supplier to lazily get unchecked exception
     * @return terminal step
     */
    EndStep<R> orThrow(Supplier<? extends RuntimeException> exception);
}
