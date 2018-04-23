package com.yuriia.matcher;

/**
 * @author yuriia
 */
public interface Matcher<T, R> {
    
    MatchStep<T, R> match(T source);
}
