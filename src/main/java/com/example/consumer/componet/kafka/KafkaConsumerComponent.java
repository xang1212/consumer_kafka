package com.example.consumer.componet.kafka;

import com.example.consumer.model.LoggingSellerModel;
import com.example.consumer.model.MarketPlaceModel;
import com.example.consumer.service.LoggingSellerService;
import com.example.consumer.service.MarketPlaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumerComponent {
    private MarketPlaceService marketPlaceService;

    public KafkaConsumerComponent(MarketPlaceService marketPlaceService) {
        this.marketPlaceService = marketPlaceService;
    }

    @KafkaListener(topics = "${kafka.topics.market}", groupId = "${kafka.groupid.market}")
    public void consumeMessage(String message) {
        log.info("Received message: {}", message);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MarketPlaceModel marketPlaceModel = objectMapper.readValue(message, MarketPlaceModel.class);
            this.marketPlaceService.insertMarketPlace(marketPlaceModel);
        } catch (Exception e) {
            log.error("Error processing Kafka message: {}", e.getMessage());
        }
    }
}


//    @KafkaListener(topics = "${kafka.topics.market}", groupId = "${kafka.groupid.logging}")
//    public void consumeLogSeller(@Payload String message) {
//        log.info("Received message: {}", message);
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            LoggingSellerModel loggingSellerModel = objectMapper.readValue(message, LoggingSellerModel.class);
//
//            this.loggingSellerService.insertLoggingSeller(loggingSellerModel);
//        } catch (Exception e) {
//            log.error("Error deserializing message: {}", e.getMessage());
//        }
//    }