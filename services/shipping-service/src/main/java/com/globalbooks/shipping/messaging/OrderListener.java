package com.globalbooks.shipping.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    @RabbitListener(queues = "orders.shipping")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }
}
