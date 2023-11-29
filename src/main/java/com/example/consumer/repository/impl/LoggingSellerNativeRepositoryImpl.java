package com.example.consumer.repository.impl;

import com.example.consumer.model.LoggingSellerModel;
import com.example.consumer.model.LoggingSellerQueryModel;
import com.example.consumer.repository.LoggingSellerNativeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class LoggingSellerNativeRepositoryImpl implements LoggingSellerNativeRepository {
    private final JdbcTemplate jdbcTemplate;

    public LoggingSellerNativeRepositoryImpl(JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertLoggingSeller(LoggingSellerModel loggingSellerModel) {

        String sql = "INSERT INTO logging (adventure_seller_id, item_id) VALUES (?, ?)";

        List<Object> paraList = new ArrayList<>();
        paraList.add(loggingSellerModel.getAdventureSellerId());
        paraList.add(loggingSellerModel.getItemId());
        return this.jdbcTemplate.update(sql, paraList.toArray());
    }

    @Override
    public List<LoggingSellerQueryModel> getLoggingSeller() {
        String sql = "SELECT seller.name as seller_name , i.name, l.created_at, l.update_at FROM " +
                "logging l " +
                "inner join " +
                "adventures seller on l.adventure_seller_id = seller.adventure_id " +
                "INNER JOIN " +
                "items i on l.item_id = i.item_id ";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            LoggingSellerQueryModel loggingSellerQueryModel = new LoggingSellerQueryModel();
            loggingSellerQueryModel.setAdventureName(rs.getString("seller_name"));
            loggingSellerQueryModel.setItemName(rs.getString("name"));
            loggingSellerQueryModel.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            loggingSellerQueryModel.setUpdateAt(rs.getTimestamp("update_at").toLocalDateTime());
            return loggingSellerQueryModel;
        });
    }
}
