package com.trivera;

import java.util.List;

public class ReactiveTest {

    public static void main(String[] args){

        /*
         * Lab: Instantiate  standard SubmissionPublisher
         */



        /*
         * Lab: Instantiate custom Subscriber
         */




        /*
         * Lab: Register custom Subscriber with Publisher
         */




        /*
         * COMPLETE: Publish items to Subscribers
         */
        System.out.println("Publishing Items...");

        List.of("3", "x", "2", "x", "1", "x")
                .forEach(publisher::submit);


        /*
         * Lab: Close Publisher
         */





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
