package com.kafka.test.entity;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;


@Data
public class MergedEntity {

    // Fields from Topic1Entity
    private String catalogNumber;
    private String country;
    private Boolean isSelling;
    private String model;
    private String productId;
    private String registrationId;
    private String registrationNumber;
    private Instant sellingStatusDate;

    // Fields from Topic2Entity
    private String orderNumber;
    private Integer quantity;
    private LocalDateTime salesDate;

    // Audit fields from both entities
    private String eventName;
    private String sourceSystem;

}

