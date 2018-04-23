package com.yuriia.matcher.impl;

import com.yuriia.matcher.EndStep;

/**
 * @author yuriia
 */
class EndStepImpl<T, R> implements EndStep<T, R> {

    final MatcherImpl<T, R> matcher;

    EndStepImpl(MatcherImpl<T, R> matcher) {
        this.matcher = matcher;
    }

    @Override
    public R value() {
        return matcher.get();
    }
}
