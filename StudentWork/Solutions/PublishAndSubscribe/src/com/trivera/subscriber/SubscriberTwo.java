package com.trivera.subscriber;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicInteger;

public class SubscriberTwo<T> implements Subscriber<T> {

    private Subscription subscription;

    AtomicInteger count = new AtomicInteger();

    /**
     * {@inheritDoc}
     * @param subscription a new subscription
     */
    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    /**
     * {@inheritDoc}
     * @param item the item
     */
    @Override
    public void onNext(T item) {
        System.out.println("-->next["
                + count.getAndIncrement()
                + "] : "
                + "type["
                + item.getClass()
                + "] : "
                + item);

        /*
         * request < 1 == doesn't want to receive anymore messages
         * request > 0 == wants to receive more messages
         *
         * Long.MAX_VALUE is considered as effectively unbounded
         */
        subscription.request(1);
    }

    /**
     * {@inheritDoc}
     * @param t the exception
     */
    @Override
    public void onError(Throwable t) {
        // Handle Throwable
        t.printStackTrace();

        /*
         * Using the Object's wait() and notifyAll() methods to cause the main thread
         * to wait until onComplete() is finished.
         * <i>Otherwise, you'll probably not observe any subscriber output.</i>
         */
        synchronized("Rx") {
            "Rx".notifyAll();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onComplete() {
        System.out.println("done...");

        /*
         * Using the Object's wait() and notifyAll() methods to cause the main thread
         * to wait until onComplete() is finished.
         * <i>Otherwise, you'll probably not observe any subscriber output.</i>
         */
        synchronized("Rx"){
            "Rx".notifyAll();
        }
    }
}
