package com.trivera.subscriber;

import java.util.concurrent.Flow;
import java.util.function.Consumer;

/**
 * http://download.java.net/java/jdk9/docs/api/java/util/concurrent/Flow.html
 * @param <T>
 *
 * A Flow.Subscriber arranges that items be requested and processed.
 * Items (invocations of Flow.Subscriber.onNext(T)) are not issued unless requested,
 * but multiple items may be requested. Many Subscriber implementations can arrange this in
 * the style of the following example, where a buffer size of 1 single-steps, and larger sizes
 * usually allow for more efficient overlapped processing with less communication; for example
 * with a value of 64, this keeps total outstanding requests between 32 and 64.
 * Because Subscriber method invocations for a given Flow.Subscription are strictly ordered,
 * there is no need for these methods to use locks or volatiles unless a Subscriber maintains multiple
 * Subscriptions (in which case it is better to instead define multiple Subscribers,
 * each with its own Subscription).
 */
public class SampleSubscriber<T> implements Flow.Subscriber<T> {

    final Consumer<? super T> consumer;
    Flow.Subscription subscription;
    final long bufferSize;
    long count;

    SampleSubscriber(long bufferSize, Consumer<? super T> consumer) {
        this.bufferSize = bufferSize;
        this.consumer = consumer;
    }

    public void onSubscribe(Flow.Subscription subscription) {
        long initialRequestSize = bufferSize;
        count = bufferSize - bufferSize / 2; // re-request when half consumed
        (this.subscription = subscription).request(initialRequestSize);
    }

    public void onNext(T item) {
        if (--count <= 0)
            subscription.request(count = bufferSize - bufferSize / 2);
        consumer.accept(item);
    }

    public void onError(Throwable ex) {
        ex.printStackTrace();
    }

    public void onComplete() {
    }
}