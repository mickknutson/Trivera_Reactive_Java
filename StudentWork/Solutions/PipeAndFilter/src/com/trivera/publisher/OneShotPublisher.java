package com.trivera.publisher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Examples.
 * A Flow.Publisher usually defines its own Flow.Subscription implementation;
 * constructing one in method subscribe and issuing it to the calling Flow.Subscriber.
 * It publishes items to the subscriber asynchronously, normally using an Executor.
 * For example, here is a very simple publisher that only issues (when requested) a
 * single TRUE item to a single subscriber. Because the subscriber receives only a
 * single item, this class does not use buffering and ordering control required in most
 * implementations (for example SubmissionPublisher).
 */
public class OneShotPublisher implements Flow.Publisher<Boolean> {

    private final ExecutorService executor = ForkJoinPool.commonPool(); // daemon-based
    private boolean subscribed; // true after first subscribe

    public synchronized void subscribe(Flow.Subscriber<? super Boolean> subscriber) {
        if (subscribed)
            subscriber.onError(new IllegalStateException()); // only one allowed
        else {
            subscribed = true;
            subscriber.onSubscribe(new OneShotSubscription(subscriber, executor));
        }
    }

    static class OneShotSubscription implements Flow.Subscription {
        private final Flow.Subscriber<? super Boolean> subscriber;
        private final ExecutorService executor;
        private Future<?> future; // to allow cancellation
        private boolean completed;

        OneShotSubscription(Flow.Subscriber<? super Boolean> subscriber,
                            ExecutorService executor) {
            this.subscriber = subscriber;
            this.executor = executor;
        }

        public synchronized void request(long n) {
            if (n != 0 && !completed) {
                completed = true;
                if (n < 0) {
                    IllegalArgumentException ex = new IllegalArgumentException();
                    executor.execute(() -> subscriber.onError(ex));
                } else {
                    future = executor.submit(() -> {
                        subscriber.onNext(Boolean.TRUE);
                        subscriber.onComplete();
                    });
                }
            }
        }

        public synchronized void cancel() {
            completed = true;
            if (future != null) future.cancel(false);
        }
    }
}