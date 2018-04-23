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
        return new ConstantPredicate<>(t -> Objects.equals(t, constant), constant);
    }

    /**
     * Build (optimizable) predicate to test if value class is equal to given type.
     *
     * @param type - desired type
     * @return predicate
     */
    public static <T> Predicate<T> isType(Class<T> type) {
        return new ConstantPredicate<>(t -> t != null && t.getClass().equals(type), type);
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

    /**
     * Predicate to mark that current case is constant and can be optimized as map lookup (by constant keys) instead
     * of chain of predicates.
     *
     * @param <T> - type of the predicate value
     */
    public static final class ConstantPredicate<T> implements Predicate<T> {

        /**
         * Wrapped predicate
         */
        private final Predicate<T> predicate;
        /**
         * Constant value (that we can map on)
         */
        private final Object origin;

        private ConstantPredicate(Predicate<T> predicate, Object origin) {
            this.predicate = predicate;
            this.origin = origin;
        }

        @Override
        public boolean test(T t) {
            return predicate.test(t);
        }

        /**
         * @return origin constant value.
         */
        public Object origin() {
            return origin;
        }
    }
}
