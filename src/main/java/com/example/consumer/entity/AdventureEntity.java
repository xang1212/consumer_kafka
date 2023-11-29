package com.example.consumer.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class AdventureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adventureId;

    private String name;

    private BigDecimal balance;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}
