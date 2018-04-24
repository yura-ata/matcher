package com.yuriia.matcher.impl;

import com.yuriia.matcher.Cases;
import com.yuriia.matcher.Predicates;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author yuriia
 */
public class MapLookupCases<T, R> implements Cases<T, R> {
    
    private Map<Object, Function<T, R>> lookUp = new HashMap<>(); 
    private Predicates.OptimizablePredicate<T> predicate;
    private Supplier<R> defaultValue;
    
    @SuppressWarnings("unchecked")
    public static <T, R> Cases<T, R> compile(Cases<T, R> cases) {
        if (cases instanceof DefaultCases) {
            return cases;
        }
        DefaultCases<T, R> dCases = (DefaultCases<T, R>) cases; 
        if (!(dCases.cases.size() > 1)) {
            // to small - don't bother to optimize
            return cases;
        }
        Predicate<?> firstPredicate = dCases.cases.get(0).casePredicate;
        if (!(Predicates.OptimizablePredicate.class.isInstance(firstPredicate))) {
            // not an optimizable predicate
            return cases;
        }
        MapLookupCases<T, R> mapLookup = new MapLookupCases<>();
        mapLookup.predicate = (Predicates.OptimizablePredicate<T>) firstPredicate;
        for (Case<T, R> matchCase : dCases.cases) {
            if (!matchCase.casePredicate.getClass().equals(firstPredicate.getClass())) {
                // cases contains not homogeneous predicates
                return cases;
            } else {
                Predicates.OptimizablePredicate<?> casePredicate = (Predicates.OptimizablePredicate<?>) matchCase.casePredicate;
                mapLookup.lookUp.put(casePredicate.getCaseConstant(), (Function<T, R>) matchCase.mapper);
            }
        }
        mapLookup.defaultValue = dCases.defaultValue;
        return mapLookup;
    }
    
    @Override
    public R match(T value) {
        return lookUp.getOrDefault(predicate.mapValue(value), k -> defaultValue.get()).apply(value);
    }

    @Override
    public <C extends T> Case<C, R> addCase(Predicate<? super C> pattern) {
        throw new IllegalStateException();
    }

    @Override
    public void setDefaultValue(Supplier<R> defaultValue) {
        throw new IllegalStateException();
    }
}
