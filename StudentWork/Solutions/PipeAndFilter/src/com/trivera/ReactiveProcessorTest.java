package com.trivera;

import com.trivera.processors.ItemFilterProcessor;
import com.trivera.processors.ItemProcessor;
import com.trivera.subscriber.SubscriberOne;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class ReactiveProcessorTest {

    public static void main(String[] args){

        // Create Publisher
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // Register Filter
        ItemFilterProcessor<String, String> filterProcessor = new ItemFilterProcessor<>(s -> s.equals("x"));
        publisher.subscribe(filterProcessor);

        // Register transformer
        ItemProcessor<String, Integer> itemTransformProcessor = new ItemProcessor<>(Integer::parseInt);
        filterProcessor.subscribe(itemTransformProcessor);

        // Register SubscriberOne
        SubscriberOne<Integer> subscriber = new SubscriberOne<>();
        itemTransformProcessor.subscribe(subscriber);


        // Publish items
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
