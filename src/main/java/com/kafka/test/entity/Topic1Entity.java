package com.kafka.test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;


@Entity
@Table(name = "topic1_entity")
@Data
public class Topic1Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Key key;

    @Embedded
    private Value value;

    @Embedded
    private Audit audit;

    public String getCountry(){
        return this.getKey().getCountry();
    }
    public String getCatalogNumber(){
        return this.getKey().getCatalogNumber();
    }
    public Instant getSellingStatusDate(){
        return this.getValue().getSellingStatusDate();
    }

    public boolean getIsSelling(){
        return this.getValue().isSelling();
    }
    public String getModel(){
        return this.getValue().getModel();
    }
    public String getProductId(){
        return this.getValue().getProductId();
    }

    public String getRegistrationId(){
        return this.getValue().getRegistrationId();
    }
    public String getRegistrationNumber(){
        return this.getValue().getRegistrationNumber();
    }
}

@Embeddable
@Data
@Getter
class Key implements Serializable {
    @Column(name = "catalog_number")
    @JsonProperty("catalog_number")
    private String catalogNumber;

    private String country;
}
@Embeddable
@Data
class Audit {
    @Column(name = "event_name")
    @JsonProperty("event_name")
    private String eventName;

    @Column(name = "source_system")
    @JsonProperty("source_system")
    private String sourceSystem;
}
@Embeddable
@Data
class Value {
    @Column(name = "catalog_number", insertable = false, updatable = false)
    @JsonProperty("catalog_number")
    private String catalogNumber;

    @Column(name = "is_selling")
    @JsonProperty("is_selling")
    private boolean isSelling;

    private String model;

    @Column(name = "product_id")
    @JsonProperty("product_id")
    private String productId;

    @Column(name = "registration_id")
    @JsonProperty("registration_id")
    private String registrationId;

    @Column(name = "registration_number")
    @JsonProperty("registration_number")
    private String registrationNumber;

    @Column(name = "selling_status_date")
    @JsonProperty("selling_status_date")
    private Instant sellingStatusDate;

    @Column(name = "country", insertable = false, updatable = false)
    private String country;
}
