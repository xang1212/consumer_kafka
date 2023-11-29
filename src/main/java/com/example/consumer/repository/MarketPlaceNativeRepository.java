package com.example.consumer.repository;

import com.example.consumer.model.LoggingSellerModel;
import com.example.consumer.model.MarketPlaceModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketPlaceNativeRepository {
    int insertMarketPlace(MarketPlaceModel marketPlaceModel) ;
}
