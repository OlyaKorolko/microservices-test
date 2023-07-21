package com.olyakorolko.service;

import com.olyakorolko.domain.Order;
import com.olyakorolko.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    public Order placeOrder(Order order) {
        Order newOrder = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .items(order.getItems())
                .build();
        Order savedOrder = orderRepository.save(newOrder);
        log.info("Order " + newOrder.getId() + " is saved");
        return savedOrder;
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
