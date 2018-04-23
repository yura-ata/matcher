package com.yuriia.matcher;

import com.yuriia.matcher.impl.MatcherImpl;

/**
 * Functionality to emulate Pattern Matching. It is similar to 'switch' operator, but with some enhancements:
 * <ul>
 * <li>More types to switch on: Classes (types) or Predicates (patterns)</li>
 * <li>Can return value (expression switch) and not just execute statements</li>
 * <li>No need to cast matched values if case is a type</li>
 * <li>Better readability?</li>
 * <li>Lazy</li>
 * </ul>
 * Though there are obvious drawbacks:
 * <ul>
 * <li>it uses more memory (several Objects are used to represent matcher state) than just a 'switch' statement</li>
 * <li>performance implications (especially for Predicate cases where it will be O(n) instead of O(1)) </li>
 * </ul>
 *
 * @author yuriia
 */
public interface Matcher<T, R> {

    /**
     * Specify value to match.
     *
     * @param source - value to match
     * @return match step
     */
    MatchStep<T, R> match(T source);

    /**
     * Static factory to match given value.
     *
     * @param source     - value to match
     * @param resultType - (type witness) desired type of the result
     * @return match step
     */
    static <A, B> MatchStep<A, B> match(A source, @SuppressWarnings("unused") Class<B> resultType) {
        return new MatcherImpl<A, B>().match(source);
    }
}
