package com.yuriia.matcher.impl;

import com.yuriia.matcher.CompilableMatcher;
import com.yuriia.matcher.Matcher;
import com.yuriia.matcher.Predicates;
import com.yuriia.matcher.steps.CaseStep;
import com.yuriia.matcher.steps.MatchOrResultStep;
import com.yuriia.matcher.steps.WhereCaseStep;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Implementation for Match and End Steps.
 *
 * @author yuriia
 */
public class MatchOrResultStepImpl<T, R> implements MatchOrResultStep<T, R> {

    /**
     * State of the current matcher.
     */
    private final MatcherImpl<T, R> matcher;

    public MatchOrResultStepImpl(MatcherImpl<T, R> matcher) {
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
    public R match(T value) {
        return matcher.match(value);
    }

    @Override
    public Matcher<T, R> compile() {
        matcher.compile();
        return this;
    }

    @Override
    public CompilableMatcher<T, R> orGet(Supplier<R> value) {
        Objects.requireNonNull(value);
        matcher.setDefaultValue(value);
        return this;
    }

    @Override
    public CompilableMatcher<T, R> orGet(R value) {
        matcher.setDefaultValue(() -> value);
        return this;
    }

    @Override
    public CompilableMatcher<T, R> orThrow(RuntimeException exception) {
        Objects.requireNonNull(exception);
        matcher.setDefaultValue(() -> { throw exception;});
        return this;
    }

    @Override
    public CompilableMatcher<T, R> orThrow(Supplier<? extends RuntimeException> exception) {
        Objects.requireNonNull(exception);
        matcher.setDefaultValue(() -> { throw exception.get();});
        return this;
    }
}
