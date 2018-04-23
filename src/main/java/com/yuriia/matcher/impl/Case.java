package com.yuriia.matcher.impl;

import com.yuriia.matcher.MatcherPredicate;
import com.yuriia.matcher.Predicates;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author yuriia
 */
class Case<T, R> {
    
    private MatcherPredicate<? super T> casePredicate;
    private Function<? super T, ? extends R> mapper;

    Case(MatcherPredicate<? super T> casePredicate) {
        this.casePredicate = casePredicate;
    }

    boolean matches(T source) {
        return casePredicate.test(source);
    }

    R map(T source) {
        return this.mapper.apply(source);
    }

    void setCaseMapper(Function<? super T, ? extends R> mapper) {
        this.mapper = mapper;
    }

    void addWhen(Predicate<? super T> when) {
        this.casePredicate = Predicates.and(this.casePredicate, when);
    }
}
