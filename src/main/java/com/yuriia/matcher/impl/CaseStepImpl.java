package com.yuriia.matcher.impl;

import com.yuriia.matcher.CaseStep;
import com.yuriia.matcher.MatchStep;

import java.util.function.Function;

/**
 * @author yuriia
 */
class CaseStepImpl<T, C extends T, R> implements CaseStep<T, C, R> {

    private final MatcherImpl<T, R> matcher;
    final Case<C, R> matcherCase;

    CaseStepImpl(MatcherImpl<T, R> matcher, Case<C, R> matcherCase) {
        this.matcher = matcher;
        this.matcherCase = matcherCase;
    }

    @Override
    public MatchStep<T, R> get(Function<C, R> mapper) {
        matcherCase.setCaseMapper(mapper);
        return new MatchStepImpl<>(matcher);
    }
}
