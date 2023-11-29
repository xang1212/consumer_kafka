package com.example.consumer.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class MarketPlaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mpId;

    @ManyToOne
    private AdventureEntity adventureSeller;

    @ManyToOne
    private ItemEntity item;

    private String status;

    @ManyToOne
    private AdventureEntity adventureBuyer;
    private int adventureBuyerId = 0;

    @OneToOne
    private NotiEntity noti;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}
