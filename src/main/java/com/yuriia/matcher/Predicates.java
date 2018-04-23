package com.yuriia.matcher;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author yuriia
 */
public class Predicates {

    public static <T> MatcherPredicate<T> is(Class<T> type) {
        return new MatcherPredicate<T>() {

            @Override
            public boolean test(T t) {
                return type.equals(t.getClass());
            }

            @Override
            public boolean isConstant() {
                return true;
            }
        };
    }

    public static <T> MatcherPredicate<T> equal(T o) {
        return new MatcherPredicate<T>() {
            
            @Override
            public boolean test(T t) {
                return Objects.equals(o, t);
            }

            @Override
            public boolean isConstant() {
                return true;
            }
        };
    }

    public static <T> MatcherPredicate<T> of(Predicate<? super T> predicate) {
        return predicate::test;
    }

    public static <T> MatcherPredicate<T> and(Predicate<? super T> p1, Predicate<? super T> p2) {
        return t -> p1.test(t) && p2.test(t);
    }

    public static <T> MatcherPredicate<T> instanceOf(Class<T> type) {
        return type::isInstance;
    }
}
