package com.yuriia.matcher.impl;

import com.yuriia.matcher.CaseStep;
import com.yuriia.matcher.MatchOrEndStep;
import com.yuriia.matcher.WhereCaseStep;

import java.util.Objects;
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
        return new WhereCaseStepImpl<>(this, matcher.addCase(t -> t != null && t.getClass().equals(type)));
    }

    @Override
    public <C extends T> CaseStep<T, C, R> is(C constant) {
        return new WhereCaseStepImpl<>(this, matcher.addCase(t -> Objects.equals(t, constant)));
    }

    @Override
    public <C extends T> WhereCaseStep<T, C, R> with(Class<C> type) {
        return new WhereCaseStepImpl<>(this, matcher.addCase(type::isInstance));
    }

    @Override
    public <C extends T> WhereCaseStep<T, C, R> with(Predicate<C> predicate) {
        return new WhereCaseStepImpl<>(this, matcher.addCase(predicate));
    }

    @Override
    public R get() {
        return matcher.get();
    }

    @Override
    public R orGet(Supplier<R> value) {
        matcher.setDefault(Objects.requireNonNull(value));
        return get();
    }

    @Override
    public R orGet(R value) {
        matcher.setDefault(value);
        return get();
    }

    @Override
    public R orThrow(RuntimeException exception) {
        matcher.setThrowable(Objects.requireNonNull(exception));
        return get();
    }

    @Override
    public R orThrow(Supplier<? extends RuntimeException> exception) {
        matcher.setThrowable(Objects.requireNonNull(exception));
        return get();
    }
}
