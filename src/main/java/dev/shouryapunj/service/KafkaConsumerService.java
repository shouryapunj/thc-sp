package dev.shouryapunj.service;

import dev.shouryapunj.dto.OrderCartDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final OrderCartService orderCartService;

    public KafkaConsumerService(OrderCartService orderCartService){
        this.orderCartService = orderCartService;
    }

    @KafkaListener(containerFactory = "jsonKafkaListenerContainerFactory",
            topics = "${kafka.topic.name}",
            groupId = "${kafka.topic.groupId}")
    public void consumeOrderData(OrderCartDTO orderCart) {

        orderCartService.consumeOrder(orderCart);
    }

}
