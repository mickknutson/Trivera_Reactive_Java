package com.trivera;

import com.trivera.domain.Order;
import com.trivera.subscriber.SubscriberOne;
import com.trivera.subscriber.SubscriberTwo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class PubSubOrderTest {

    public static void main(String[] args){

        // Create Publisher
        SubmissionPublisher<Order> publisher = new SubmissionPublisher<>();

        // Register SubscriberOne
        SubscriberOne<Order> subscriber = new SubscriberOne<>();
        publisher.subscribe(subscriber);

        // Publish items
        System.out.println("Publishing Orders...");

        List<Order> orders = getOrders();

        orders.forEach(i -> publisher.submit(i));

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

    public static List<Order> getOrders(){
        List<Order> orders = new ArrayList<>();

        orders.add(new Order(1, 1, "missing JSON API", "Mark Reinhold"));
        orders.add(new Order(2, 2, "missing Money and Currency API", "Mark Reinhold"));
        orders.add(new Order(3, 3, "missing Money and Currency API", "Mark Reinhold"));
        orders.add(new Order(4, 4, "missing Local Variable Type Inference", "Mark Reinhold"));

        return orders;
    }

} // class{}
