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



    /*
     * Lab: Create member variable for Subscription
     */




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





        /*
         * Lab: Add else clause to print which items where filtered out
         */






        /*
         * Lab: request more messages from the subscription
         */



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