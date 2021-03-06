package com.trivera;

import com.trivera.processors.ItemFilterProcessor;
import com.trivera.processors.ItemTransformerProcessor;
import com.trivera.subscriber.SubscriberOne;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class ReactiveProcessorTest {

    public static void main(String[] args){

        /*
         * Lab: Instantiate standard SubmissionPublisher
         */
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        /*
         * Lab: Instantiate custom Filter Processor
         * Use the lambda Predicate "s -> s.equals("x")" and the constructor
         */
        ItemFilterProcessor<String, String> filter = new ItemFilterProcessor<>(s -> s.equals("x"));


        /*
         * Lab: Register Processor with publisher
         */
        publisher.subscribe(filter);


        /*
         * Lab: Instantiate custom Item Transformer Processor
         * Use the lambda Function "Integer::parseInt" and the constructor
         */
        ItemTransformerProcessor<String, Integer> transformer = new ItemTransformerProcessor<>(Integer::parseInt);



        /*
         * Lab: Register Processor with previous Filter Processor,
         * not the original publisher
         */
        filter.subscribe(transformer);


        /*
         * Lab: Instantiate custom Subscriber
         */
        SubscriberOne<Integer> subscriber = new SubscriberOne<>();

        /*
         * Lab: Register custom Subscriber with previous Transformer Processor
         */
        transformer.subscribe(subscriber);







        /*
         * COMPLETE: Publish items to Subscribers
         */
        System.out.println("Publishing Items...");

        List.of("3", "x", "2", "x", "1", "x")
                .forEach(publisher::submit);

        publisher.close();

        /*
         * Using the Object's wait() and notifyAll() methods to cause the main thread
         * to wait until onComplete() is finished.
         * <i>Otherwise, you'll probably not observe any subscriber output.</i>
         */
        try {
            synchronized("Rx"){
                "Rx".wait();
            }
        } catch (InterruptedException ie) {}

    } // main()

} // class{}
