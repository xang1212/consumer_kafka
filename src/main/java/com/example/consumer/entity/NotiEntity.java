package com.example.consumer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class NotiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notiId;

    private String message;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}
