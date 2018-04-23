package com.yuriia.matcher;

/**
 * Marker interface to specify that current step is either MatchStep or EndStep (union type).
 *
 * @param <T> - type of the value being matched
 * @param <R> - type of the result value
 * @author yuriia
 */
public interface MatchOrEndStep<T, R> extends MatchStep<T, R>, DefaultMatchStep<R>, EndStep<R> {
}