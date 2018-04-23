package com.yuriia.matcher;

import java.util.function.Predicate;

/**
 * @author yuriia
 */
public interface WhereCaseStep<T, C extends T, R> extends CaseStep<T, C, R> {

    CaseStep<T, C, R> where(Predicate<C> predicate);
}
