package com.trivera;

import com.trivera.subscriber.SubscriberOne;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class ReactiveTest {

    public static void main(String[] args){

        /*
         * Create standard SubmissionPublisher
         */
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        /*
         * Create custom Subscriber
         */
        SubscriberOne<String> subscriber = new SubscriberOne<>();

        /*
         * Register custom Subscriber with Publisher
         */
        publisher.subscribe(subscriber);

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
