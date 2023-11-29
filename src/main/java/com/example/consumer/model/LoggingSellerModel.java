package com.example.consumer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoggingSellerModel {
    private int id;
    private int mpId;
    private int adventureSellerId;
    private int itemId;
    private String status;

    private Integer adventureBuyerId;

    private int notiId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateAt;

    public LoggingSellerModel() {

        this.adventureBuyerId = 0;
    }
}

