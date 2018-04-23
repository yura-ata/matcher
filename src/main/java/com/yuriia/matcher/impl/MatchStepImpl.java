package com.yuriia.matcher.impl;

import com.yuriia.matcher.EndStep;
import com.yuriia.matcher.MatchStep;
import com.yuriia.matcher.Predicates;
import com.yuriia.matcher.WhereCaseStep;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author yuriia
 */
class MatchStepImpl<T, R> extends EndStepImpl<T, R> implements MatchStep<T, R> {
    
    MatchStepImpl(MatcherImpl<T, R> matcher) {
       super(matcher);
    }

    @Override
    public <C extends T> WhereCaseStep<T, C, R> with(Class<C> type) {
        return new WhereCaseStepImpl<>(matcher, matcher.addCase(Predicates.is(type)));
    }

    @Override
    public <C extends T> WhereCaseStep<T, C, R> with(Predicate<? super C> predicate) {
        return new WhereCaseStepImpl<>(matcher, matcher.addCase(Predicates.of(predicate)));
    }

    @Override
    public EndStep<T, R> defaultCase(Supplier<R> value) {
        matcher.setDefault(value);
        return this;
    }

    @Override
    public EndStep<T, R> defaultCase(R value) {
        matcher.setDefault(value);
        return this;
    }
}
