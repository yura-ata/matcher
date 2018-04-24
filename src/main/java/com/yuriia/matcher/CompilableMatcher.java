package com.yuriia.matcher;

/**
 * @author yuriia
 */
public interface CompilableMatcher<T, R> extends Matcher<T, R> {

    Matcher<T, R> compile();
}