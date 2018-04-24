package com.yuriia.matcher;

import com.yuriia.matcher.impl.Case;
import com.yuriia.matcher.impl.DefaultCases;
import com.yuriia.matcher.impl.MapLookupCases;
import com.yuriia.matcher.impl.MatchOrResultStepImpl;
import com.yuriia.matcher.steps.MatchStep;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Functionality to emulate Pattern Matching. It is similar to 'switch' operator, but with some enhancements:
 * <ul>
 * <li>More types to switch on: Classes (types) or Predicates (patterns)</li>
 * <li>Can return value (expression switch) and not just execute statements</li>
 * <li>No need to cast matched values if case is a type</li>
 * <li>Better readability?</li>
 * <li>Lazy</li>
 * </ul>
 * Though there are obvious drawbacks:
 * <ul>
 * <li>it uses more memory (several Objects are used to represent matcher state) than just a 'switch' statement</li>
 * <li>performance implications (especially for Predicate cases where it will be O(n) instead of O(1)) </li>
 * </ul>
 *
 * @author yuriia
 */
public interface Matcher<T, R> extends Step {
    
    R match(T value);

    static <T, R> MatchStep<T, R> when() {
        return new MatcherImpl<T, R>().start();
    }

    /**
     * Matcher implementation. Holds Matcher state.
     *
     * @author yuriia
     */
    class MatcherImpl<T, R> implements Matcher<T, R> {
    
        /**
         * Collection of Match cases.
         */
        private Cases<T, R> cases = new DefaultCases<>();

        private MatcherImpl() {
        }

        MatchStep<T, R> start() {
            return new MatchOrResultStepImpl<>(this);
        }
    
        /**
         * @return matched value.
         */
        @Override
        public R match(T source) {
            return cases.match(source);
        }
        
        public void compile() {
            this.cases = MapLookupCases.compile(cases);
        }
    
        /**
         * Append match case with given predicate.
         *
         * @param predicate - predicate to match values with
         * @param <C>       - actual type of the value
         * @return match case
         */
        public <C extends T> Case<C, R> addCase(Predicate<? super C> predicate) {
            return cases.addCase(predicate);
        }
        
        public void setDefaultValue(Supplier<R> defaultValue) {
            this.cases.setDefaultValue(defaultValue);
        }
    }
}
