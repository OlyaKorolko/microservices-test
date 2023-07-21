package com.olyakorolko.controller;

import com.olyakorolko.client.InventoryClient;
import com.olyakorolko.domain.Order;
import com.olyakorolko.domain.OrderItem;
import com.olyakorolko.dto.OrderInDTO;
import com.olyakorolko.dto.OrderOutDTO;
import com.olyakorolko.dto.mappers.OrderMapper;
import com.olyakorolko.events.OrderPlacedEvent;
import com.olyakorolko.exception.NotInStockException;
import com.olyakorolko.service.OrderService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
@Slf4j
public class OrderController {
    public static final String INVENTORY = "inventory";
    public static final String NOTIFICATION_TOPIC = "notification";

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @TimeLimiter(name = INVENTORY)
    @CircuitBreaker(name = INVENTORY, fallbackMethod = "addOrderFallback")
    public CompletableFuture<OrderOutDTO> addOrder(@RequestBody OrderInDTO orderInDTO) {
        Order order = orderMapper.fromDto(orderInDTO);
        List<String> skuCodes = order.getItems().stream()
                .map(OrderItem::getSkuCode)
                .collect(toList());

        return CompletableFuture.supplyAsync(() -> {
            if (inventoryClient.isInStock(skuCodes)) {
                Order newOrder = orderService.placeOrder(order);
                kafkaTemplate.send(NOTIFICATION_TOPIC, new OrderPlacedEvent(newOrder.getOrderNumber()));
                return orderMapper.toDto(newOrder);
            } else {
                throw new NotInStockException("Items are not in stock, order cannot be added");
            }
        });
    }

    public CompletableFuture<OrderOutDTO> addOrderFallback(OrderInDTO orderInDTO, NotInStockException runtimeException) {
        return CompletableFuture.failedFuture(runtimeException);
    }

    public CompletableFuture<OrderOutDTO> addOrderFallback(OrderInDTO orderInDTO, FeignException exception) {
        log.info("from cache");
        return CompletableFuture.failedFuture(new Exception("Something went wrong, please try again"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderOutDTO> getAllOrders() {
        return orderService.findAll().stream().map(orderMapper::toDto).collect(toList());
    }
}
