package com.example.consumer.repository.impl;

import com.example.consumer.model.LoggingSellerModel;
import com.example.consumer.model.MarketPlaceModel;
import com.example.consumer.repository.MarketPlaceNativeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Slf4j

@Repository
public class MarketPlaceNativeRepositoryImpl implements MarketPlaceNativeRepository {
    private final JdbcTemplate jdbcTemplate;

    public MarketPlaceNativeRepositoryImpl(JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public int insertMarketPlace(MarketPlaceModel marketPlaceModel) {
        String sql = "INSERT INTO market_places (adventure_seller_id, item_id) VALUES (?, ?)";

        List<Object> paraList = new ArrayList<>();
        paraList.add(marketPlaceModel.getAdventureSellerId());
        paraList.add(marketPlaceModel.getItemId());
        return this.jdbcTemplate.update(sql, paraList.toArray());
    }

}
