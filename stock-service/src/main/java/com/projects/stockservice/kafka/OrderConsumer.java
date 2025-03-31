package com.projects.stockservice.kafka;

import com.projects.basedomains.dto.OrderEvent;
import com.projects.stockservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static  final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics="${spring.kafka.topic.name}"
    ,groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderEvent event){
        LOGGER.info(String.format("Order event received in stock service => %s",event.toString()));

        //save the order event into DB
        orderService.saveOrder(event.getOrder());
        LOGGER.info("Order saved to MongoDB successfully!");
    }

}
