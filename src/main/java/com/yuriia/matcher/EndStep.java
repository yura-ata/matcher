package com.yuriia.matcher;

/**
 * Marker interface to specify that current step is union of MatchStep & DefaultValueStep & Matcher steps.
 * So API client can either:
 * <ul>
 * <li>Add new case into current matcher (Match step)</li>
 * <li>Specify default value and match value (DefaultValueStep)</li>
 * <li>Compute value - terminal step (Matcher)</li>
 * </ul>
 *
 * @param <T> - type of the value being matched
 * @param <R> - type of the result value
 * @author yuriia
 */
public interface EndStep<T, R> extends Matcher<T, R>, MatchStep<T, R>, DefaultValueStep<T, R> {
}
