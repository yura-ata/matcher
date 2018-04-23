package com.yuriia.matcher;

import java.util.function.Predicate;

/**
 * Step to modulate {@link CaseStep} (case clause) with additional predicate. For example:
 * {@code .match(object).with(String.class).where(s -> s.isEmpty()).get(s -> "empty") } - match only empty strings
 *
 * @author yuriia
 */
public interface WhereCaseStep<T, C extends T, R> extends CaseStep<T, C, R> {

    /**
     * Apply additional predicate on current case clause.
     *
     * @param predicate - value predicate
     * @return case step
     */
    CaseStep<T, C, R> where(Predicate<C> predicate);
}
