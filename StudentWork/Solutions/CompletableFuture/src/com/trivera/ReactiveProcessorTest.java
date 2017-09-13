package com.trivera;

import com.trivera.processors.ItemFilterProcessor;
import com.trivera.processors.ItemProcessor;
import com.trivera.subscriber.SubscriberOne;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class ReactiveProcessorTest {

    public static void main(String[] args){

        /*
         * Create standard SubmissionPublisher
         */
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        /*
         * Create custom Filter Processor
         */
        ItemFilterProcessor<String, String> filterProcessor = new ItemFilterProcessor<>(s -> s.equals("x"));
        /*
         * Register Processor with publisher
         */
        publisher.subscribe(filterProcessor);

        /*
         * Create custom Processor
         */
        ItemProcessor<String, Integer> itemTransformProcessor = new ItemProcessor<>(Integer::parseInt);

        /*
         * Register Processor with previous Processor
         */
        filterProcessor.subscribe(itemTransformProcessor);

        /*
         * Create custom Subscriber
         */
        SubscriberOne<Integer> subscriber = new SubscriberOne<>();

        /*
         * Register custom Subscriber with previous Processor
         */
        itemTransformProcessor.subscribe(subscriber);


        /*
         * Publish items to Subscribers
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
