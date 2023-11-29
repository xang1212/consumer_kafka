package com.example.consumer.service;

import com.example.consumer.model.MarketPlaceModel;
import com.example.consumer.model.ResponseModel;
import com.example.consumer.repository.MarketPlaceNativeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MarketPlaceService {

    public MarketPlaceService(MarketPlaceNativeRepository marketPlaceNativeRepository
    ){
        this.marketPlaceNativeRepository = marketPlaceNativeRepository;
    }
    private final MarketPlaceNativeRepository marketPlaceNativeRepository;

    public void insertMarketPlace(MarketPlaceModel marketPlaceModel) {
        ResponseModel<Integer> result = new ResponseModel<>();
        try {
            int insertedRows = this.marketPlaceNativeRepository.insertMarketPlace(marketPlaceModel);
            result.setStatus(201);
            result.setDescription("Market inserted successfully.");
            result.setData(insertedRows);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Insert market error: {}", e.getMessage());
        }
    }

}
