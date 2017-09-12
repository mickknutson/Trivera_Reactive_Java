package com.trivera.subscriber;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicInteger;

public class SubscriberTwo<T> implements Subscriber<T> {

    private Subscription subscription;

    AtomicInteger count = new AtomicInteger();

    private AtomicInteger howMuchMessagesConsume;
    public List<T> consumedElements = new LinkedList<>();

    public SubscriberTwo(Integer howMuchMessagesConsume) {
        this.howMuchMessagesConsume = new AtomicInteger(howMuchMessagesConsume);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1); // a value of  Long.MAX_VALUE may be considered as effectively unbounded
    }

    @Override
    public void onNext(T item) {

        howMuchMessagesConsume.decrementAndGet();

        System.out.println("--> next["
                + count.getAndIncrement()
                + "] : "
                + "type["
                + item.getClass()
                + "] : "
                + item);
        consumedElements.add(item);

        /*
        * Value < 1 == Subscriber does not want to receive anymore messages
        * Value > 0 == Subscriber wants to receive more messages
         */
        if (howMuchMessagesConsume.get() > 0) {
            subscription.request(1);
        } else {
            System.out.println("--> (no more)");
        }
    }

    @Override
    public void onError(Throwable t) {
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

    @Override
    public void onComplete() {
        System.out.println("ST CYA !!!");

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
