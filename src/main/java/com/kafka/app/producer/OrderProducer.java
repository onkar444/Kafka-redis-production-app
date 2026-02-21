package com.kafka.app.producer;

import com.kafka.app.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
public class OrderProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendOrder(Order order) {
        try {
            String message = objectMapper.writeValueAsString(order);
            kafkaTemplate.send("orders-topic",message);
            log.info("ORDER EVENT SENT: {}",message);
        }catch(Exception e){
            log.error("FAILED TO SEND EVENT: {}",e);
        }
    }
}
