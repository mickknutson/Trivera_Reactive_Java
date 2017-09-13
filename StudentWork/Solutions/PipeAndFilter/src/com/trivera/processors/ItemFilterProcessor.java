package com.trivera.processors;

import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;
import java.util.function.Predicate;

public class ItemFilterProcessor<T,R>
        extends SubmissionPublisher<R>
        implements Processor<T, R> {

    private Predicate<? super T> predicate;
    private Subscription subscription;

    public ItemFilterProcessor(Predicate<? super T> predicate) {
        super();
        this.predicate = predicate;
    }

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
        if(! predicate.test(item)) {
            submit((R) item);
        } else{
            System.out.println("item filtered out : " + item);
        }
        subscription.request(1);
    }

    /**
     * {@inheritDoc}
     * @param t the exception
     */
    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onComplete() {
        close();
    }
}