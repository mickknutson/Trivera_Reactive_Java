package com.trivera.subscriber;

import java.util.concurrent.Flow;

/**
 * http://download.java.net/java/jdk9/docs/api/java/util/concurrent/Flow.html
 * @param <T>
 *
 * The default value of defaultBufferSize() may provide a useful starting
 * point for choosing request sizes and capacities in Flow components
 * based on expected rates, resources, and usages. Or, when flow control is
 * never needed, a subscriber may initially request an effectively unbounded
 * number of items, as in:
 *
 */
public class UnboundedSubscriber<T> implements Flow.Subscriber<T> {
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(Long.MAX_VALUE); // effectively unbounded
    }
    public void onNext(T item) { use(item); }
    public void onError(Throwable ex) { ex.printStackTrace(); }
    public void onComplete() {}
    void use(T item) { return; }
}