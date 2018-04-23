package com.yuriia.matcher.impl;


import com.yuriia.matcher.CaseStep;
import com.yuriia.matcher.WhereCaseStep;

import java.util.function.Predicate;

/**
 * @author yuriia
 */
class WhereCaseStepImpl<T, C extends T, R> extends CaseStepImpl<T, C, R> implements WhereCaseStep<T, C, R> {

    WhereCaseStepImpl(MatcherImpl<T, R> matcher, Case<C, R> matcherCase) {
        super(matcher, matcherCase);
    }

    @Override
    public CaseStep<T, C, R> where(Predicate<C> predicate) {
        matcherCase.addWhen(predicate);
        return this;
    }
}
