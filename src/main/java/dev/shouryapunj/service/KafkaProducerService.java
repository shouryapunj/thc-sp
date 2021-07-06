package dev.shouryapunj.service;

import dev.shouryapunj.dto.OrderCartDTO;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, OrderCartDTO> orderKafkaTemplate;

    @Value("${kafka.topic.name}")
    private String ORDER_TOPIC;

    public KafkaProducerService(KafkaTemplate<String, OrderCartDTO> orderKafkaTemplate) {
        this.orderKafkaTemplate = orderKafkaTemplate;
    }

    public boolean sendOrder(OrderCartDTO orderCart) {

        orderKafkaTemplate.executeInTransaction(t -> {
            ListenableFuture<SendResult<String, OrderCartDTO>> future = t.send(ORDER_TOPIC, orderCart);
            future.addCallback(new ListenableFutureCallback<SendResult<String, OrderCartDTO>>() {
                @Override
                public void onFailure(Throwable throwable) {

                }

                @Override
                public void onSuccess(SendResult<String, OrderCartDTO> stringCustomerOrderSendResult) {
                    RecordMetadata sentOrder = stringCustomerOrderSendResult.getRecordMetadata();
                }
            });
            return true;
        });
        return true;
    }
}
