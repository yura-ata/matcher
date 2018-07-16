package com.yuriia.matcher;

import java.util.function.Predicate;

/**
 * Match step of the Matcher - same as 'case' keyword in the 'switch' statement.
 *
 * @author yuriia
 */
public interface MatchStep<T, R> {

    /**
     * Check if current value satisfies given {@link Predicate}.
     *
     * @param predicate - predicate to test current value
     * @param <C>       - real type of the values (so that next steps doesn't need to cast value to C)
     * @return case step (to specify what to do if value matches)
     */
    <C extends T> WhereCaseStep<T, C, R> when(Predicate<C> predicate);

    /**
     * Check if class of current value when equals to given class.
     * Not an instanceof check, but can be optimized into O(1) map look-up instead O(n) checks.
     *
     * @param type - desired value type
     * @param <C>  - real type of the values (so that next steps doesn't need to cast value to C)
     * @return case step (to specify what to do if value matches)
     */
    <C extends T> WhereCaseStep<T, C, R> when(Class<C> type);

    /**
     * Check if current value when equals to the given constant.
     *
     * @param constant - constant value to match on
     * @param <C>      - real type of the values (so that next steps doesn't need to cast value to C)
     * @return case step (to specify what to do if value matches)
     */
    <C extends T> CaseStep<T, C, R> when(C constant);

    /**
     * Check if current value when instance of given type (instanceof).
     *
     * @param type - desired value type
     * @param <C>  - real type of the values (so that next steps doesn't need to cast value to C)
     * @return case step (to specify what to do if value matches)
     */
    <C extends T> WhereCaseStep<T, C, R> instanceOf(Class<C> type);
}
