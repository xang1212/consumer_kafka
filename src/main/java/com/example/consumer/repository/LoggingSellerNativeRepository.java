package com.example.consumer.repository;

import com.example.consumer.model.LoggingSellerModel;
import com.example.consumer.model.LoggingSellerQueryModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoggingSellerNativeRepository {
    int insertLoggingSeller(LoggingSellerModel loggingSellerModel);
    List<LoggingSellerQueryModel> getLoggingSeller();
}
