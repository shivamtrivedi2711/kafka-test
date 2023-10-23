package com.kafka.test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "topic2entity")
@Data
public class Topic2Entity {

    @Embeddable
    @Data
    public static class Topic2Key {
        @Column(name = "catalog_number")
        @JsonProperty("catalog_number")
        private String catalogNumber;

        @Column(name = "country")
        private String country;

        // Getters, Setters, hashCode, and equals
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "catalogNumber", column = @Column(name = "catalog_number")),
            @AttributeOverride(name = "country", column = @Column(name = "country"))
    })
    private Topic2Key key;

    @Embeddable
    @Data
    public static class Topic2Value {

        @Column(name = "order_number")
        @JsonProperty("order_number")
        private String orderNumber;

        @Column(name = "quantity")
        private Integer quantity;

        @Column(name = "sales_date", columnDefinition = "TIMESTAMP")
        @JsonProperty("sales_date")
        private LocalDateTime salesDate;

        // Getters, Setters
    }

    @Embedded
    private Topic2Value value;




    @Embeddable
    @Data
    public static class AuditInfo {
        @Column(name = "event_name")
        @JsonProperty("event_name")
        private String eventName;

        @Column(name = "source_system")
        @JsonProperty("source_system")
        private String sourceSystem;


    }

    @Embedded
    private AuditInfo audit;

    public String getCountry(){
        return this.getKey().getCountry();
    }
    public String getCatalogNumber(){
        return this.getKey().getCatalogNumber();
    }
    public LocalDateTime getSalesDate(){
            return this.getValue().getSalesDate();
    }

    public String getOrderNumber(){
        return this.getValue().getOrderNumber();
    }
    public Integer getQuantity(){
        return this.getValue().getQuantity();
    }
}

