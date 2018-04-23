package com.yuriia.matcher.impl;

import com.yuriia.matcher.CaseStep;
import com.yuriia.matcher.MatchOrEndStep;
import com.yuriia.matcher.WhereCaseStep;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * WhereCase and Case Steps implementation.
 *
 * @author yuriia
 */
class WhereCaseStepImpl<T, C extends T, R> implements WhereCaseStep<T, C, R> {

    /**
     * State of the Current matcher.
     */
    private final MatcherImpl<T, R> matcher;
    /**
     * Current match case.
     */
    private final Case<C, R> matcherCase;

    WhereCaseStepImpl(MatcherImpl<T, R> matcher, Predicate<? super C> predicate) {
        this.matcher = matcher;
        this.matcherCase = matcher.addCase(predicate);
    }

    @Override
    public CaseStep<T, C, R> where(Predicate<C> predicate) {
        matcherCase.addWhen(predicate);
        return this;
    }

    @Override
    public MatchOrEndStep<T, R> get(Function<C, R> mapper) {
        matcherCase.setCaseMapper(mapper);
        return new MatchOrEndStepImpl<>(matcher);
    }
}
