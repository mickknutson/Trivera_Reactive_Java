package com.trivera.processors;

import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Predicate;

/**
 * {@inheritDoc}
 */
public class ItemFilterProcessor<T,R>
        extends SubmissionPublisher<R>
        implements Processor<T, R> {

    /*
     * Lab: Create member variable for lambda Predicate
     */
    private Predicate<? super T> predicate;


    /*
     * Lab: Create member variable for Subscription
     */
    private Subscription subscription;


    public ItemFilterProcessor(Predicate<? super T> predicate) {
        super();
        this.predicate = predicate;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }




    @Override
    public void onNext(T item) {

        /*
         * Lab: Test to ensure the item is not (!) true
         * Then submit the item, casting it to (R) return type
         */
        if(! predicate.test(item)) {
            submit((R) item);
        }

        /*
         * Lab: Add else clause to print which items where filtered out
         */
        else {
            System.out.println("item filtered out : " + item);
        }



        /*
         * Lab: request more messages from the subscription
         */
        subscription.request(1);
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        close();
    }
}