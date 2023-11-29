package com.example.consumer.repository;

import com.example.consumer.model.LoggingSellerModel;
import com.example.consumer.model.LoggingSellerQueryModel;

import java.util.List;

public class LoggingSellerNativeRepositoryTest implements LoggingSellerNativeRepository{
    @Override
    public int insertLoggingSeller(LoggingSellerModel loggingSellerModel) {
        return 1;
    }

    @Override
    public List<LoggingSellerQueryModel> getLoggingSeller() {
        return null;
    }
}
