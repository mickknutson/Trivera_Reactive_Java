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
    private Function<? super T, ? extends R> function;


    /*
     * Lab: Create member variable for Subscription
     */
    private Subscription subscription;

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
        System.out.println("apply Function and submit : " + item);


        /*
         * Lab: Submit the item that has the lambda function applied, and converted
         * to (R) return type.
         */
        submit((R) function.apply(item));


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
