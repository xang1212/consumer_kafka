package com.example.adventure.entity;

import com.example.consumer.entity.AdventureEntity;
import com.example.consumer.entity.ItemEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class LoggingSellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int mpId;
    @ManyToOne
    private AdventureEntity adventureSeller;

    @ManyToOne
    private ItemEntity item;
    private String status;

    @ManyToOne
    private AdventureEntity adventureBuyer;
    private int adventureBuyerId = 0;

    private int notiId;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}
