package com.yuriia.matcher.impl;

import com.yuriia.matcher.CaseStep;
import com.yuriia.matcher.EndStep;
import com.yuriia.matcher.MatchOrEndStep;
import com.yuriia.matcher.MatchStep;
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
     * Parent match step to continue matching.
     */
    private final MatchOrEndStep<T, R> parentStep;
    /**
     * Current match case.
     */
    private final Case<C, R> matchCase;

    WhereCaseStepImpl(MatchOrEndStep<T, R> parentStep, Case<C, R> matchCase) {
        this.parentStep = parentStep;
        this.matchCase = matchCase;
    }

    @Override
    public CaseStep<T, C, R> where(Predicate<C> predicate) {
        matchCase.addWhen(predicate);
        return this;
    }

    @Override
    public MatchOrEndStep<T, R> get(Function<C, R> mapper) {
        matchCase.setCaseMapper(mapper);
        return parentStep;
    }
}
