package com.yuriia.matcher.steps;

import com.yuriia.matcher.CompilableMatcher;
import com.yuriia.matcher.Step;

import java.util.function.Supplier;

/**
 * Step to provide default value (or exception to throw) when nothing matches.
 * It is guaranteed that only one of this methods can be called (and only once).
 *
 * @param <R>
 */
public interface DefaultCaseStep<T, R> extends Step {

    /**
     * Provide default value when current value does not match any of the specified cases.
     *
     * @param value - default value
     * @return terminal step
     */
    CompilableMatcher<T, R> orGet(R value);

    /**
     * Provide supplier for the default value when current value does not match any of the specified cases.
     *
     * @param value - supplier to lazily get default value
     * @return terminal step
     */
    CompilableMatcher<T, R> orGet(Supplier<R> value);

    /**
     * Provide exception to throw when current value does not match any of the specified cases.
     *
     * @param exception - unchecked exception
     * @return terminal step
     */
    CompilableMatcher<T, R> orThrow(RuntimeException exception);

    /**
     * Provide supplier for the exception to throw when current value does not match any of the specified cases.
     *
     * @param exception - supplier to lazily get unchecked exception
     * @return terminal step
     */
    CompilableMatcher<T, R> orThrow(Supplier<? extends RuntimeException> exception);
}
