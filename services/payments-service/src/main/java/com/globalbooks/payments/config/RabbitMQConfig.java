package com.globalbooks.payments.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange ordersExchange() {
        return new TopicExchange("orders");
    }

    @Bean
    public Queue paymentsQueue() {
        return new Queue("orders.payments");
    }

    @Bean
    public Queue shippingQueue() {
        return new Queue("orders.shipping");
    }

    @Bean
    public Binding paymentsBinding(TopicExchange ordersExchange, Queue paymentsQueue) {
        return BindingBuilder.bind(paymentsQueue).to(ordersExchange).with("order.created");
    }

    @Bean
    public Binding shippingBinding(TopicExchange ordersExchange, Queue shippingQueue) {
        return BindingBuilder.bind(shippingQueue).to(ordersExchange).with("order.created");
    }
}
