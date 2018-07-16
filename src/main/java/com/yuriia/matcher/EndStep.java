package com.yuriia.matcher;

/**
 * Marker interface to specify that current is union of MatchStep & DefaultValueStep & Matcher steps.
 * So API client can either:
 * <ul>
 * <li>Add new case into current matcher (Match step)</li>
 * <li>Specify default value and match value (DefaultValueStep)</li>
 * <li>Match value (Matcher)</li>
 * </ul>
 *
 * @param <T> - type of the value being matched
 * @param <R> - type of the result value
 * @author yuriia
 */
public interface EndStep<T, R> extends MatchStep<T, R>, DefaultValueStep<T, R>, Matcher<T, R> {
}
