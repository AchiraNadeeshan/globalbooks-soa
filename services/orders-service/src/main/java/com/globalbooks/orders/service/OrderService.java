package com.globalbooks.orders.service;

import com.globalbooks.orders.model.Order;
import com.globalbooks.orders.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Order createOrder(Order order) {
        Order newOrder = orderRepository.save(order);
        rabbitTemplate.convertAndSend("orders", "order.created", newOrder);
        return newOrder;
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}
