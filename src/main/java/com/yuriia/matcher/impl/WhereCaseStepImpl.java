package com.yuriia.matcher.impl;

import com.yuriia.matcher.Matcher;
import com.yuriia.matcher.steps.CaseStep;
import com.yuriia.matcher.steps.MatchOrResultStep;
import com.yuriia.matcher.steps.WhereCaseStep;

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
    private final Matcher.MatcherImpl<T, R> matcher;
    /**
     * Current match case.
     */
    private final Case<C, R> matcherCase;

    WhereCaseStepImpl(Matcher.MatcherImpl<T, R> matcher, Predicate<? super C> predicate) {
        this.matcher = matcher;
        this.matcherCase = matcher.addCase(predicate);
    }

    @Override
    public CaseStep<T, C, R> where(Predicate<C> predicate) {
        matcherCase.addWhen(predicate);
        return this;
    }

    @Override
    public MatchOrResultStep<T, R> get(Function<C, R> mapper) {
        matcherCase.setCaseMapper(mapper);
        return new MatchOrResultStepImpl<>(matcher);
    }
}
