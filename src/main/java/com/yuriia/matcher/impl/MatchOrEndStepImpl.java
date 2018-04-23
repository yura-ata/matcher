package com.yuriia.matcher.impl;

import com.yuriia.matcher.*;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Implementation for Match and End Steps.
 *
 * @author yuriia
 */
class MatchOrEndStepImpl<T, R> implements MatchOrEndStep<T, R> {

    /**
     * State of the current matcher.
     */
    private final MatcherImpl<T, R> matcher;

    MatchOrEndStepImpl(MatcherImpl<T, R> matcher) {
        this.matcher = matcher;
    }

    @Override
    public <C extends T> WhereCaseStep<T, C, R> is(Class<C> type) {
        return new WhereCaseStepImpl<>(matcher, Predicates.isType(type));
    }

    @Override
    public <C extends T> CaseStep<T, C, R> is(C constant) {
        return new WhereCaseStepImpl<>(matcher, Predicates.is(constant));
    }

    @Override
    public <C extends T> WhereCaseStep<T, C, R> with(Class<C> type) {
        return new WhereCaseStepImpl<>(matcher, Predicates.instanceOf(type));
    }

    @Override
    public <C extends T> WhereCaseStep<T, C, R> with(Predicate<? super C> predicate) {
        return new WhereCaseStepImpl<>(matcher, predicate);
    }

    @Override
    public R value() {
        return matcher.get();
    }

    @Override
    public EndStep<R> orGet(Supplier<R> value) {
        matcher.setDefault(value);
        return this;
    }

    @Override
    public EndStep<R> orGet(R value) {
        matcher.setDefault(value);
        return this;
    }

    @Override
    public EndStep<R> orThrow(RuntimeException exception) {
        matcher.setThrowable(exception);
        return this;
    }

    @Override
    public EndStep<R> orThrow(Supplier<? extends RuntimeException> exception) {
        matcher.setThrowable(exception);
        return this;
    }
}
