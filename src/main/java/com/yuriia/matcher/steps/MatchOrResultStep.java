package com.yuriia.matcher.steps;

import com.yuriia.matcher.CompilableMatcher;

/**
 * Marker interface to specify that current step is MatchStep & DefaultCaseStep & CompilableMatcher (union type) -
 * to add either next case or go to default value or compute result.
 *
 * @param <T> - type of the value being matched
 * @param <R> - type of the result value
 * @author yuriia
 */
public interface MatchOrResultStep<T, R> extends MatchStep<T, R>, DefaultCaseStep<T, R>, CompilableMatcher<T, R> {
}
