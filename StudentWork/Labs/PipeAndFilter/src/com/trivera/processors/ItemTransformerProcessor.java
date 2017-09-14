package com.trivera.processors;

import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

/**
 * {@inheritDoc}
 */
public class ItemTransformerProcessor<T,R>
        extends SubmissionPublisher<R>
        implements Processor<T, R> {

    /*
     * Lab: Create member variable for lambda Function
     */




    /*
     * Lab: Create member variable for Subscription
     */




    public ItemTransformerProcessor(Function<? super T, ? extends R> function) {
        super();
        this.function = function;
    }



    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }




    @Override
    public void onNext(T item) {


        /*
         * Lab: Print out item to console
         */



        /*
         * Lab: Submit the item that has the lambda function applied, and converted
         * to (R) return type.
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
