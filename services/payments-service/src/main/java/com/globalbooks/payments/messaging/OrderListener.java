package com.globalbooks.payments.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    @RabbitListener(queues = "orders.payments")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }
}
