package com.yuriia.matcher.steps;

import com.yuriia.matcher.Step;

import java.util.function.Predicate;

/**
 * First (and probably main) step of the Matcher. Similar to the 'case' keyword in the 'switch' statement.
 * It is impossible to call terminal or modulating steps from this step.
 *
 * @author yuriia
 */
public interface MatchStep<T, R> extends Step {

    /**
     * Match current values using given {@link Predicate}.
     *
     * @param predicate - predicate to test current value
     * @return case step (to specify what to do if value matches)
     */
    <C extends T> WhereCaseStep<T, C, R> with(Predicate<? super C> predicate);

    /**
     * Match current value is instance of given class.
     *
     * @param type - desired value type
     * @param <C>  - real type of the values (so that next steps doesn't need to cast it to C)
     * @return case step (to specify what to do if value matches)
     */
    <C extends T> WhereCaseStep<T, C, R> with(Class<C> type);

    /**
     * Match current value class equals to given class.
     * Not an instanceof check, but can be optimized into O(1) map look-up instead O(n) checks.
     *
     * @param type - desired value type
     * @param <C>  - real type of the values (so that next steps doesn't need to cast it to C)
     * @return case step (to specify what to do if value matches)
     */
    <C extends T> WhereCaseStep<T, C, R> is(Class<C> type);

    /**
     * Match current value is equals to the given constant.
     *
     * @param constant - constant value to match on
     * @param <C>      - real type of the values (so that next steps doesn't need to cast it to C)
     * @return case step (to specify what to do if value matches)
     */
    <C extends T> CaseStep<T, C, R> is(C constant);
}
