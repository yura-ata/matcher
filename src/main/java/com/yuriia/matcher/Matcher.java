package com.yuriia.matcher;

import java.util.Optional;

/**
 * Functionality to emulate Pattern Matching. It is similar to 'switch' operator, but with some enhancements:
 * <ul>
 * <li>More types to switch on: Classes (types) or Predicates (patterns)</li>
 * <li>Can return value (expression switch) and not just execute statements</li>
 * <li>No need to cast values in case expressions</li>
 * <li>Better readability?</li>
 * </ul>
 * Matcher is actually a terminal step in the chain of Matcher steps.
 *
 * @author yuriia
 */
public interface Matcher<T, R> {

    /**
     * Match given values with specified patterns.
     *
     * @return result of the pattern matching
     */
    R match(T value);

    default Optional<R> matchAsOptional(T value) {
        return Optional.ofNullable(match(value));
    }

    /**
     * Create builder to configure matcher.
     *
     * @param <FROM> - type of value that we need to match
     * @param <TO>   - type of the result
     * @return builder
     */
    static <FROM, TO> Builder<FROM, TO> matcher() {
        return new Builder<>();
    }

    /**
     * Convenience Matcher builder to specify from and ty types: generic type inference is not powerful enough to infer
     * this types from last call of method chain.
     * T and R are actually specified only by Matcher.match method, but has to be used in all preceding calls (when, then, etc.).
     *
     * @param <T> - from type holder
     * @param <R> - to type holder
     * @author yuriia
     */
    class Builder<T, R> {

        /**
         * Use from() or to() factories instead.
         */
        private Builder() {
        }

        /**
         * Get matcher builder converted from [T, R] into [FROM, R].
         *
         * @param from   - class just to specify FROM type.
         * @param <FROM> - type of matcher result
         * @return builder
         */
        <FROM> Builder<FROM, R> from(@SuppressWarnings("unused") Class<FROM> from) {
            @SuppressWarnings("unchecked") Builder<FROM, R> builder = (Builder<FROM, R>) this;
            return builder;
        }

        /**
         * Create matcher with given types and start matching (return MatchStep).
         *
         * @param to   - class just to specify TO type
         * @param <TO> - type of matcher value
         * @return builder
         */
        <TO> MatchStep<T, TO> to(@SuppressWarnings("unused") Class<TO> to) {
            return new MatcherImpl<>();
        }

        /**
         * @return started matcher with current types.
         */
        MatchStep<T, R> get() {
            return new MatcherImpl<>();
        }
    }
}
