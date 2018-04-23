package com.yuriia.matcher;

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
    R value();
}