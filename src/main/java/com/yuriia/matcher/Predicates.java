package com.yuriia.matcher;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Static factory for the useful predicates.
 *
 * @author yuriia
 */
public class Predicates {

    /**
     * Build (optimizable) predicate to test if value is equals with given constant.
     *
     * @param constant - constant value (can be null)
     * @return predicate
     */
    public static <T> Predicate<T> is(T constant) {
        return new ConstantPredicate<>(constant);
    }

    /**
     * Build (optimizable) predicate to test if value class is equal to given type.
     *
     * @param type - desired type
     * @return predicate
     */
    public static <T> Predicate<T> isType(Class<T> type) {
        return new ConstantPredicate<>(type);
    }

    /**
     * Build predicate to test if value is of given type (instanceof).
     *
     * @param type - desired type
     * @return predicate
     */
    public static <T> Predicate<T> instanceOf(Class<T> type) {
        return type::isInstance;
    }

    /**
     * Build And predicate to test both provided predicates.
     *
     * @param first  - first predicate
     * @param second - second predicate
     * @return composed predicate
     */
    public static <T> Predicate<? super T> and(Predicate<? super T> first, Predicate<? super T> second) {
        return t -> first.test(t) && second.test(t);
    }

    public static abstract class OptimizablePredicate<T> implements Predicate<T> {

        private final Object caseConstant;

        private OptimizablePredicate(Object caseConstant) {
            this.caseConstant = caseConstant;
        }

        public Object getCaseConstant() {
            return caseConstant;
        }

        public abstract Object mapValue(T value);
    }

    /**
     * Predicate to mark that current case is constant and can be optimized as map lookup (by constant keys) instead
     * of chain of predicates.
     *
     * @param <T> - type of the predicate value
     */
    public static final class ConstantPredicate<T> extends OptimizablePredicate<T> {

        private ConstantPredicate(Object caseConstant) {
            super(caseConstant);
        }

        @Override
        public boolean test(T t) {
            return Objects.equals(t, getCaseConstant());
        }

        @Override
        public Object mapValue(T value) {
            return value;
        }
    }

    public static final class ClassPredicate<T> extends OptimizablePredicate<T> {

        private ClassPredicate(Object caseConstant) {
            super(caseConstant);
        }

        @Override
        public boolean test(T t) {
            return t != null && t.getClass().equals(getCaseConstant());
        }

        @Override
        public Object mapValue(T value) {
            return value != null ? value.getClass() : null;
        }
    }
}
