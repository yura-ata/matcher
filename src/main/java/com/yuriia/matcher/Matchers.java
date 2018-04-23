package com.yuriia.matcher;

import com.yuriia.matcher.impl.MatcherImpl;

/**
 * @author yuriia
 */
public class Matchers {
    
    static <A, B> MatchStep<A, B> match(A source) {
        return new MatcherImpl<A, B>().match(source);
    }

    static <A, B> MatchStep<A, B> match(A source, @SuppressWarnings("unused") Class<B> resultType) {
        return new MatcherImpl<A, B>().match(source);
    }
}
