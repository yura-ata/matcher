package com.yuriia.matcher;

import java.util.function.Function;

/**
 * @author yuriia
 */
public interface CaseStep<T, C extends T, R> {

   MatchStep<T, R> get(Function<C, R> mapper);
}
