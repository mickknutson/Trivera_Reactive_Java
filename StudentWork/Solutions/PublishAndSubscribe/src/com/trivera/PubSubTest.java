package com.trivera;

import com.trivera.subscriber.SubscriberOne;
import com.trivera.subscriber.SubscriberTwo;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class PubSubTest {

    public static void main(String[] args){

        // Create Publisher
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // Register SubscriberOne
        SubscriberOne<String> subscriber = new SubscriberOne<>();
        publisher.subscribe(subscriber);

        // Register SubscriberOne
        SubscriberTwo<String> subscriber2 = new SubscriberTwo<>(3);
        publisher.subscribe(subscriber2);

        // Publish items
        System.out.println("Publishing Items...");

        List.of("3", "x", "2", "x", "1", "x")
                .forEach(i -> publisher.submit(i));

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
