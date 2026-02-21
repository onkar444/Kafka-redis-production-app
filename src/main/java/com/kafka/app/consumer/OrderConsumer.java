package com.kafka.app.consumer;

import com.kafka.app.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;


@Service
public class OrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;

    public OrderConsumer(ObjectMapper objectMapper, StringRedisTemplate redisTemplate) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }


    @RetryableTopic(
            attempts ="3",
            backOff = @BackOff(delay = 2000),
            dltTopicSuffix = "-dlt"
    )
    @KafkaListener(topics = "orders-topic", groupId = "group-id")
    public void consume(String message) {
        try {
            Order order = objectMapper.readValue(message, Order.class);
            String redisKey = "processed_order" + order.orderId();
            Boolean alreadyProcessed = redisTemplate.hasKey(redisKey);

            if (alreadyProcessed) {
                log.warn("DUPLICATE EVENT DETECTED,SKIPPING ORDERID={}", order.orderId());
                return;
            }

            if(order.product().equalsIgnoreCase("FAIL") && !message.contains("retry")){
                throw new RuntimeException("Simulated Failure");
            }

            log.info("PROCESSING ORDER:{}", order);

            redisTemplate.opsForValue().set(redisKey, "true");
        } catch (Exception e) {
            log.error("ERROR PROCESSING ORDER, RETRYING...", e);
            throw new RuntimeException(e);
        }
    }

    @DltHandler
    public void handleDlt(String message){
        log.error("MESSAGE SENT TO DLT : {}",message);
    }
}
