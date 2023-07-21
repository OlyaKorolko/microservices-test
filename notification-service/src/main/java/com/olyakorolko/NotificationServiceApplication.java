package com.olyakorolko;

import com.olyakorolko.events.OrderPlacedConsumerEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class NotificationServiceApplication {
    public static final String NOTIFICATION_TOPIC = "notification";

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = {NOTIFICATION_TOPIC})
    public void handleNotification(OrderPlacedConsumerEvent orderPlacedConsumerEvent) {
        log.info("notifications are to be sent, order is placed with number " + orderPlacedConsumerEvent.getOrderNumber());
    }

}
